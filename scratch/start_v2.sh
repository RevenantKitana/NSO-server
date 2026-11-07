#!/bin/bash
cd /home/ubuntu/nso-server-v2
mkdir -p logs

PID=$(pgrep -f "Nso-v2.jar" || true)
if [ -n "$PID" ]; then
    echo "Killing existing V2 process ($PID)..."
    kill -9 $PID || true
    sleep 1
fi

nohup /usr/bin/java -server -Djava.awt.headless=true -Dfile.encoding=UTF-8 -Xms128M -Xmx320M -Xss192k -XX:+UseSerialGC -XX:TieredStopAtLevel=1 -XX:ReservedCodeCacheSize=32M -cp Nso-v2.jar com.nsoz2.server.NinjaSchool > logs/server_v2.log 2>&1 &
sleep 3

NEW_PID=$(pgrep -f "Nso-v2.jar" || true)
echo "Server V2 started with PID: $NEW_PID on port 14445 (Package: com.nsoz2)"
