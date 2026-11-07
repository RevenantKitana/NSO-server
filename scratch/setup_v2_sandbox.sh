#!/bin/bash
set -e

echo "=== 1. Checking Legacy Server (Port 14444) ==="
LEGACY_PID=$(pgrep -f "/home/ubuntu/nso-server/Nso-jar-with-dependencies.jar" || true)
echo "Legacy Server running with PID: $LEGACY_PID (DO NOT TOUCH)"

echo "=== 2. Creating /home/ubuntu/nso-server-v2 ==="
mkdir -p /home/ubuntu/nso-server-v2/logs
ln -sfn /home/ubuntu/nso-server/Data /home/ubuntu/nso-server-v2/Data

echo "=== 3. Configuring config.properties ==="
sed -e 's/server.id=1/server.id=2/' \
    -e 's/server.port=14444/server.port=14445/' \
    -e 's/websocket.port=8020/websocket.port=8021/' \
    -e 's/server.rmi.port=10000/server.rmi.port=10001/' \
    /home/ubuntu/nso-server/config.properties > /home/ubuntu/nso-server-v2/config.properties

echo "=== 4. Configuring mysql.properties ==="
sed -e 's/nsoz.database.name=nso_test/nsoz.database.name=nso_v2/' \
    /home/ubuntu/nso-server/mysql.properties > /home/ubuntu/nso-server-v2/mysql.properties

echo "=== 5. Cloning Database nso_test -> nso_v2 ==="
sudo mariadb -e "CREATE DATABASE IF NOT EXISTS nso_v2 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
sudo mysqldump nso_test | sudo mariadb nso_v2

echo "=== 6. Ensuring Admin Account for V2 Sandbox ==="
cat << 'SQL_EOF' | sudo mariadb nso_v2
INSERT INTO users (id, username, password, role) 
VALUES (5, 'admin', '$2a$12$wjvakMxNHH5Ap.v.hxhA6.paq/ZEieweuXrlS0fvLJBqYuQuUi0LC', 99) 
ON DUPLICATE KEY UPDATE password = VALUES(password), role = VALUES(role);

INSERT INTO players (id, user_id, server_id, name, gender, class, data, map) 
VALUES (8, 5, 2, 'admin_v2', 1, 0, '[]', '[22,1741,264]') 
ON DUPLICATE KEY UPDATE server_id = 2, map = '[22,1741,264]';
SQL_EOF

echo "=== 7. Creating start.sh ==="
cat << 'START_EOF' > /home/ubuntu/nso-server-v2/start.sh
#!/bin/bash
cd /home/ubuntu/nso-server-v2
mkdir -p logs

PID=$(pgrep -f "Nso-v2.jar" || true)
if [ -n "$PID" ]; then
    echo "Killing existing V2 process ($PID)..."
    kill -9 $PID || true
    sleep 1
fi

nohup /usr/bin/java -server -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Xms128M -Xmx384M -Xss256k -jar Nso-v2.jar > logs/server_v2.log 2>&1 &
sleep 2

NEW_PID=$(pgrep -f "Nso-v2.jar" || true)
echo "Server V2 started with PID: $NEW_PID on port 14445"
START_EOF

chmod +x /home/ubuntu/nso-server-v2/start.sh

echo "=== Setup completed successfully! ==="

