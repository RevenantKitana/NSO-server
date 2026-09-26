#!/usr/bin/env bash
set -e

echo "======================================================"
echo " 1. STOPPING & DISABLING UNNECESSARY SERVICES         "
echo "======================================================"

SERVICES_TO_DISABLE=(
    "fwupd.service"
    "fwupd.socket"
    "iscsid.service"
    "iscsid.socket"
    "udisks2.service"
    "rpcbind.service"
    "rpcbind.socket"
    "unattended-upgrades.service"
    "snap.oracle-cloud-agent.oracle-cloud-agent.service"
    "snap.oracle-cloud-agent.oracle-cloud-agent-updater.service"
    "snapd.service"
    "snapd.socket"
)

for svc in "${SERVICES_TO_DISABLE[@]}"; do
    if systemctl list-unit-files | grep -q "^$svc"; then
        echo "Disabling $svc..."
        systemctl stop "$svc" 2>/dev/null || true
        systemctl disable "$svc" 2>/dev/null || true
        systemctl mask "$svc" 2>/dev/null || true
    fi
done

echo "======================================================"
echo " 2. CAPPING SYSTEMD JOURNAL RAM USAGE                 "
echo "======================================================"
mkdir -p /etc/systemd/journald.conf.d
cat <<'EOF' > /etc/systemd/journald.conf.d/99-cap-size.conf
[Journal]
Storage=persistent
Compress=yes
SystemMaxUse=30M
RuntimeMaxUse=15M
MaxRetentionSec=7day
EOF
systemctl restart systemd-journald
journalctl --vacuum-size=30M >/dev/null 2>&1 || true

echo "======================================================"
echo " 3. ADVANCED SYSCTL KERNEL MEMORY & TCP TUNING        "
echo "======================================================"
cat <<'EOF' > /etc/sysctl.d/99-nso-tuning.conf
# Memory management
vm.swappiness=10
vm.vfs_cache_pressure=50
vm.dirty_background_ratio=5
vm.dirty_ratio=10
vm.overcommit_memory=1
fs.file-max=2097152
kernel.pid_max=4194304
vm.max_map_count=262144

# Network & Socket tuning for game server
net.core.somaxconn=4096
net.ipv4.tcp_max_syn_backlog=4096
net.ipv4.tcp_tw_reuse=1
net.ipv4.tcp_fin_timeout=15
net.ipv4.tcp_keepalive_time=300
net.ipv4.tcp_keepalive_intvl=15
net.ipv4.tcp_keepalive_probes=5
EOF

sysctl -p /etc/sysctl.d/99-nso-tuning.conf

echo "======================================================"
echo " 4. CLEANING MEMORY & DROPPING UNUSED CACHES          "
echo "======================================================"
sync
echo 3 > /proc/sys/vm/drop_caches

echo "======================================================"
echo " 5. VERIFYING GAME SERVER & DATABASE STATUS           "
echo "======================================================"
systemctl is-active --quiet mariadb && echo "MariaDB: RUNNING (OK)" || echo "MariaDB: ERROR"
systemctl is-active --quiet nso-server && echo "NSO Server: RUNNING (OK)" || echo "NSO Server: ERROR"

echo ""
echo "=== RAM USAGE AFTER OS OPTIMIZATION ==="
free -h
echo ""
echo "=== TOP PROCESSES BY RAM ==="
ps -eo pid,user,%cpu,%mem,rss,comm --sort=-rss | head -n 10
