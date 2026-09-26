- Xây dựng WEB cho deploy vercel:
  - Đăng kí tài khoản bằng tài khoản, mật khẩu và 1 otp được tạo từ phía backend bằng kích hoạt thủ công qua kết nối
  - Hiển thị giftcode

- Thêm feature: 1 hàm sinh số ngẫu nhiên (6 chữ số)tạm thời sinh trên backend , tài khoản chỉ có thể được lập nếu thỏa mãn mã này(đề cập ở trên). Mã này chỉ có tác dụng duy nhất như là 1 cấp phép của chủ server để cấp cho người chơi, sau khi người chơi dùng mã này tạo tài khoản xong thì mã này sẽ không còn tác dụng, muốn cấp cho người chơi khác phải thông qua kết nối ssh tới backend. Mã sẽ tự xóa sau khi được sử dụng để đăng kí hoặc hết hạn(90 phút)
- Thêm 1 UI file html chạy trên local quản lí trực tiếp giftcode qua ssh (xem/ tạo/ xóa), lưu ý khi xóa cần xóa thêm đến các dữ liệu khác có tham chiếu của mã này trong database. UI hỗ trợ chọn vật phẩm nếu cần
- Thêm auto backup database hằng ngày ( tự động tạo 1 file backup của database trong thư mục data/backup mỗi 12h, tối đa 7 bản backup, khi đủ 7 bản thì xóa bản cũ nhất)
- Thêm file backup qua ssh. Lưu file về máy tính cá nhân. Backup này không ảnh hưởng tới luồng backup tự động. Có thể chủ động tạo bất cứ lúc nào.
- Thêm backup source server khi update, tối đa 3 bản
