#!/bin/bash
PID=$(pgrep -f "Nso-v2.jar" || true)
if [ -n "$PID" ]; then
    echo "Stopping V2 process ($PID)..."
    kill -15 $PID || true
    sleep 2
    if pgrep -f "Nso-v2.jar" > /dev/null; then
        kill -9 $PID || true
    fi
    echo "V2 stopped."
else
    echo "V2 not running."
fi

