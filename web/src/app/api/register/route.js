import { NextResponse } from 'next/server';
import bcrypt from 'bcryptjs';
import { queryDb } from '@/lib/db';

export async function POST(request) {
  try {
    const body = await request.json();
    const { username, password, confirmPassword, otp } = body;

    // 1. Basic validation
    if (!username || !password || !otp) {
      return NextResponse.json(
        { error: 'Vui lòng điền đầy đủ tài khoản, mật khẩu và mã OTP!' },
        { status: 400 }
      );
    }

    const cleanUsername = String(username).trim().toLowerCase();
    const cleanOtp = String(otp).trim();

    // Check username format (alphanumeric 3-15 chars)
    if (!/^[a-z0-9]{3,15}$/.test(cleanUsername)) {
      return NextResponse.json(
        { error: 'Tên tài khoản từ 3 đến 15 ký tự, chỉ gồm chữ cái và số, không có ký tự đặc biệt!' },
        { status: 400 }
      );
    }

    // Check password length
    if (password.length < 4 || password.length > 50) {
      return NextResponse.json(
        { error: 'Mật khẩu phải từ 4 ký tự trở lên!' },
        { status: 400 }
      );
    }

    if (password !== confirmPassword) {
      return NextResponse.json(
        { error: 'Xác nhận mật khẩu không khớp!' },
        { status: 400 }
      );
    }

    // Check OTP format (6 digits)
    if (!/^\d{6}$/.test(cleanOtp)) {
      return NextResponse.json(
        { error: 'Mã OTP cấp phép phải là 6 chữ số!' },
        { status: 400 }
      );
    }

    // 2. Validate OTP in database (must be unused and not expired)
    const otpRows = await queryDb(
      'SELECT id, code, expires_at, used FROM registration_otps WHERE code = ? LIMIT 1',
      [cleanOtp]
    );

    if (otpRows.length === 0) {
      return NextResponse.json(
        { error: 'Mã OTP không hợp lệ! Vui lòng liên hệ [Khánh] để nhận mã cấp phép.' },
        { status: 400 }
      );
    }

    const otpRecord = otpRows[0];
    if (otpRecord.used === 1) {
      return NextResponse.json(
        { error: 'Mã OTP này đã được sử dụng trước đó! Mỗi mã chỉ dùng được 1 lần.' },
        { status: 400 }
      );
    }

    if (new Date(otpRecord.expires_at) < new Date()) {
      return NextResponse.json(
        { error: 'Mã OTP này đã hết hạn (quá 90 phút)! Vui lòng liên hệ [Khánh] để nhận mã mới.' },
        { status: 400 }
      );
    }

    // 3. Check if username is already taken
    const existingUsers = await queryDb(
      'SELECT id FROM users WHERE username = ? LIMIT 1',
      [cleanUsername]
    );

    if (existingUsers.length > 0) {
      return NextResponse.json(
        { error: 'Tên tài khoản này đã được sử dụng! Vui lòng chọn tên khác.' },
        { status: 400 }
      );
    }

    // 4. Hash password with BCrypt (Cost 12 - matches Java server StringUtils.checkPassword)
    const hashedPassword = await bcrypt.hash(password, 12);

    // 5. Insert new user into database
    await queryDb(
      `INSERT INTO users (
        username, password, activated, balance, luong, tongnap, 
        point_vip, role, status, online, nap, tanthu, level, 
        created_at, updated_at
      ) VALUES (?, ?, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 'member', NOW(), NOW())`,
      [cleanUsername, hashedPassword]
    );

    // 6. Delete the OTP immediately upon successful use
    await queryDb(
      'DELETE FROM registration_otps WHERE code = ?',
      [cleanOtp]
    );

    // Also clean up any expired OTPs in background
    queryDb('DELETE FROM registration_otps WHERE expires_at < NOW()').catch(() => {});

    return NextResponse.json({
      success: true,
      message: 'Đăng ký tài khoản thành công! Bạn có thể mở Client game và đăng nhập ngay bây giờ.',
      username: cleanUsername,
    });
  } catch (error) {
    console.error('Registration error:', error);
    return NextResponse.json(
      { error: 'Có lỗi xảy ra trong quá trình xử lý: ' + (error.message || 'Lỗi kết nối cơ sở dữ liệu') },
      { status: 500 }
    );
  }
}
