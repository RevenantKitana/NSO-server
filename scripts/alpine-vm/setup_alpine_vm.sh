#!/bin/sh
# ===============================================================================
# NSO Game Server - Comprehensive Alpine Linux VM Bootstrap & Optimization
# Compatible with Alpine Linux v3.x (OpenRC, apk, musl libc + jemalloc)
# Supports both Disk-Installed Alpine and Live/Diskless RAM Mode with /dev/sda
# ===============================================================================
set -e

TOTAL_RAM_MB=$(free -m | awk '/^Mem:/{print $2}')
echo "======================================================"
echo " 1. DETECTING ALPINE VM HARDWARE & STORAGE            "
echo "======================================================"
echo "  [OS]       : $(grep PRETTY_NAME /etc/os-release | cut -d= -f2 | tr -d '\"')"
echo "  [Arch]     : $(uname -m)"
echo "  [CPU Cores]: $(nproc)"
echo "  [RAM Total]: ${TOTAL_RAM_MB} MB"

ROOT_FS_TYPE=$(awk '$2 == "/" {print $3}' /proc/mounts | tail -n 1)
echo "  [Root FS]  : ${ROOT_FS_TYPE}"

# -------------------------------------------------------------------------------
# AUTOMATIC DISK MOUNTING FOR LIVE/DISKLESS ALPINE VM (IF / IS ON TMPFS)
# -------------------------------------------------------------------------------
if [ "$ROOT_FS_TYPE" = "tmpfs" ]; then
    DISK_DEV=""
    if [ -b /dev/sda ]; then
        DISK_DEV="/dev/sda"
    elif [ -b /dev/vda ]; then
        DISK_DEV="/dev/vda"
    fi

    if [ -n "$DISK_DEV" ]; then
        echo ""
        echo "  [!] Detected Alpine running in RAM (tmpfs) with disk ${DISK_DEV}."
        echo "  [*] Setting up persistent ext4 storage on ${DISK_DEV} to preserve RAM..."
        apk add --no-cache e2fsprogs >/dev/null 2>&1 || true

        if ! blkid "$DISK_DEV" 2>/dev/null | grep -q 'TYPE="ext4"'; then
            echo "  [*] Formatting ${DISK_DEV} as ext4 (Label: NSO_ALPINE)..."
            mkfs.ext4 -F -L NSO_ALPINE "$DISK_DEV" >/dev/null 2>&1
        fi

        mkdir -p /mnt/disk
        if ! awk '$2 == "/mnt/disk" {found=1} END {exit !found}' /proc/mounts; then
            mount -t ext4 "$DISK_DEV" /mnt/disk
        fi

        mkdir -p /mnt/disk/usr /mnt/disk/var /mnt/disk/opt /mnt/disk/etc_backup

        if ! awk '$2 == "/usr" {found=1} END {exit !found}' /proc/mounts; then
            if [ ! -d /mnt/disk/usr/bin ]; then
                cp -a /usr/. /mnt/disk/usr/
            fi
            mount --bind /mnt/disk/usr /usr
        fi

        if ! awk '$2 == "/var" {found=1} END {exit !found}' /proc/mounts; then
            if [ ! -d /mnt/disk/var/lib ]; then
                cp -a /var/. /mnt/disk/var/
            fi
            mount --bind /mnt/disk/var /var
        fi

        if ! awk '$2 == "/opt" {found=1} END {exit !found}' /proc/mounts; then
            mount --bind /mnt/disk/opt /opt
        fi

        echo "  >> Persistent storage on ${DISK_DEV} mounted to /usr, /var, /opt!"
    fi
fi

echo ""
echo "======================================================"
echo " 2. CONFIGURING ALPINE APK REPOSITORIES               "
echo "======================================================"
ALPINE_VER=$(cut -d. -f1,2 /etc/alpine-release)
if ! grep -q "dl-cdn.alpinelinux.org" /etc/apk/repositories 2>/dev/null; then
    cat <<EOF >> /etc/apk/repositories
https://dl-cdn.alpinelinux.org/alpine/v${ALPINE_VER}/main
https://dl-cdn.alpinelinux.org/alpine/v${ALPINE_VER}/community
EOF
    echo "  >> Added official Alpine v${ALPINE_VER} main & community mirrors."
fi

apk update

echo ""
echo "======================================================"
echo " 3. SYSTEM TUNING (ADAPTIVE SWAP & KERNEL SYSCTL)     "
echo "======================================================"
SWAP_PATH="/opt/swapfile"
AVAIL_DISK_MB=$(df -m /opt | awk 'NR==2 {print $4}')

if ! grep -q "$SWAP_PATH" /proc/swaps 2>/dev/null && ! grep -q "/swapfile" /proc/swaps 2>/dev/null; then
    if [ ! -f "$SWAP_PATH" ]; then
        if [ "$AVAIL_DISK_MB" -ge 6500 ]; then
            SWAP_MB=2048
        elif [ "$AVAIL_DISK_MB" -ge 2200 ]; then
            SWAP_MB=1024
        else
            SWAP_MB=512
        fi
        echo "  [*] Creating ${SWAP_MB}MB swap file at ${SWAP_PATH}..."
        dd if=/dev/zero of="$SWAP_PATH" bs=1M count="$SWAP_MB" status=none
        chmod 600 "$SWAP_PATH"
        mkswap "$SWAP_PATH" >/dev/null
    fi
    swapon "$SWAP_PATH" 2>/dev/null || true
    if [ "$ROOT_FS_TYPE" != "tmpfs" ] && ! grep -q "$SWAP_PATH" /etc/fstab 2>/dev/null; then
        echo "${SWAP_PATH} none swap sw 0 0" >> /etc/fstab
        rc-update add swap boot >/dev/null 2>&1 || true
    fi
    echo "  >> Swap activated at ${SWAP_PATH}."
else
    echo "  >> Swap is already active."
fi

mkdir -p /etc/sysctl.d
cat <<'EOF' > /etc/sysctl.d/99-nso-tuning.conf
# Memory Subsystem Tuning
vm.swappiness = 10
vm.vfs_cache_pressure = 50
vm.dirty_background_ratio = 5
vm.dirty_ratio = 10
vm.overcommit_memory = 1
fs.file-max = 2097152
vm.max_map_count = 262144

# Network & Socket Stack (Low Latency & High Concurrent Connections)
net.core.somaxconn = 4096
net.ipv4.tcp_max_syn_backlog = 4096
net.ipv4.tcp_tw_reuse = 1
net.ipv4.tcp_fin_timeout = 15
net.ipv4.tcp_keepalive_time = 300
net.ipv4.tcp_keepalive_intvl = 15
net.ipv4.tcp_keepalive_probes = 5

# Network Security & Anti-SYN Flood
net.ipv4.tcp_syncookies = 1
net.ipv4.tcp_synack_retries = 2
net.ipv4.conf.all.rp_filter = 1
net.ipv4.conf.default.rp_filter = 1
net.ipv4.icmp_echo_ignore_broadcasts = 1
EOF

sysctl -p /etc/sysctl.d/99-nso-tuning.conf >/dev/null 2>&1 || true
rc-update add sysctl boot >/dev/null 2>&1 || true

mkdir -p /etc/security/limits.d
cat <<'EOF' > /etc/security/limits.d/99-nso-limits.conf
* soft nofile 65535
* hard nofile 65535
* soft nproc 65535
* hard nproc 65535
root soft nofile 65535
root hard nofile 65535
EOF

echo ""
echo "======================================================"
echo " 4. INSTALLING PACKAGES (OPENJDK 17, MARIADB, TOOLS)  "
echo "======================================================"
apk add --no-cache \
    openjdk17-jre-headless \
    jemalloc \
    mariadb \
    mariadb-client \
    tzdata \
    bash \
    curl \
    tar \
    gzip \
    iptables \
    iproute2 \
    procps \
    logrotate

cp -f /usr/share/zoneinfo/Asia/Ho_Chi_Minh /etc/localtime 2>/dev/null || true
echo "Asia/Ho_Chi_Minh" > /etc/timezone

mkdir -p /etc/logrotate.d
cat <<'EOF' > /etc/logrotate.d/nso-server
/opt/nso-server/logs/*.log {
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

echo ""
echo "======================================================"
echo " 5. ADAPTIVE MARIADB TUNING (INTERNAL 127.0.0.1:3306) "
echo "======================================================"
if [ ! -d /var/lib/mysql/mysql ]; then
    echo "  [*] Initializing MariaDB data directory..."
    /etc/init.d/mariadb setup
fi

# Alpine enables skip-networking by default in mariadb-server.cnf - disable it for local JDBC TCP 3306
if [ -f /etc/my.cnf.d/mariadb-server.cnf ]; then
    sed -i 's/^[[:space:]]*skip-networking/#skip-networking/g' /etc/my.cnf.d/mariadb-server.cnf
fi

if [ "$TOTAL_RAM_MB" -ge 4000 ]; then
    DB_BUFFER_POOL="512M"
    DB_LOG_BUFFER="16M"
    DB_MAX_CONN="150"
elif [ "$TOTAL_RAM_MB" -ge 2000 ]; then
    DB_BUFFER_POOL="256M"
    DB_LOG_BUFFER="16M"
    DB_MAX_CONN="100"
else
    DB_BUFFER_POOL="32M"
    DB_LOG_BUFFER="4M"
    DB_MAX_CONN="40"
fi

mkdir -p /etc/my.cnf.d
cat <<EOF > /etc/my.cnf.d/60-nso-tuning.cnf
[mysqld]
performance_schema = OFF
innodb_buffer_pool_size = ${DB_BUFFER_POOL}
innodb_log_buffer_size = ${DB_LOG_BUFFER}
innodb_read_io_threads = 2
innodb_write_io_threads = 2
max_connections = ${DB_MAX_CONN}
key_buffer_size = 8M
table_open_cache = 100
table_definition_cache = 150
thread_cache_size = 4
bind-address = 127.0.0.1
port = 3306
skip-name-resolve
EOF

rc-service mariadb restart
rc-update add mariadb default >/dev/null 2>&1 || true

# Setup database & users (Internal localhost & 127.0.0.1 only)
mariadb -u root -e "CREATE DATABASE IF NOT EXISTS nso_test CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"
mariadb -u root -e "CREATE USER IF NOT EXISTS 'nso_user'@'localhost' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -u root -e "ALTER USER 'nso_user'@'localhost' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -u root -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_user'@'localhost';"
mariadb -u root -e "CREATE USER IF NOT EXISTS 'nso_user'@'127.0.0.1' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -u root -e "ALTER USER 'nso_user'@'127.0.0.1' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -u root -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_user'@'127.0.0.1';"
mariadb -u root -e "FLUSH PRIVILEGES;"
echo "  >> MariaDB initialized and user 'nso_user' configured on 127.0.0.1:3306."

echo ""
echo "======================================================"
echo " 6. FIREWALL & NETWORK SECURITY (IPTABLES)            "
echo "======================================================"
iptables -I INPUT 1 -p tcp --dport 22 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 2 -p tcp --dport 14444 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 3 -p tcp --dport 8020 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 4 -p tcp --dport 80 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 5 -p tcp --dport 443 -j ACCEPT 2>/dev/null || true
/etc/init.d/iptables save >/dev/null 2>&1 || true

echo ""
echo "======================================================"
echo " 7. PREPARING NSO SERVER WORKSPACE & OPENRC SERVICE   "
echo "======================================================"
mkdir -p /opt/nso-server/logs /opt/nso-server/backups /opt/nso-server/data/backup
mkdir -p /home/ubuntu
if [ ! -e /home/ubuntu/nso-server ]; then
    ln -s /opt/nso-server /home/ubuntu/nso-server
fi

rc-update add crond default >/dev/null 2>&1 || true
rc-service crond restart >/dev/null 2>&1 || true

# Backup /etc configs to persistent disk if running in Live mode
if [ "$ROOT_FS_TYPE" = "tmpfs" ] && [ -d /mnt/disk/etc_backup ]; then
    cp -rf /etc/my.cnf* /mnt/disk/etc_backup/ 2>/dev/null || true
    cp -rf /etc/sysctl.d /mnt/disk/etc_backup/ 2>/dev/null || true
    cp -rf /root/.ssh /mnt/disk/etc_backup/ 2>/dev/null || true
fi

echo ""
echo "=== MEMORY & SWAP AFTER ALPINE OPTIMIZATION ==="
free -h
echo ""
echo "=== DISK STORAGE ==="
df -h / /opt
echo ""
echo "=== ACTIVE LISTENING PORTS ==="
ss -tuln
echo ""
echo ">> Alpine Linux VM bootstrap completed successfully!"

