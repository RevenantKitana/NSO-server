#!/usr/bin/env bash
set -e

# Configure MariaDB to listen on 0.0.0.0
sed -i "s/bind-address = 127.0.0.1/bind-address = 0.0.0.0/g" /etc/mysql/mariadb.conf.d/60-nso-tuning.cnf
systemctl restart mariadb

# Create remote web user with secure privileges
mariadb -u root -e "
CREATE USER IF NOT EXISTS 'nso_web'@'%' IDENTIFIED BY 'NsoWebDb2026!@#';
ALTER USER 'nso_web'@'%' IDENTIFIED BY 'NsoWebDb2026!@#';
GRANT SELECT, INSERT, UPDATE, DELETE ON nso_test.* TO 'nso_web'@'%';
FLUSH PRIVILEGES;
"

# Open port 3306 on firewall
iptables -I INPUT 1 -p tcp --dport 3306 -j ACCEPT
iptables-save > /etc/iptables/rules.v4 2>/dev/null || true
ufw allow 3306/tcp comment "MariaDB Remote for Vercel" 2>/dev/null || true

echo "MARIADB_VERCEL_READY"
