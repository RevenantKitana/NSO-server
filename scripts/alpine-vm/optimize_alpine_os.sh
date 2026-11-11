#!/bin/sh
# ===============================================================================
# NSO Game Server - Standalone Alpine Linux OS Optimization & Security Hardening
# ===============================================================================
set -e

TOTAL_RAM_MB=$(free -m | awk '/^Mem:/{print $2}')
echo "======================================================"
echo " 1. ALPINE HARDWARE & MEMORY CHECK                    "
echo "======================================================"
echo "  [OS]       : $(grep PRETTY_NAME /etc/os-release | cut -d= -f2 | tr -d '\"')"
echo "  [Arch]     : $(uname -m)"
echo "  [CPU Cores]: $(nproc)"
echo "  [RAM Total]: ${TOTAL_RAM_MB} MB"

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
    echo "  >> Swap activated."
fi

echo ""
echo "======================================================"
echo " 2. KERNEL SYSCTL & NETWORK STACK OPTIMIZATION        "
echo "======================================================"
mkdir -p /etc/sysctl.d
cat <<'EOF' > /etc/sysctl.d/99-nso-tuning.conf
vm.swappiness = 10
vm.vfs_cache_pressure = 50
vm.dirty_background_ratio = 5
vm.dirty_ratio = 10
vm.overcommit_memory = 1
fs.file-max = 2097152
vm.max_map_count = 262144

net.core.somaxconn = 4096
net.ipv4.tcp_max_syn_backlog = 4096
net.ipv4.tcp_tw_reuse = 1
net.ipv4.tcp_fin_timeout = 15
net.ipv4.tcp_keepalive_time = 300
net.ipv4.tcp_keepalive_intvl = 15
net.ipv4.tcp_keepalive_probes = 5

net.ipv4.tcp_syncookies = 1
net.ipv4.tcp_synack_retries = 2
net.ipv4.conf.all.rp_filter = 1
net.ipv4.conf.default.rp_filter = 1
net.ipv4.icmp_echo_ignore_broadcasts = 1
EOF

sysctl -p /etc/sysctl.d/99-nso-tuning.conf >/dev/null 2>&1 || true

echo ""
echo "======================================================"
echo " 3. MARIADB ULTRA-LOW MEMORY TUNING                   "
echo "======================================================"
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

if [ -d /etc/my.cnf.d ]; then
    sed -i 's/^[[:space:]]*skip-networking/#skip-networking/g' /etc/my.cnf.d/mariadb-server.cnf 2>/dev/null || true
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
    rc-service mariadb restart 2>/dev/null || true
fi

echo ""
echo "======================================================"
echo " 4. IPTABLES FIREWALL & APK CACHE CLEANUP             "
echo "======================================================"
iptables -I INPUT 1 -p tcp --dport 22 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 2 -p tcp --dport 14444 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 3 -p tcp --dport 8020 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 4 -p tcp --dport 80 -j ACCEPT 2>/dev/null || true
iptables -I INPUT 5 -p tcp --dport 443 -j ACCEPT 2>/dev/null || true
/etc/init.d/iptables save >/dev/null 2>&1 || true

rm -rf /var/cache/apk/* 2>/dev/null || true

echo ""
echo "=== MEMORY & SWAP ==="
free -h
echo ""
echo ">> Alpine OS optimization & hardening completed successfully!"

