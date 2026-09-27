#!/usr/bin/env bash
set -e

echo "======================================================"
echo " 1. SYSTEM TUNING (SWAP & SYSCTL FOR 1 CPU / 1GB RAM) "
echo "======================================================"

# Create 4GB Swap if not exists
if [ ! -f /swapfile ]; then
    echo "Creating 4G swap file..."
    fallocate -l 4G /swapfile || dd if=/dev/zero of=/swapfile bs=1M count=4096
    chmod 600 /swapfile
    mkswap /swapfile
    swapon /swapfile
    if ! grep -q '/swapfile' /etc/fstab; then
        echo '/swapfile none swap sw 0 0' >> /etc/fstab
    fi
fi

# Sysctl tuning
cat <<'EOF' > /etc/sysctl.d/99-nso-tuning.conf
vm.swappiness=10
vm.vfs_cache_pressure=50
fs.file-max=2097152
net.core.somaxconn=4096
net.ipv4.tcp_tw_reuse=1
net.ipv4.tcp_fin_timeout=15
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
echo " 2. INSTALLING PACKAGES (JAVA 17 & MARIADB)           "
echo "======================================================"
# Fix any corrupted apt config if present
if [ -f /etc/apt/apt.conf.d/20auto-upgrades ]; then
    cat <<'EOF' > /etc/apt/apt.conf.d/20auto-upgrades
APT::Periodic::Update-Package-Lists "0";
APT::Periodic::Download-Upgradeable-Packages "0";
APT::Periodic::AutocleanInterval "0";
APT::Periodic::Unattended-Upgrade "0";
EOF
fi

export DEBIAN_FRONTEND=noninteractive
apt-get update -y
apt-get install -y openjdk-17-jre-headless mariadb-server mariadb-client ufw htop rsync curl unzip

echo "======================================================"
echo " 3. CONFIGURING MARIADB FOR 1 CPU / LOW RAM          "
echo "======================================================"
cat <<'EOF' > /etc/mysql/mariadb.conf.d/60-nso-tuning.cnf
[mysqld]
performance_schema = OFF
innodb_buffer_pool_size = 128M
innodb_log_buffer_size = 8M
innodb_read_io_threads = 2
innodb_write_io_threads = 2
max_connections = 50
key_buffer_size = 16M
table_open_cache = 400
thread_cache_size = 8
bind-address = 127.0.0.1
EOF

systemctl restart mariadb
systemctl enable mariadb

# Setup database & user
mariadb -e "CREATE DATABASE IF NOT EXISTS nso_test CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"
mariadb -e "CREATE USER IF NOT EXISTS 'nso_user'@'localhost' IDENTIFIED BY 'NsoGame2026!@#';"
mariadb -e "GRANT ALL PRIVILEGES ON nso_test.* TO 'nso_user'@'localhost';"
mariadb -e "FLUSH PRIVILEGES;"

echo "======================================================"
echo " 4. FIREWALL SETUP                                    "
echo "======================================================"
ufw allow 22/tcp comment 'SSH'
ufw allow 14444/tcp comment 'NSO Game Port'
ufw allow 8020/tcp comment 'NSO Web / API'
ufw --force enable

echo "======================================================"
echo " 5. ENVIRONMENT READY                                 "
echo "======================================================"
mkdir -p /home/ubuntu/nso-server
chown -R ubuntu:ubuntu /home/ubuntu/nso-server

free -h
echo "Setup completed successfully."
