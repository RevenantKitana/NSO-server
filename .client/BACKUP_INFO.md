# BẢNG THÔNG TIN QUẢN LÝ CLIENT & BACKUPS

Tài liệu này ghi nhận chi tiết nguồn gốc, mã băm SHA256 và vị trí lưu trữ các bản Client JAR trong hệ thống.

---

## 1. Client Chính Thức Hiện Tại

| Tên Tệp | Vị Trí | Cổng Kết Nối | SHA-256 Checksum | Mô Tả |
| :--- | :--- | :---: | :--- | :--- |
| **`NSO.jar`** | `.client/` | **`14444`** | `3353D7E962697FA7556417987C5B69B29946AEC971D7DE1A6C884B265206B4D1` | Client nguyên bản chuẩn kết nối Server Legacy (`161.118.209.14:14444`). |

---

## 2. Kho Lưu Trữ Bản Backup Độc Lập (`backups/client/`)

Mọi file backup đều được lưu trữ an toàn, tách biệt tại thư mục `backups/client/`:

| Tên Tệp Backup | SHA-256 Checksum | Mô Tả & Nguồn Gốc |
| :--- | :--- | :--- |
| **`NSO_original_legacy_14444.jar`** | `3353D7E962697FA7556417987C5B69B29946AEC971D7DE1A6C884B265206B4D1` | Bản backup nguyên gốc chuẩn của Legacy Server 14444. |
| **`NSO_pristine_old_vm_202.174.jar.bak`** | `95B34B289032193794DDFFBF2164A2F8923E27CAA482AE95D31D566B173BC33F` | Bản backup từ máy chủ VM cũ (`161.118.202.174`). |
| **`NSO.jar.lg_bak`** | `E4B691654E1830693F6991EED6DF0BAB7E862EC6E0D494996BFA324F7A653806` | Bản lưu trữ phụ từ ngày 26/10/2026. |
| **`NSO.jar.sp_bak`** | `2CD104AFBF509CA9D4F655D69D6A4E93DAE40A9401CADB4F35443C5DBE3B8A7E` | Bản lưu trữ phụ từ ngày 26/10/2026. |

---

## 3. Công Cụ Đổi IP Client Linh Hoạt

Để trỏ client sang địa chỉ IP / Port khác:
```cmd
cd E:\TT\SETUP_LOCAL\SRC_GAME\.client
set_client_ip.cmd <IP_HOAC_DOMAIN> <PORT>
```
Ví dụ:
- Trỏ về Localhost: `set_client_ip.cmd 127.0.0.1 14444`
- Trỏ về Cloud VM: `set_client_ip.cmd 161.118.209.14 14444`
