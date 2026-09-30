#!/usr/bin/env bash
set -e

echo "======================================================"
echo " PERFORMING COMPLETE FACTORY OS RESET ON VM           "
echo "======================================================"

# 1. Stop and remove services
systemctl stop nso-server.service mariadb fail2ban 2>/dev/null || true
systemctl disable nso-server.service mariadb fail2ban 2>/dev/null || true
rm -f /etc/systemd/system/nso-server.service
systemctl daemon-reload

# 2. Swapoff and remove swapfile
swapoff /swapfile 2>/dev/null || true
rm -f /swapfile
sed -i '/\/swapfile/d' /etc/fstab 2>/dev/null || true

# 3. Purge packages
export DEBIAN_FRONTEND=noninteractive
apt-get purge -y "openjdk-17*" "mariadb*" "mysql*" fail2ban iptables-persistent netfilter-persistent 2>/dev/null || true
apt-get autoremove -y --purge 2>/dev/null || true
rm -rf /var/lib/mysql /etc/mysql /etc/fail2ban

# 4. Remove custom kernel & limit configs
rm -f /etc/sysctl.d/99-nso-tuning.conf /etc/sysctl.d/99-game-security.conf
rm -f /etc/security/limits.d/99-nso-limits.conf
rm -f /etc/systemd/journald.conf.d/99-nso-journald.conf /etc/systemd/journald.conf.d/99-cap-size.conf
rm -f /etc/logrotate.d/nso-server
rm -f /etc/apt/apt.conf.d/20auto-upgrades /etc/apt/apt.conf.d/10periodic
sysctl --system >/dev/null 2>&1 || true

# 5. Reset Firewall (Flush rules, ensure default ACCEPT)
iptables -P INPUT ACCEPT
iptables -P FORWARD ACCEPT
iptables -P OUTPUT ACCEPT
iptables -F
iptables -X
rm -rf /etc/iptables

# 6. Unmask background services
systemctl unmask fwupd snapd packagekit motd-news unattended-upgrades apt-daily.timer apt-daily-upgrade.timer 2>/dev/null || true

# 7. Clean directories and cronjobs
crontab -u ubuntu -r 2>/dev/null || true
crontab -r 2>/dev/null || true
rm -rf /home/ubuntu/nso-server /home/ubuntu/check_status.sh /tmp/setup_vm.sh /tmp/optimize_os.sh /tmp/nso-server.service

echo ""
echo "=== MEMORY & SWAP AFTER FACTORY RESET ==="
free -h
echo ""
echo "=== ACTIVE PORTS ==="
ss -tuln
echo ""
echo "OS FACTORY RESET COMPLETED 100%!"
