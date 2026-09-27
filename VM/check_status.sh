#!/usr/bin/env bash
# ===============================================================================
# NSO Game Server - Script Kiem Tra Trang Thai Tong Quan
# ===============================================================================

echo "==============================================================================="
echo "                KIEM TRA TRANG THAI NSO GAME SERVER TRÊN VM                    "
echo "==============================================================================="
echo "  [Thoi gian VM]  : $(date '+%Y-%m-%d %H:%M:%S %Z')"
echo "  [May chu / Host]: $(hostname) (Uptime: $(uptime -p 2>/dev/null || uptime))"
echo "  [Tai CPU (Load)]: $(uptime | awk -F'load average:' '{print $2}' | sed 's/^ //')"
echo ""

echo "-------------------------------------------------------------------------------"
echo "  1. DICH VU NSO GAME SERVER (systemd: nso-server.service)"
echo "-------------------------------------------------------------------------------"
if systemctl is-active --quiet nso-server.service; then
    echo "  >> Systemd Service: [ DANG CHAY / ACTIVE (RUNNING) ]"
    sudo systemctl status nso-server.service --no-pager | grep -E "Active:|Main PID:|Tasks:|Memory:|CPU:" | sed 's/^/     /'
else
    echo "  >> Systemd Service: [ DA DUNG HOAC CO LOI / INACTIVE / FAILED ]"
    sudo systemctl status nso-server.service --no-pager | head -n 8 | sed 's/^/     /'
fi
echo ""

echo "-------------------------------------------------------------------------------"
echo "  2. TIEN TRINH JAVA GAME (Java Process)"
echo "-------------------------------------------------------------------------------"
JAVA_PID=$(pgrep -f "Nso-jar-with-dependencies.jar" || true)
if [ -n "$JAVA_PID" ]; then
    echo "  >> Java Process   : [ ONLINE ] (PID: $JAVA_PID)"
    ps -p "$JAVA_PID" -o pid,user,%cpu,%mem,vsz,rss,etime,cmd --no-headers | awk '{printf "     PID: %s | User: %s | CPU: %s%% | RAM: %s%% | Uptime: %s\n", $1, $2, $3, $4, $7}'
else
    echo "  >> Java Process   : [ OFFLINE - KHONG CO TIEN TRINH JAVA NSO CHAY ]"
fi
echo ""

echo "-------------------------------------------------------------------------------"
echo "  3. TRANG THAI CAC PORT MANG (Listening Ports)"
echo "-------------------------------------------------------------------------------"
# Check Game Port 14444
if sudo ss -tuln | grep -q ":14444 "; then
    echo "  >> Port Game (14444)     : [ ONLINE - DANG LANG NGHE VA SAN SANG KET NOI ]"
else
    echo "  >> Port Game (14444)     : [ OFFLINE - DONG SOCKET HOAC DANG BAO TRI ]"
fi

# Check Web/API Port 8020
if sudo ss -tuln | grep -q ":8020 "; then
    echo "  >> Port Web/API (8020)   : [ ONLINE - DANG LANG NGHE ]"
else
    echo "  >> Port Web/API (8020)   : [ OFFLINE - CHUA MO ]"
fi

# Check MariaDB Port 3306
if sudo ss -tuln | grep -q ":3306 "; then
    echo "  >> MariaDB SQL (3306)    : [ ONLINE - DANG LANG NGHE ]"
else
    echo "  >> MariaDB SQL (3306)    : [ OFFLINE - DATABASE CHUA CHAY ]"
fi
echo ""

echo "-------------------------------------------------------------------------------"
echo "  4. TAI NGUYEN MAY CHU (RAM / SWAP / DISK)"
echo "-------------------------------------------------------------------------------"
echo "  [RAM & SWAP]:"
free -h | sed 's/^/     /'
echo ""
echo "  [DUNG LUONG O DIA]:"
df -h / | sed 's/^/     /'
echo ""

echo "-------------------------------------------------------------------------------"
echo "  5. NHAT KY LOG MOI NHAT (10 dong cuoi)"
echo "-------------------------------------------------------------------------------"
if [ -f /home/ubuntu/nso-server/logs/service.log ]; then
    echo "  [File: /home/ubuntu/nso-server/logs/service.log]"
    tail -n 10 /home/ubuntu/nso-server/logs/service.log | sed 's/^/     /'
elif [ -f /home/ubuntu/nso-server/logs/service_error.log ]; then
    echo "  [File: /home/ubuntu/nso-server/logs/service_error.log]"
    tail -n 10 /home/ubuntu/nso-server/logs/service_error.log | sed 's/^/     /'
else
    sudo journalctl -u nso-server.service -n 10 --no-pager | sed 's/^/     /'
fi
echo "==============================================================================="
