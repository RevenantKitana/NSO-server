#!/usr/bin/env bash
set -e

cd /home/ubuntu/nso-server

echo "1. Extracting Data..."
unzip -o Data.zip
rm -f Data.zip

echo "2. Applying Configs..."
mv -f config.properties.prod config.properties
mv -f mysql.properties.prod mysql.properties

mkdir -p logs

echo "3. Importing Database..."
mariadb -u root nso_test < nso_test.sql
echo "Database imported successfully."

echo "4. Setting up Systemd Service..."
sudo cp -f nso-server.service /etc/systemd/system/nso-server.service
sudo systemctl daemon-reload
sudo systemctl enable nso-server.service

echo "5. Starting NSO Game Server Service..."
sudo systemctl restart nso-server.service

echo "6. Waiting 5s for startup..."
sleep 5

echo "7. Service Status:"
sudo systemctl status nso-server.service --no-pager

echo "8. Checking Listening Ports:"
sudo ss -tulnp | grep -E '14444|8020|3306' || true

echo "9. Resource Usage:"
free -h
