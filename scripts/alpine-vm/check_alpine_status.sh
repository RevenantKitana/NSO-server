#!/bin/sh
# ===============================================================================
# NSO Game Server - Alpine Linux Status Check Script (OpenRC)
# ===============================================================================

echo "==============================================================================="
echo "             KIEM TRA TRANG THAI NSO GAME SERVER (ALPINE LINUX)                "
echo "==============================================================================="
echo "  [He dieu hanh]  : $(grep PRETTY_NAME /etc/os-release 2>/dev/null | cut -d= -f2 | tr -d '\"')"
echo "  [Thoi gian VM]  : $(date '+%Y-%m-%d %H:%M:%S %Z')"
echo "  [May chu / Host]: $(hostname) (Uptime: $(uptime | awk -F'up ' '{print $2}' | awk -F',' '{print $1}'))"
echo "  [Tai CPU (Load)]: $(uptime | awk -F'load average:' '{print $2}' | sed 's/^ //')"
echo ""

echo "-------------------------------------------------------------------------------"
echo "  1. DICH VU NSO GAME SERVER (OpenRC: /etc/init.d/nso-server)"
echo "-------------------------------------------------------------------------------"
if rc-service nso-server status >/dev/null 2>&1; then
    echo "  >> OpenRC Service : [ DANG CHAY / STARTED ]"
    rc-service nso-server status 2>&1 | sed 's/^/     /'
else
    echo "  >> OpenRC Service : [ DA DUNG HOAC CHUA KHOI CHAY / STOPPED ]"
    rc-service nso-server status 2>&1 | sed 's/^/     /' || true
fi
echo ""

echo "-------------------------------------------------------------------------------"
echo "  2. TIEN TRINH JAVA GAME (Java Process + jemalloc)"
echo "-------------------------------------------------------------------------------"
JAVA_PID=$(pgrep -f "Nso-jar-with-dependencies.jar" | head -n 1 || true)
if [ -n "$JAVA_PID" ]; then
    echo "  >> Java Process   : [ ONLINE ] (PID: $JAVA_PID)"
    RSS_KB=$(awk '/^VmRSS:/{print $2}' /proc/"$JAVA_PID"/status 2>/dev/null || echo "0")
    RSS_MB=$((RSS_KB / 1024))
    THREADS=$(awk '/^Threads:/{print $2}' /proc/"$JAVA_PID"/status 2>/dev/null || echo "?")
    echo "     PID: $JAVA_PID | RSS Memory: ${RSS_MB} MB | Threads: $THREADS"
else
    echo "  >> Java Process   : [ OFFLINE - KHONG CO TIEN TRINH JAVA NSO CHAY ]"
fi
echo ""

echo "-------------------------------------------------------------------------------"
echo "  3. TRANG THAI CAC PORT MANG (Listening Ports)"
echo "-------------------------------------------------------------------------------"
if ss -tuln 2>/dev/null | grep -q ":14444 "; then
    echo "  >> Port Game (14444)     : [ ONLINE - DANG LANG NGHE VA SAN SANG KET NOI ]"
else
    echo "  >> Port Game (14444)     : [ OFFLINE - DONG SOCKET HOAC DANG BAO TRI ]"
fi

if ss -tuln 2>/dev/null | grep -q ":80 "; then
    echo "  >> Port HTTP Bridge (80) : [ ONLINE - EMBEDDED API BRIDGE DANG CHAY ]"
else
    echo "  >> Port HTTP Bridge (80) : [ OFFLINE - CHUA MO ]"
fi

if ss -tuln 2>/dev/null | grep -q ":3306 "; then
    echo "  >> MariaDB SQL (3306)    : [ ONLINE - DANG LANG NGHE NOI BO 127.0.0.1 ]"
else
    echo "  >> MariaDB SQL (3306)    : [ OFFLINE - DATABASE CHUA CHAY ]"
fi
echo ""

echo "-------------------------------------------------------------------------------"
echo "  4. TAI NGUYEN MAY CHU ALPINE (RAM / SWAP / DISK)"
echo "-------------------------------------------------------------------------------"
echo "  [RAM & SWAP]:"
free -h | sed 's/^/     /'
echo ""
echo "  [DUNG LUONG O DIA]:"
df -h / /opt 2>/dev/null | uniq | sed 's/^/     /'
echo ""

echo "-------------------------------------------------------------------------------"
echo "  5. NHAT KY LOG MOI NHAT (10 dong cuoi)"
echo "-------------------------------------------------------------------------------"
if [ -f /opt/nso-server/logs/service.log ] && [ -s /opt/nso-server/logs/service.log ]; then
    echo "  [File: /opt/nso-server/logs/service.log]"
    tr '\r' '\n' < /opt/nso-server/logs/service.log | grep -v '^[[:space:]]*$' | tail -n 10 | sed 's/^/     /'
elif [ -f /opt/nso-server/logs/service_error.log ] && [ -s /opt/nso-server/logs/service_error.log ]; then
    echo "  [File: /opt/nso-server/logs/service_error.log]"
    tr '\r' '\n' < /opt/nso-server/logs/service_error.log | grep -v '^[[:space:]]*$' | tail -n 10 | sed 's/^/     /'
else
    echo "     (Chua co du lieu log trong /opt/nso-server/logs/)"
fi
echo "==============================================================================="

