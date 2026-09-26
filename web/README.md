# NSO WEB PORTAL - VERCEL READY

Trang web đăng ký tài khoản & hiển thị Giftcode dành cho máy chủ **NSO Ninja School Private 2026**.

---

## 🌟 Tính Năng Chính
1. **Đăng ký tài khoản bảo mật bằng mã OTP 6 số**:
   - Yêu cầu mã OTP 6 số do Quản trị viên cấp phép trực tiếp từ SSH backend.
   - Mỗi mã OTP chỉ sử dụng được 1 lần và tự động hết hạn sau 90 phút.
   - Mật khẩu được mã hóa chuẩn **BCrypt Cost 12** tương thích 100% với Server Game.
2. **Hiển thị Giftcode công khai & Phần thưởng**:
   - Danh sách giftcode đang hoạt động trực tiếp từ Database.
   - Phân loại: Dùng chung / Mỗi nhân vật 1 lần.
   - Thống kê chi tiết quà tặng: Yên, Xu, Lượng, Vật phẩm đính kèm.
   - Nút 1-click Sao chép mã tiện lợi.
3. **Trạng thái Máy Chủ thời gian thực**:
   - Tự động ping cổng `14444` của Game Server để hiển thị trạng thái `ONLINE / BẢO TRÌ`.
   - Đếm số lượng tài khoản đã tạo và số người chơi đang online.
4. **Giao diện hiện đại (Cyber Ninja Dark Theme)**:
   - Tối ưu chuẩn SEO, Responsive hoàn hảo trên Mobile, Tablet, PC.

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

### Cách 1: Deploy qua Vercel CLI (Nhanh nhất)
```bash
# Cài đặt vercel CLI toàn cục (nếu chưa có)
npm install -g vercel

# Đăng nhập vercel
vercel login

# Deploy dự án
vercel
```

### Cách 2: Deploy qua Vercel Dashboard (GitHub)
1. Đẩy thư mục mã nguồn này lên repository GitHub của bạn (hoặc tạo repo riêng cho thư mục `web`).
2. Vào [vercel.com](https://vercel.com) > Nhấn **Add New Project** > Chọn Repository GitHub.
3. **Cấu hình Environment Variables (Biến môi trường)** trên Vercel:
   - `MYSQL_HOST`: `161.118.202.174`
   - `MYSQL_PORT`: `3306`
   - `MYSQL_USER`: `nso_web`
   - `MYSQL_PASSWORD`: `NsoWebDb2026!@#`
   - `MYSQL_DATABASE`: `nso_test`
   - `GAME_SERVER_HOST`: `161.118.202.174`
   - `GAME_SERVER_PORT`: `14444`
4. Nhấn **Deploy**. Vercel sẽ tự động build và cấp domain miễn phí (ví dụ: `your-nso-server.vercel.app`).
