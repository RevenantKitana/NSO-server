# NSO WEB PORTAL - VERCEL READY (API BRIDGE ARCHITECTURE)

Trang web đăng ký tài khoản & hiển thị Giftcode dành cho máy chủ **NSO Ninja School Private 2026**.

---

## 🌟 Tính Năng Chính
1. **Kiến trúc Bảo Mật HTTP API Bridge (Zero Open MySQL Port)**:
   - **Port 3306 đóng kín 100%** trên máy chủ VM.
   - Vercel giao tiếp với VM thông qua API Bridge bảo mật bằng `x-bridge-token`.
   - Hỗ trợ Cloudflare Proxy (Đám mây cam 🟠) để **giấu hoàn toàn IP thật** của máy chủ.
2. **Đăng ký tài khoản bảo mật bằng mã OTP 6 số**:
   - Yêu cầu mã OTP 6 số do Quản trị viên cấp phép trực tiếp từ SSH backend.
   - Mật khẩu được mã hóa chuẩn **BCrypt Cost 12** tương thích 100% với Server Game.
3. **Hiển thị Giftcode công khai & Phần thưởng**:
   - Danh sách giftcode đang hoạt động trực tiếp từ Database.
   - Nút 1-click Sao chép mã tiện lợi.
4. **Trạng thái Máy Chủ thời gian thực**:
   - Tự động hiển thị trạng thái `ONLINE / BẢO TRÌ`, số lượng tài khoản và người chơi đang online.

---

## 🚀 Hướng Dẫn Chạy Trên Local

```bash
# 1. Đi vào thư mục web
cd web

# 2. Cài đặt các thư viện (nếu chưa cài)
npm install

# 3. Khởi động server phát triển
npm run dev
```
Truy cập: `http://localhost:3000`

---

## ☁️ Hướng Dẫn Deploy Lên Vercel

1. Đẩy thư mục mã nguồn này lên repository GitHub của bạn.
2. Vào [vercel.com](https://vercel.com) > Nhấn **Add New Project** > Chọn Repository GitHub.
3. **Cấu hình Environment Variables (Biến môi trường)** trên Vercel:
   - `BRIDGE_API_URL`: `https://nso.mio.io.vn` (hoặc `http://168.107.66.164:8020`)
   - `BRIDGE_SECRET_KEY`: `NsoBridgeSecret2026!@#`
4. Nhấn **Deploy**. Vercel sẽ tự động build và cấp domain miễn phí.
