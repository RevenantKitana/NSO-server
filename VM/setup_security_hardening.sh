#!/usr/bin/env bash
set -e

echo "=========================================================="
echo " 1. CAU HINH KERNEL LINUX CHONG DDOS & SYN FLOOD         "
echo "=========================================================="

cat << "EOF" | sudo tee /etc/sysctl.d/99-game-security.conf
# Chống tấn công SYN Flood
net.ipv4.tcp_syncookies = 1
net.ipv4.tcp_max_syn_backlog = 4096
net.ipv4.tcp_synack_retries = 2

# Chống IP Spoofing (giả mạo địa chỉ IP)
net.ipv4.conf.all.rp_filter = 1
net.ipv4.conf.default.rp_filter = 1

# Bỏ qua gói tin ping broadcast (chống Smurf Attack)
net.ipv4.icmp_echo_ignore_broadcasts = 1

# Giảm thời gian chờ kết nối đóng để giải phóng RAM nhanh
net.ipv4.tcp_fin_timeout = 15
net.ipv4.tcp_tw_reuse = 1
EOF

sudo sysctl -p /etc/sysctl.d/99-game-security.conf >/dev/null

echo "=========================================================="
echo " 2. CAU HINH IPTABLES RATE-LIMIT CHO CONG GAME (14444)   "
echo "=========================================================="

# Đảm bảo iptables-persistent có sẵn
sudo apt-get update -qq && sudo apt-get install -y -qq iptables-persistent fail2ban

# Xóa các rules rate-limit cũ nếu có
sudo iptables -D INPUT -p tcp --dport 14444 -m state --state NEW -m recent --set --name GAME_LIMIT 2>/dev/null || true
sudo iptables -D INPUT -p tcp --dport 14444 -m state --state NEW -m recent --update --seconds 10 --hitcount 20 --name GAME_LIMIT -j DROP 2>/dev/null || true

# Thêm rule giới hạn mỗi IP tối đa 20 kết nối mới trong 10 giây vào cổng game 14444
sudo iptables -I INPUT 1 -p tcp --dport 14444 -m state --state NEW -m recent --set --name GAME_LIMIT
sudo iptables -I INPUT 2 -p tcp --dport 14444 -m state --state NEW -m recent --update --seconds 10 --hitcount 20 --name GAME_LIMIT -j DROP

# Giới hạn cổng SSH 22 (chống brute force)
sudo iptables -D INPUT -p tcp --dport 22 -m state --state NEW -m recent --set --name SSH_LIMIT 2>/dev/null || true
sudo iptables -D INPUT -p tcp --dport 22 -m state --state NEW -m recent --update --seconds 60 --hitcount 6 --name SSH_LIMIT -j DROP 2>/dev/null || true
sudo iptables -I INPUT 3 -p tcp --dport 22 -m state --state NEW -m recent --set --name SSH_LIMIT
sudo iptables -I INPUT 4 -p tcp --dport 22 -m state --state NEW -m recent --update --seconds 60 --hitcount 6 --name SSH_LIMIT -j DROP

# Lưu iptables vĩnh viễn
sudo netfilter-persistent save >/dev/null 2>&1 || true

echo "=========================================================="
echo " 3. KICH HOAT FAIL2BAN TU DONG KHOA IP TAN CONG          "
echo "=========================================================="

cat << "EOF" | sudo tee /etc/fail2ban/jail.local
[DEFAULT]
bantime = 86400
findtime = 600
maxretry = 5

[sshd]
enabled = true
port = 22
mode = aggressive
EOF

sudo systemctl restart fail2ban
sudo systemctl enable fail2ban >/dev/null 2>&1

echo "=========================================================="
echo " HOAN TAT CAU HINH BAO MAT DA TANG CHO VM!               "
echo "=========================================================="
