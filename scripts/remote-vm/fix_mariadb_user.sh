#!/usr/bin/env bash
set -e

echo "=== FIXING MARIADB USERS & PERMISSIONS ==="

# 1. Fix Game Server user (nso_user) for all hosts (%, localhost, 127.0.0.1)
mariadb -e "CREATE USER IF NOT EXISTS 'nso_user'@'%' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -e "ALTER USER 'nso_user'@'%' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_user'@'%';"

mariadb -e "CREATE USER IF NOT EXISTS 'nso_user'@'localhost' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -e "ALTER USER 'nso_user'@'localhost' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_user'@'localhost';"

mariadb -e "CREATE USER IF NOT EXISTS 'nso_user'@'127.0.0.1' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -e "ALTER USER 'nso_user'@'127.0.0.1' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_user'@'127.0.0.1';"

# 2. Fix Web Server user (nso_web) for all hosts
mariadb -e "CREATE USER IF NOT EXISTS 'nso_web'@'%' IDENTIFIED BY 'NsoWebDb2026!@#';"
mariadb -e "ALTER USER 'nso_web'@'%' IDENTIFIED BY 'NsoWebDb2026!@#';"
mariadb -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_web'@'%';"

mariadb -e "CREATE USER IF NOT EXISTS 'nso_web'@'localhost' IDENTIFIED BY 'NsoWebDb2026!@#';"
mariadb -e "ALTER USER 'nso_web'@'localhost' IDENTIFIED BY 'NsoWebDb2026!@#';"
mariadb -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_web'@'localhost';"

mariadb -e "CREATE USER IF NOT EXISTS 'nso_web'@'127.0.0.1' IDENTIFIED BY 'NsoWebDb2026!@#';"
mariadb -e "ALTER USER 'nso_web'@'127.0.0.1' IDENTIFIED BY 'NsoWebDb2026!@#';"
mariadb -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_web'@'127.0.0.1';"

mariadb -e "FLUSH PRIVILEGES;"

echo "=== RESTARTING NSO-SERVER SERVICE ==="
systemctl restart nso-server.service
sleep 3
systemctl status nso-server.service --no-pager

echo ""
echo "=== ACTIVE LISTENING PORTS ==="
ss -tuln
