# Hướng dẫn Quản trị NSO Server

## 1. Trình quản lý All-in-One
Chạy file `manage.bat` ở thư mục gốc để mở Dashboard điều khiển toàn diện:
- **Local:** Build server, chạy local, đổi IP client, cài đặt portable JDK/Maven.
- **Cloud VM:** 1-click build & deploy, bảng điều khiển VM (status, live log, restart), kết nối SSH, monitor tài nguyên, backup database, mở trang web quản trị Giftcode/OTP, cài đặt VM mới tinh từ A-Z (Bootstrap Full).

## 2. Cấu hình hệ thống tập trung
File cấu hình: `config/server_config.ini`
- `VM_IP`: Địa chỉ IP của máy chủ Cloud.
- `VM_USER`: Tên user SSH (mặc định `ubuntu`).
- `VM_PORT`: Cổng game server (mặc định `14444`).
- `SSH_KEY`: Đường dẫn file SSH private key.
- `SERVER_PROFILE`: Profile cấu hình và JVM (`LOW_RESOURCE`, `STANDARD`, `AUTO`).
  + `LOW_RESOURCE`: Tối ưu đặc biệt cho VM 1 CPU / 1-2GB RAM (SerialGC, Heap 256M-512M, Stack 256k, ThreadPool nhỏ).
  + `STANDARD`: Dành cho VPS 4GB+ RAM (G1GC, Heap 1024M-2048M, hiệu năng cao).
  + `AUTO`: Tự động nhận diện RAM VM để sinh cấu hình thích ứng.

## 3. Cấu trúc thư mục
- `database/`: Chứa file database game sạch (`init_nso_clean.sql`) và script reset mùa/người chơi (`reset_player_data.sql`).
- `config/`: Cấu hình hệ thống, file SSH Key và template properties production.
- `scripts/local/`: Script hỗ trợ trên máy tính cá nhân (setup tools, backup db).
- `scripts/remote-vm/`: Script quản trị trên máy chủ Linux VM (.sh, .service).
- `tools/admin-portal/`: Web Admin Node.js quản lý Giftcode và mã OTP.
- `.client/`: File Game Client JAR và công cụ patch IP/Port.
- `backups/`: Thư mục chứa các bản sao lưu Database từ VM tải về.
