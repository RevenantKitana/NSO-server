#!/usr/bin/env bash
set -e

echo "======================================================"
echo " 1. STOPPING & DISABLING UNNECESSARY & AUTO-UPDATE SVCS"
echo "======================================================"

SERVICES_TO_DISABLE=(
    # Auto-update & package services
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
    
    # Cloud agent updaters & unused daemons
    "fwupd.service"
    "fwupd.socket"
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

# Kill any leftover apt or upgrade processes
killall -9 unattended-upgrade apt-get apt 2>/dev/null || true

echo "======================================================"
echo " 2. PERMANENTLY DISABLING AUTOMATIC OS UPDATES        "
echo "======================================================"
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

# Clean apt cache to free disk and RAM buffer
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
systemctl restart systemd-journald
journalctl --vacuum-size=20M >/dev/null 2>&1 || true

echo "======================================================"
echo " 4. ADVANCED SYSCTL KERNEL MEMORY & TCP TUNING        "
echo "======================================================"
cat <<'EOF' > /etc/sysctl.d/99-nso-tuning.conf
# Memory management (low memory server optimization)
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

sysctl -p /etc/sysctl.d/99-nso-tuning.conf >/dev/null 2>&1 || true

echo "======================================================"
echo " 5. CLEANING MEMORY & DROPPING UNUSED CACHES          "
echo "======================================================"
sync
echo 3 > /proc/sys/vm/drop_caches

echo "======================================================"
echo " 6. VERIFYING GAME SERVER & DATABASE STATUS           "
echo "======================================================"
systemctl is-active --quiet mariadb && echo "MariaDB: RUNNING (OK)" || echo "MariaDB: ERROR"
systemctl is-active --quiet nso-server && echo "NSO Server: RUNNING (OK)" || echo "NSO Server: ERROR"

echo ""
echo "=== RAM USAGE AFTER OS OPTIMIZATION ==="
free -h
echo ""
echo "=== DISK USAGE ==="
df -h /
echo ""
echo "=== TOP PROCESSES BY RAM ==="
ps -eo pid,user,%cpu,%mem,rss,comm --sort=-rss | head -n 10
