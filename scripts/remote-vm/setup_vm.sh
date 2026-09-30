#!/usr/bin/env bash
# ===============================================================================
# NSO Game Server - Comprehensive OS Optimization & VM Bootstrap Setup
# Optimized for Cloud VMs (Oracle Cloud Ampere A1 / x86_64 / Debian / Ubuntu)
# ===============================================================================
set -e

TOTAL_RAM_MB=$(free -m | awk '/^Mem:/{print $2}')
echo "======================================================"
echo " 1. DETECTING VM HARDWARE & MEMORY RESOURCES          "
echo "======================================================"
echo "  [Arch]     : $(uname -m)"
echo "  [CPU Cores]: $(nproc)"
echo "  [RAM Total]: ${TOTAL_RAM_MB} MB"
echo ""

echo "======================================================"
echo " 2. SYSTEM TUNING (SWAP & ADVANCED KERNEL SYSCTL)     "
echo "======================================================"

# Create 4GB Swap if not exists
if [ ! -f /swapfile ] && [ "$TOTAL_RAM_MB" -lt 8192 ]; then
    echo "Creating 4GB swap file for memory safety..."
    fallocate -l 4G /swapfile || dd if=/dev/zero of=/swapfile bs=1M count=4096
    chmod 600 /swapfile
    mkswap /swapfile
    swapon /swapfile
    if ! grep -q '/swapfile' /etc/fstab; then
        echo '/swapfile none swap sw 0 0' >> /etc/fstab
    fi
    echo "  >> Swap 4GB activated successfully."
fi

# Advanced Kernel Sysctl Tuning
cat <<'EOF' > /etc/sysctl.d/99-nso-tuning.conf
# Memory Subsystem Tuning
vm.swappiness = 10
vm.vfs_cache_pressure = 50
vm.dirty_background_ratio = 5
vm.dirty_ratio = 10
vm.overcommit_memory = 1
fs.file-max = 2097152
kernel.pid_max = 4194304
vm.max_map_count = 262144

# Network & Socket Stack (Low Latency & High Concurrent Connections)
net.core.somaxconn = 4096
net.ipv4.tcp_max_syn_backlog = 4096
net.ipv4.tcp_tw_reuse = 1
net.ipv4.tcp_fin_timeout = 15
net.ipv4.tcp_keepalive_time = 300
net.ipv4.tcp_keepalive_intvl = 15
net.ipv4.tcp_keepalive_probes = 5

# Network Security & Anti-DDoS / Anti-SYN Flood
net.ipv4.tcp_syncookies = 1
net.ipv4.tcp_synack_retries = 2
net.ipv4.conf.all.rp_filter = 1
net.ipv4.conf.default.rp_filter = 1
net.ipv4.icmp_echo_ignore_broadcasts = 1
EOF

sysctl -p /etc/sysctl.d/99-nso-tuning.conf >/dev/null 2>&1 || true

# File Descriptors & Limits Tuning
cat <<'EOF' > /etc/security/limits.d/99-nso-limits.conf
* soft nofile 65535
* hard nofile 65535
* soft nproc 65535
* hard nproc 65535
root soft nofile 65535
root hard nofile 65535
EOF

# Limit Journal Log Size
mkdir -p /etc/systemd/journald.conf.d
cat <<'EOF' > /etc/systemd/journald.conf.d/99-nso-journald.conf
[Journal]
Storage=persistent
Compress=yes
SystemMaxUse=20M
RuntimeMaxUse=10M
MaxRetentionSec=3day
EOF
systemctl restart systemd-journald 2>/dev/null || true
journalctl --vacuum-size=20M >/dev/null 2>&1 || true

# Limit and Rotate NSO Server Application Logs
cat <<'EOF' > /etc/logrotate.d/nso-server
/home/ubuntu/nso-server/logs/*.log {
    daily
    rotate 3
    size 20M
    compress
    delaycompress
    missingok
    notifempty
    copytruncate
}
EOF

echo "======================================================"
echo " 3. DE-BLOATING BACKGROUND OS SERVICES & AUTO-UPDATES "
echo "======================================================"
SERVICES_TO_DISABLE=(
    "unattended-upgrades.service"
    "apt-daily.service"
    "apt-daily.timer"
    "apt-daily-upgrade.service"
    "apt-daily-upgrade.timer"
    "packagekit.service"
    "motd-news.service"
    "motd-news.timer"
    "man-db.service"
    "man-db.timer"
    "fwupd.service"
    "fwupd.socket"
    "fwupd-refresh.timer"
    "iscsid.service"
    "iscsid.socket"
    "udisks2.service"
    "rpcbind.service"
    "rpcbind.socket"
    "snap.oracle-cloud-agent.oracle-cloud-agent.service"
    "snap.oracle-cloud-agent.oracle-cloud-agent-updater.service"
    "snapd.service"
    "snapd.socket"
    "snapd.seeded.service"
)

for svc in "${SERVICES_TO_DISABLE[@]}"; do
    systemctl stop "$svc" 2>/dev/null || true
    systemctl disable "$svc" 2>/dev/null || true
    systemctl mask "$svc" 2>/dev/null || true
done

killall -9 unattended-upgrade apt-get apt 2>/dev/null || true

mkdir -p /etc/apt/apt.conf.d
cat <<'EOF' > /etc/apt/apt.conf.d/20auto-upgrades
APT::Periodic::Update-Package-Lists "0";
APT::Periodic::Download-Upgradeable-Packages "0";
APT::Periodic::AutocleanInterval "0";
APT::Periodic::Unattended-Upgrade "0";
EOF

cat <<'EOF' > /etc/apt/apt.conf.d/10periodic
APT::Periodic::Update-Package-Lists "0";
APT::Periodic::Download-Upgradeable-Packages "0";
APT::Periodic::AutocleanInterval "0";
APT::Periodic::Unattended-Upgrade "0";
EOF

echo "======================================================"
echo " 4. INSTALLING PACKAGES (JAVA 17, MARIADB, TOOLS)     "
echo "======================================================"
export DEBIAN_FRONTEND=noninteractive
apt-get update -y
apt-get install -y openjdk-17-jre-headless mariadb-server mariadb-client ufw iptables-persistent fail2ban htop rsync curl unzip tar

echo "======================================================"
echo " 5. ADAPTIVE MARIADB TUNING                           "
echo "======================================================"
# Adaptive InnoDB Buffer Pool size based on RAM
if [ "$TOTAL_RAM_MB" -ge 4000 ]; then
    DB_BUFFER_POOL="512M"
    DB_LOG_BUFFER="16M"
    DB_MAX_CONN="150"
elif [ "$TOTAL_RAM_MB" -ge 2000 ]; then
    DB_BUFFER_POOL="256M"
    DB_LOG_BUFFER="16M"
    DB_MAX_CONN="100"
else
    DB_BUFFER_POOL="128M"
    DB_LOG_BUFFER="8M"
    DB_MAX_CONN="50"
fi

cat <<EOF > /etc/mysql/mariadb.conf.d/60-nso-tuning.cnf
[mysqld]
performance_schema = OFF
innodb_buffer_pool_size = ${DB_BUFFER_POOL}
innodb_log_buffer_size = ${DB_LOG_BUFFER}
innodb_read_io_threads = 2
innodb_write_io_threads = 2
max_connections = ${DB_MAX_CONN}
key_buffer_size = 16M
table_open_cache = 400
thread_cache_size = 8
bind-address = 0.0.0.0
EOF

systemctl restart mariadb
systemctl enable mariadb

# Setup database & users
mariadb -e "CREATE DATABASE IF NOT EXISTS nso_test CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"
mariadb -e "CREATE USER IF NOT EXISTS 'nso_user'@'localhost' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_user'@'localhost';"
mariadb -e "CREATE USER IF NOT EXISTS 'nso_web'@'%' IDENTIFIED BY 'NsoWebDb2026!@#';"
mariadb -e "ALTER USER 'nso_web'@'%' IDENTIFIED BY 'NsoWebDb2026!@#';"
mariadb -e "GRANT SELECT, INSERT, UPDATE, DELETE ON nso_test.* TO 'nso_web'@'%';"
mariadb -e "FLUSH PRIVILEGES;"

echo "======================================================"
echo " 6. FIREWALL & NETWORK SECURITY (RATE-LIMIT & FAIL2BAN)"
echo "======================================================"
# Disable UFW to prevent OCI VNIC virtual routing conflicts
ufw disable >/dev/null 2>&1 || true

# Reset existing custom rules if any
iptables -D INPUT -p tcp --dport 14444 -m state --state NEW -m recent --set --name GAME_LIMIT 2>/dev/null || true
iptables -D INPUT -p tcp --dport 14444 -m state --state NEW -m recent --update --seconds 10 --hitcount 20 --name GAME_LIMIT -j DROP 2>/dev/null || true
iptables -D INPUT -p tcp --dport 22 -m state --state NEW -m recent --set --name SSH_LIMIT 2>/dev/null || true
iptables -D INPUT -p tcp --dport 22 -m state --state NEW -m recent --update --seconds 60 --hitcount 6 --name SSH_LIMIT -j DROP 2>/dev/null || true

# Rate-limit game port 14444 (Max 20 new conns / 10s per IP)
iptables -I INPUT 1 -p tcp --dport 14444 -m state --state NEW -m recent --set --name GAME_LIMIT
iptables -I INPUT 2 -p tcp --dport 14444 -m state --state NEW -m recent --update --seconds 10 --hitcount 20 --name GAME_LIMIT -j DROP

# Rate-limit SSH port 22 (Max 6 new conns / 60s per IP)
iptables -I INPUT 3 -p tcp --dport 22 -m state --state NEW -m recent --set --name SSH_LIMIT
iptables -I INPUT 4 -p tcp --dport 22 -m state --state NEW -m recent --update --seconds 60 --hitcount 6 --name SSH_LIMIT -j DROP

# Allow ports
iptables -I INPUT 5 -p tcp --dport 22 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 6 -p tcp --dport 14444 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 7 -p tcp --dport 8020 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 8 -p tcp --dport 3306 -j ACCEPT 2>/dev/null || true

mkdir -p /etc/iptables
iptables-save > /etc/iptables/rules.v4 2>/dev/null || true

# Fail2ban configuration
cat <<'EOF' > /etc/fail2ban/jail.local
[DEFAULT]
bantime = 86400
findtime = 600
maxretry = 5

[sshd]
enabled = true
port = 22
mode = aggressive
EOF

systemctl restart fail2ban 2>/dev/null || true
systemctl enable fail2ban >/dev/null 2>&1 || true

echo "======================================================"
echo " 7. GENERATING ADAPTIVE SYSTEMD GAME SERVICE          "
echo "======================================================"
mkdir -p /home/ubuntu/nso-server
chown -R ubuntu:ubuntu /home/ubuntu/nso-server

# Dynamic JVM Memory Allocation & Low-Latency G1GC Tuning
if [ "$TOTAL_RAM_MB" -ge 5000 ]; then
    JVM_MEM="-Xms1024M -Xmx2048M -Xss256k -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:G1ReservePercent=15 -XX:InitiatingHeapOccupancyPercent=45 -XX:+ParallelRefProcEnabled -XX:+AlwaysPreTouch"
elif [ "$TOTAL_RAM_MB" -ge 2000 ]; then
    JVM_MEM="-Xms512M -Xmx1024M -Xss256k -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:G1ReservePercent=15 -XX:InitiatingHeapOccupancyPercent=45 -XX:+ParallelRefProcEnabled -XX:+AlwaysPreTouch"
else
    JVM_MEM="-Xms256M -Xmx400M -Xss256k -XX:+UseSerialGC"
fi

cat <<EOF > /etc/systemd/system/nso-server.service
[Unit]
Description=NSO Game Server Emulator
After=network.target mariadb.service
Wants=mariadb.service

[Service]
Type=simple
User=ubuntu
WorkingDirectory=/home/ubuntu/nso-server
ExecStart=/usr/bin/java -server -Djava.awt.headless=true -Dfile.encoding=UTF-8 ${JVM_MEM} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/ubuntu/nso-server/logs/oom.hprof -jar /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar
Restart=always
RestartSec=5
StandardOutput=append:/home/ubuntu/nso-server/logs/service.log
StandardError=append:/home/ubuntu/nso-server/logs/service_error.log
LimitNOFILE=65535
LimitNPROC=65535

[Install]
WantedBy=multi-user.target
EOF

systemctl daemon-reload
systemctl enable nso-server.service

echo ""
echo "=== MEMORY & SWAP AFTER OPTIMIZATION ==="
free -h
echo ""
echo "=== ACTIVE LISTENING PORTS ==="
ss -tuln
echo ""
echo ">> All OS optimizations, security hardening, and bootstrap completed successfully!"

