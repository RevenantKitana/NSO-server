#!/usr/bin/env bash
set -e

BACKUP_SOURCE_DIR="/home/ubuntu/nso-server/backups/sources"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_ARCHIVE="${BACKUP_SOURCE_DIR}/source_backup_${TIMESTAMP}.tar.gz"

mkdir -p "$BACKUP_SOURCE_DIR"

echo "=================================================="
echo " 1. SAO LUU SOURCE & RUNTIME HIEN TAI (MAX 3 BAN) "
echo "=================================================="
echo "Dang tao ban sao luu: $BACKUP_ARCHIVE ..."
tar -czf "$BACKUP_ARCHIVE" \
    -C /home/ubuntu/nso-server \
    Nso-jar-with-dependencies.jar config.properties mysql.properties Data \
    2>/dev/null || true

# Xoay vong toi da 3 ban backup source moi nhat
OLD_SOURCE_BACKUPS=$(ls -1t "$BACKUP_SOURCE_DIR"/source_backup_*.tar.gz 2>/dev/null | tail -n +4)
if [ -n "$OLD_SOURCE_BACKUPS" ]; then
    echo "$OLD_SOURCE_BACKUPS" | while read -r old_file; do
        rm -f "$old_file"
        echo "Da xoa ban backup source cu: $(basename "$old_file")"
    done
fi
CURRENT_COUNT=$(ls -1 "$BACKUP_SOURCE_DIR"/source_backup_*.tar.gz 2>/dev/null | wc -l)
echo "Da sao luu thanh cong! So ban backup source hien co: $CURRENT_COUNT/3"

echo "=================================================="
echo " 2. PULLING LATEST CODE AND ASSETS FROM GITHUB    "
echo "=================================================="
cd /home/ubuntu/src
git fetch origin main
git reset --hard origin/main

echo "=================================================="
echo " 3. SYNCING DATA ASSETS (MAP, LANG, IMG)          "
echo "=================================================="
if [ -d /home/ubuntu/src/Data ]; then
    cp -rf /home/ubuntu/src/Data/* /home/ubuntu/nso-server/Data/ 2>/dev/null || true
fi

echo "=================================================="
echo " 4. COMPILING AND BUILDING JAR WITH MAVEN         "
echo "=================================================="
mvn clean package -DskipTests

echo "=================================================="
echo " 5. UPDATING RUNTIME JAR AND RESTARTING SERVICE   "
echo "=================================================="
cp -f target/Nso-jar-with-dependencies.jar /home/ubuntu/nso-server/Nso-jar-with-dependencies.jar
sudo systemctl restart nso-server.service

echo "=================================================="
echo " 6. CHECKING SERVICE STATUS                       "
echo "=================================================="
sleep 3
sudo systemctl status nso-server.service --no-pager
echo "Update completed successfully via Git with source backup preserved!"
