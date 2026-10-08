#!/usr/bin/env bash
# ===============================================================================
# NSO Game Server - Factory Reset Script for VM
# Wipes all game data, databases, services, and returns VM to pristine clean state.
# ===============================================================================
set -e

echo "======================================================"
echo " 1. STOPPING & REMOVING SYSTEMD GAME SERVICE          "
echo "======================================================"
systemctl stop nso-server.service 2>/dev/null || true
systemctl disable nso-server.service 2>/dev/null || true
rm -f /etc/systemd/system/nso-server.service
systemctl daemon-reload
systemctl reset-failed 2>/dev/null || true

echo "======================================================"
echo " 2. WIPING MARIADB DATABASE & APPLICATION USERS       "
echo "======================================================"
mariadb -e "DROP DATABASE IF EXISTS nso_test;" 2>/dev/null || true
mariadb -e "DROP USER IF EXISTS 'nso_user'@'%', 'nso_user'@'localhost', 'nso_user'@'127.0.0.1';" 2>/dev/null || true
mariadb -e "DROP USER IF EXISTS 'nso_web'@'%', 'nso_web'@'localhost', 'nso_web'@'127.0.0.1';" 2>/dev/null || true
mariadb -e "FLUSH PRIVILEGES;" 2>/dev/null || true

echo "======================================================"
echo " 3. CLEANING FILESYSTEM & DIRECTORIES                 "
echo "======================================================"
rm -rf /home/ubuntu/nso-server
rm -rf /home/ubuntu/backups
rm -f /home/ubuntu/*.sh
rm -f /home/ubuntu/*.sql
rm -f /home/ubuntu/*.tar.gz /home/ubuntu/*.tar
rm -f /etc/logrotate.d/nso-server

# Clear user crontabs
crontab -r 2>/dev/null || true

echo "======================================================"
echo " 4. CLEARING LOGS & SYSTEM MEMORY                     "
echo "======================================================"
journalctl --vacuum-size=1M 2>/dev/null || true
sync
echo 3 > /proc/sys/vm/drop_caches

echo ""
echo "======================================================"
echo " [OK] VM HAS BEEN RESET TO CLEAN FACTORY STATE!       "
echo " You can now run option [7] in manage.cmd to setup A-Z."
echo "======================================================"
