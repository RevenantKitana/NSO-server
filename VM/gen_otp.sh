#!/usr/bin/env bash
set -e

# Auto purge expired OTPs
mariadb -u root nso_test -e "
DELETE FROM registration_otps WHERE expires_at < NOW();
" 2>/dev/null || true

# Generate a random 6-digit number (100000 - 999999)
OTP=$(shuf -i 100000-999999 -n 1)

# Expire after 90 minutes
mariadb -u root nso_test -e "
INSERT INTO registration_otps (code, created_at, expires_at, used)
VALUES ('$OTP', NOW(), DATE_ADD(NOW(), INTERVAL 90 MINUTE), 0);
"

EXPIRY=$(mariadb -u root nso_test -N -e "SELECT DATE_FORMAT(DATE_ADD(NOW(), INTERVAL 90 MINUTE), '%Y-%m-%d %H:%i:%s');")

echo "============================================================"
echo "           MA DANG KY MOI (OTP AUTHORIZATION)              "
echo "============================================================"
echo "  MA OTP      :  $OTP"
echo "  THOI HAN    :  90 PHUT (Het han luc: $EXPIRY)"
echo "  SO LAN DUNG :  1 LAN DUY NHAT"
echo "============================================================"
echo "Gui ma nay cho nguoi choi de dang ky tren trang Web."
