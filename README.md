# NSO Game Server - Cloud VM Management Hub

Hệ thống quản lý, phát triển và vận hành NSO Game Server trên Cloud VM.

## 🚀 Quản trị hệ thống
Nhấp đúp vào **`manage.bat`** (ở thư mục gốc) để mở giao diện quản trị điều khiển:
- **1-Click Build & Deploy lên Cloud VM** (Tự động build -> backup jar cũ trên VM -> upload jar mới -> restart service).
- **Cài đặt toàn diện Cloud VM mới tinh từ A-Z** (Cài Java 17, MariaDB, Swap 4GB, Firewall, upload Data và nạp Database sạch).
- **Kiểm tra trạng thái & Điều khiển VM** (Xem live logs, restart, start, stop service).
- **Kết nối SSH Terminal** vào VM.
- **Giám sát tài nguyên VM thời gian thực** (CPU, RAM, Disk, Uptime).
- **Sao lưu Database từ VM về máy cá nhân** (lưu trữ trong `backups/`).
- **Mở Web Admin Quản trị** (`http://localhost:4000`) để phát Giftcode & Mã OTP.
- **Cấu hình IP / Port cho Game Client JAR** (`.client/`).

## 📁 Cấu trúc thư mục
- `manage.bat`: Bảng điều khiển quản trị viên duy nhất.
- `SKILLS_SYSTEM_OF_6_CLASSES_GUIDE.md`: **Tài liệu toàn diện về Hệ thống Kỹ Năng 6 Môn Phái (10x, 12x, 13x, Phân thân, Tâm pháp)**.
- `CHARACTER_STATS_AND_ITEM_OPTIONS_GUIDE.md`: **Tài liệu toàn diện về toàn bộ chỉ số Nhân vật & 162 Option Trang bị (ID 0 - 161)**.
- `DATABASE_AND_GAME_BALANCE_GUIDE.md`: **Hướng dẫn chi tiết về Cơ sở dữ liệu và Cân bằng Game**.
- `database/`: Database sạch chuẩn (`init_nso_clean.sql`) và script reset mùa/người chơi (`reset_player_data.sql`).
- `config/`: Cấu hình hệ thống tập trung (`server_config.ini`), file SSH key và file cấu hình production.
- `scripts/local/`: Script hỗ trợ trên máy cá nhân (setup JDK/Maven portable, tải backup DB, patch client).
- `scripts/remote-vm/`: Toàn bộ script cài đặt và vận hành trên máy chủ Linux VM (.sh, .service).
- `tools/admin-portal/`: Ứng dụng Web Admin Node.js quản lý Giftcode và OTP.
- `.client/`: Thư mục chứa client game JAR.
- `backups/`: Thư mục lưu các bản sao lưu database từ VM tải về.
