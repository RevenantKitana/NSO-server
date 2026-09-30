import { NextResponse } from 'next/server';
import bcrypt from 'bcryptjs';
import { fetchFromBridge } from '@/lib/bridge';

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

    // 2. Hash password with BCrypt (Cost 12)
    const passwordHash = await bcrypt.hash(password, 12);

    // 3. Forward to VM API Bridge
    const result = await fetchFromBridge('/api/register', {
      method: 'POST',
      body: JSON.stringify({
        username: cleanUsername,
        passwordHash,
        otp: cleanOtp,
      }),
    });

    return NextResponse.json(result);
  } catch (error) {
    console.error('Registration error:', error.message);
    return NextResponse.json(
      { error: error.message || 'Lỗi kết nối máy chủ máy ảo' },
      { status: 400 }
    );
  }
}
