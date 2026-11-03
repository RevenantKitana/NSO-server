#!/bin/bash
cd /home/ubuntu/nso-server-v2
mkdir -p logs

PID=$(pgrep -f "Nso-v2.jar")
if [ -n "$PID" ]; then
    echo "Killing existing V2 process ($PID)..."
    kill -9 $PID
    sleep 1
fi

nohup /usr/bin/java -server -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Xms128M -Xmx384M -Xss256k -jar Nso-v2.jar > logs/server_v2.log 2>&1 &
sleep 2

NEW_PID=$(pgrep -f "Nso-v2.jar")
echo "Server V2 Refactor started with PID: $NEW_PID on port 14445"

