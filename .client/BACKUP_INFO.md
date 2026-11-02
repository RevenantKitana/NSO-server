# BẢNG THÔNG TIN QUẢN LÝ CLIENT & FILE BACKUP

Tài liệu này ghi nhận chi tiết nguồn gốc, hàm băm SHA256 và mục đích kết nối của từng tệp Client JAR trong hệ thống dual-stack.

---

## 1. Danh Sách Tệp Client Hiện Có

| Tên Tệp | Vị Trí | Cổng Kết Nối | SHA-256 Checksum | Mô Tả & Mục Đích |
| :--- | :--- | :---: | :--- | :--- |
| **`NSO_rework_v2.jar`** | `.client/`<br>`PB/`<br>`web/public/` | **`14445`** | `2DBC5C37532733CD8F616DA340159BE1B1BF4507088DF191F49EECCEE3851532` | **Client chính thức cho phiên bản Rework V2 Clean Architecture.** |
| **`NSO.jar`** (Hiện tại) | `.client/`<br>`web/public/` | **`14445`** | `2DBC5C37532733CD8F616DA340159BE1B1BF4507088DF191F49EECCEE3851532` | Tệp mặc định trong thư mục `.client`, đã trỏ về Server V2 Rework. |
| **`NSO_legacy_v1.jar`** | `.client/`<br>`PB/` | **`14444`** | `3353D7E962697FA7556417987C5B69B29946AEC971D7DE1F4020993AE18EC256` | Client nguyên bản chạy song song cho Server Legacy V1 (port 14444). |
| **`NSO.jar.original_legacy_14444.bak`** | `.client/`<br>`backups/client/` | **`14444`** | `3353D7E962697FA7556417987C5B69B29946AEC971D7DE1F4020993AE18EC256` | **Bản BACKUP ĐỘC LẬP nguyên bản** của client legacy trước khi patch rework. |
| **`NSO.jar.bak`** | `.client/` | `14444` (VM cũ) | `95B34B289032193794DDFFBF2164A2F8923E27CAA482AE95D31D566B173BC33F` | Bản backup cũ từ trước (VM `161.118.202.174`), được bảo toàn nguyên vẹn 100%. |

---

## 2. Hướng Dẫn Sử Dụng Nhanh

1. **Chơi bản Rework (Server V2 - Cổng 14445):**
   - Mở giả lập MicroEmulator (`E:\TT\SETUP_LOCAL\PB\Micro_AngelChip.zip`) hoặc KEmulator / J2ME Loader.
   - Chọn tệp: `E:\TT\SETUP_LOCAL\SRC_GAME\.client\NSO_rework_v2.jar` (hoặc `E:\TT\SETUP_LOCAL\PB\NSO_rework_v2.jar`).

2. **Chơi bản Legacy cũ (Server V1 - Cổng 14444):**
   - Chọn tệp: `E:\TT\SETUP_LOCAL\SRC_GAME\.client\NSO_legacy_v1.jar` (hoặc `E:\TT\SETUP_LOCAL\PB\NSO_legacy_v1.jar`).

3. **Chuyển đổi IP/Port linh hoạt qua script:**
   - Chạy lệnh:
     ```cmd
     cd E:\TT\SETUP_LOCAL\SRC_GAME\.client
     set_client_ip.cmd rework   REM Chuyển sang Server V2 (14445)
     set_client_ip.cmd legacy   REM Chuyển sang Server V1 (14444)
     set_client_ip.cmd restore  REM Phục hồi nguyên bản từ backup độc lập
     ```
