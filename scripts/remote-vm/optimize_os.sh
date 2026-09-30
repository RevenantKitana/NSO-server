#!/usr/bin/env bash
# ===============================================================================
# NSO Game Server - Standalone OS Optimization & Security Hardening
# ===============================================================================
set -e

TOTAL_RAM_MB=$(free -m | awk '/^Mem:/{print $2}')
echo "======================================================"
echo " 1. SYSTEM HARDWARE & MEMORY CHECK                    "
echo "======================================================"
echo "  [Arch]     : $(uname -m)"
echo "  [CPU Cores]: $(nproc)"
echo "  [RAM Total]: ${TOTAL_RAM_MB} MB"

# Create 4GB Swap if not exists
if [ ! -f /swapfile ] && [ "$TOTAL_RAM_MB" -lt 8192 ]; then
    echo "Creating 4GB swap file..."
    fallocate -l 4G /swapfile || dd if=/dev/zero of=/swapfile bs=1M count=4096
    chmod 600 /swapfile
    mkswap /swapfile
    swapon /swapfile
    if ! grep -q '/swapfile' /etc/fstab; then
        echo '/swapfile none swap sw 0 0' >> /etc/fstab
    fi
    echo "  >> Swap 4GB activated."
fi

echo "======================================================"
echo " 2. STOPPING & DISABLING UNNECESSARY & AUTO-UPDATE SVCS"
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

apt-get clean 2>/dev/null || true

echo "======================================================"
echo " 3. CAPPING SYSTEMD JOURNAL RAM USAGE                 "
echo "======================================================"
mkdir -p /etc/systemd/journald.conf.d
cat <<'EOF' > /etc/systemd/journald.conf.d/99-cap-size.conf
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
echo " 4. ADVANCED SYSCTL KERNEL MEMORY & TCP TUNING        "
echo "======================================================"
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

# Limits tuning
cat <<'EOF' > /etc/security/limits.d/99-nso-limits.conf
* soft nofile 65535
* hard nofile 65535
* soft nproc 65535
* hard nproc 65535
root soft nofile 65535
root hard nofile 65535
EOF

echo "======================================================"
echo " 5. NETWORK SECURITY (RATE LIMIT & FAIL2BAN)          "
echo "======================================================"
command -v ufw >/dev/null 2>&1 && ufw disable >/dev/null 2>&1 || true

iptables -D INPUT -p tcp --dport 14444 -m state --state NEW -m recent --set --name GAME_LIMIT 2>/dev/null || true
iptables -D INPUT -p tcp --dport 14444 -m state --state NEW -m recent --update --seconds 10 --hitcount 20 --name GAME_LIMIT -j DROP 2>/dev/null || true
iptables -D INPUT -p tcp --dport 22 -m state --state NEW -m recent --set --name SSH_LIMIT 2>/dev/null || true
iptables -D INPUT -p tcp --dport 22 -m state --state NEW -m recent --update --seconds 60 --hitcount 6 --name SSH_LIMIT -j DROP 2>/dev/null || true

iptables -I INPUT 1 -p tcp --dport 14444 -m state --state NEW -m recent --set --name GAME_LIMIT 2>/dev/null || true
iptables -I INPUT 2 -p tcp --dport 14444 -m state --state NEW -m recent --update --seconds 10 --hitcount 20 --name GAME_LIMIT -j DROP 2>/dev/null || true

mkdir -p /etc/iptables
iptables-save > /etc/iptables/rules.v4 2>/dev/null || true

echo "======================================================"
echo " 6. CLEANING MEMORY & VERIFYING                       "
echo "======================================================"
sync
echo 3 > /proc/sys/vm/drop_caches

echo ""
echo "=== RAM & SWAP USAGE AFTER OS OPTIMIZATION ==="
free -h
echo ""
echo "=== DISK ROOT USAGE ==="
df -h /

