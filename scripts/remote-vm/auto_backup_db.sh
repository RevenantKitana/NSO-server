#!/usr/bin/env bash
set -e

# ==========================================================
# AUTO DATABASE BACKUP SCRIPT FOR NSO SERVER
# Schedule: Every 12 hours | Max Retention: 7 Backups
# ==========================================================

BACKUP_DIR="/home/ubuntu/nso-server/data/backup"
LOG_DIR="/home/ubuntu/nso-server/logs"
DB_NAME="nso_test"
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
BACKUP_FILE="${BACKUP_DIR}/nso_backup_${TIMESTAMP}.sql.gz"

mkdir -p "$BACKUP_DIR"
mkdir -p "$LOG_DIR"

echo "[$(date '+%Y-%m-%d %H:%M:%S')] Starting automatic database backup..." >> "$LOG_DIR/auto_backup.log"

# Dump and compress
if sudo mariadb-dump --single-transaction --routines --triggers "$DB_NAME" | gzip > "$BACKUP_FILE"; then
    BACKUP_SIZE=$(du -h "$BACKUP_FILE" | cut -f1)
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] Backup created: $BACKUP_FILE ($BACKUP_SIZE)" >> "$LOG_DIR/auto_backup.log"
else
    echo "[$(date '+%Y-%m-%d %H:%M:%S')] ERROR: Failed to dump database!" >> "$LOG_DIR/auto_backup.log"
    exit 1
fi

# Rotate backups: Keep only the 7 most recent backups
OLD_BACKUPS=$(ls -1t "$BACKUP_DIR"/nso_backup_*.sql.gz 2>/dev/null | tail -n +8)
if [ -n "$OLD_BACKUPS" ]; then
    echo "$OLD_BACKUPS" | while read -r file; do
        rm -f "$file"
        echo "[$(date '+%Y-%m-%d %H:%M:%S')] Purged old backup: $file" >> "$LOG_DIR/auto_backup.log"
    done
fi

TOTAL_REMAINING=$(ls -1 "$BACKUP_DIR"/nso_backup_*.sql.gz 2>/dev/null | wc -l)
echo "[$(date '+%Y-%m-%d %H:%M:%S')] Auto backup completed successfully. Total active backups: $TOTAL_REMAINING/7" >> "$LOG_DIR/auto_backup.log"

# ==========================================================
# AUTO LOG CAPPING & TRUNCATION (PREVENT UNLIMITED LOGS)
# Keep each log file under 20MB / max 30,000 lines
# ==========================================================
for logfile in "$LOG_DIR"/*.log; do
    if [ -f "$logfile" ]; then
        LOG_SIZE_KB=$(du -k "$logfile" | cut -f1)
        if [ "$LOG_SIZE_KB" -gt 20480 ]; then
            tail -n 30000 "$logfile" > "${logfile}.tmp" && mv -f "${logfile}.tmp" "$logfile"
            echo "[$(date '+%Y-%m-%d %H:%M:%S')] Truncated oversized log file: $logfile" >> "$LOG_DIR/auto_backup.log"
        fi
    fi
done

