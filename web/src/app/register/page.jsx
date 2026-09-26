'use client';

import { useState } from 'react';

export default function RegisterPage() {
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    confirmPassword: '',
    otp: '',
  });

  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [status, setStatus] = useState({ type: '', message: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name === 'otp' ? value.replace(/\D/g, '').slice(0, 6) : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setStatus({ type: '', message: '' });

    if (!formData.username || !formData.password || !formData.confirmPassword || !formData.otp) {
      setStatus({ type: 'error', message: 'Vui lòng điền đầy đủ các thông tin bên dưới.' });
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setStatus({ type: 'error', message: 'Mật khẩu xác nhận không khớp.' });
      return;
    }

    if (formData.otp.length !== 6) {
      setStatus({ type: 'error', message: 'Mã OTP cấp phép phải gồm đúng 6 chữ số.' });
      return;
    }

    setLoading(true);

    try {
      const res = await fetch('/api/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(formData),
      });

      const data = await res.json();

      if (!res.ok || !data.success) {
        setStatus({
          type: 'error',
          message: data.error || 'Đăng ký không thành công. Vui lòng kiểm tra lại.',
        });
      } else {
        setStatus({
          type: 'success',
          message: data.message || 'Đăng ký tài khoản thành công! Bạn có thể vào game ngay bây giờ.',
        });
        setFormData({
          username: '',
          password: '',
          confirmPassword: '',
          otp: '',
        });
      }
    } catch (err) {
      setStatus({
        type: 'error',
        message: 'Không thể kết nối máy chủ: ' + err.message,
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex-1 flex items-center justify-center px-4 sm:px-6 lg:px-8 py-12">
      <div className="w-full max-w-md">
        {/* Card Header */}
        <div className="text-center mb-8">
          <div className="inline-flex w-16 h-16 rounded-2xl bg-gradient-to-tr from-orange-500 to-amber-500 p-0.5 shadow-xl shadow-orange-500/20 mb-4">
            <div className="w-full h-full bg-[#0d1117] rounded-[14px] flex items-center justify-center text-2xl font-black text-orange-400">
              忍
            </div>
          </div>
          <h1 className="text-3xl font-black text-white tracking-tight">ĐĂNG KÝ TÀI KHOẢN</h1>
          <p className="text-sm text-slate-400 mt-2">
            Nhập thông tin và mã OTP 6 số do{' '}
            <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="text-orange-400 font-bold underline">
              Khánh (k.mio.io.vn)
            </a>{' '}
            cấp phép
          </p>
        </div>

        {/* Card Body */}
        <div className="bg-white/[0.03] border border-white/10 backdrop-blur-2xl p-8 rounded-3xl shadow-2xl shadow-black/60 relative overflow-hidden">
          <div className="absolute -top-24 -right-24 w-48 h-48 bg-orange-500/10 blur-3xl rounded-full pointer-events-none" />

          {/* Feedback Alert */}
          {status.message && (
            <div
              className={`mb-6 p-4 rounded-2xl border text-sm flex items-start gap-3 ${
                status.type === 'success'
                  ? 'bg-emerald-500/10 border-emerald-500/30 text-emerald-300'
                  : 'bg-red-500/10 border-red-500/30 text-red-300'
              }`}
            >
              {status.type === 'success' ? (
                <svg className="w-5 h-5 shrink-0 text-emerald-400 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              ) : (
                <svg className="w-5 h-5 shrink-0 text-red-400 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
              )}
              <div className="leading-relaxed">{status.message}</div>
            </div>
          )}

          <form onSubmit={handleSubmit} className="space-y-5">
            {/* Username Input */}
            <div>
              <label className="block text-xs font-bold text-slate-300 uppercase tracking-wider mb-2">
                Tên tài khoản
              </label>
              <div className="relative">
                <input
                  type="text"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  placeholder="Ví dụ: naruto2026"
                  autoComplete="username"
                  required
                  className="w-full px-4 py-3.5 rounded-xl bg-black/40 border border-white/10 text-white placeholder-slate-500 text-sm focus:outline-none focus:border-orange-500 focus:ring-2 focus:ring-orange-500/20 transition-all"
                />
                <div className="absolute right-3.5 top-3.5 text-slate-500">
                  <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
                </div>
              </div>
              <p className="text-[11px] text-slate-500 mt-1">Từ 3 đến 15 ký tự (chữ cái hoặc số)</p>
            </div>

            {/* Password Input */}
            <div>
              <label className="block text-xs font-bold text-slate-300 uppercase tracking-wider mb-2">
                Mật khẩu
              </label>
              <div className="relative">
                <input
                  type={showPassword ? 'text' : 'password'}
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  placeholder="Nhập mật khẩu"
                  autoComplete="new-password"
                  required
                  className="w-full px-4 py-3.5 rounded-xl bg-black/40 border border-white/10 text-white placeholder-slate-500 text-sm focus:outline-none focus:border-orange-500 focus:ring-2 focus:ring-orange-500/20 transition-all"
                />
                <button
                  type="button"
                  onClick={() => setShowPassword(!showPassword)}
                  className="absolute right-3.5 top-3.5 text-slate-500 hover:text-slate-300 transition-colors"
                >
                  {showPassword ? (
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.858.908a3 3 0 114.243 4.243M9.878 9.878l4.242 4.242M9.88 9.88l-3.29-3.29m7.532 7.532l3.29 3.29M3 3l18 18" />
                    </svg>
                  ) : (
                    <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                    </svg>
                  )}
                </button>
              </div>
            </div>

            {/* Confirm Password Input */}
            <div>
              <label className="block text-xs font-bold text-slate-300 uppercase tracking-wider mb-2">
                Xác nhận mật khẩu
              </label>
              <div className="relative">
                <input
                  type={showPassword ? 'text' : 'password'}
                  name="confirmPassword"
                  value={formData.confirmPassword}
                  onChange={handleChange}
                  placeholder="Nhập lại mật khẩu"
                  autoComplete="new-password"
                  required
                  className="w-full px-4 py-3.5 rounded-xl bg-black/40 border border-white/10 text-white placeholder-slate-500 text-sm focus:outline-none focus:border-orange-500 focus:ring-2 focus:ring-orange-500/20 transition-all"
                />
              </div>
            </div>

            {/* Authorization OTP 6 Digits */}
            <div className="pt-2">
              <div className="flex items-center justify-between mb-2">
                <label className="text-xs font-bold text-orange-400 uppercase tracking-wider flex items-center gap-1.5">
                  <svg className="w-4 h-4 text-orange-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 7a2 2 0 012 2m4 0a6 6 0 01-7.743 5.743L11 17H9v2H7v2H4a1 1 0 01-1-1v-2.586a1 1 0 01.293-.707l5.964-5.964A6 6 0 1121 9z" />
                  </svg>
                  Mã Cấp Phép OTP (6 Số)
                </label>
                <span className="text-[11px] text-amber-400/90 font-medium">Hạn dùng 90 phút</span>
              </div>
              <div className="relative">
                <input
                  type="text"
                  name="otp"
                  value={formData.otp}
                  onChange={handleChange}
                  placeholder="000000"
                  maxLength={6}
                  required
                  className="w-full px-4 py-3.5 rounded-xl bg-orange-950/20 border border-orange-500/30 text-orange-300 placeholder-slate-600 font-mono text-xl tracking-[0.4em] text-center font-bold focus:outline-none focus:border-orange-400 focus:ring-2 focus:ring-orange-500/30 transition-all"
                />
              </div>
              <p className="text-[11px] text-slate-400 mt-1.5">
                Mã xác thực duy nhất do Khánh cấp (dùng 1 lần).
              </p>
            </div>

            {/* Submit Button */}
            <button
              type="submit"
              disabled={loading}
              className="w-full mt-4 py-4 px-6 rounded-2xl font-bold text-base text-white bg-gradient-to-r from-orange-500 via-amber-500 to-red-500 hover:from-orange-600 hover:to-red-600 shadow-xl shadow-orange-500/25 hover:shadow-orange-500/40 active:scale-[0.99] disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-300 flex items-center justify-center gap-3"
            >
              {loading ? (
                <>
                  <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4" />
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z" />
                  </svg>
                  Đang khởi tạo tài khoản...
                </>
              ) : (
                'Hoàn Tất Đăng Ký'
              )}
            </button>
          </form>
        </div>

        {/* Support Help Box */}
        <div className="mt-8 p-5 rounded-2xl bg-white/[0.02] border border-white/5 text-center text-xs text-slate-400">
          Chưa có mã OTP?{' '}
          <a
            href="https://k.mio.io.vn"
            target="_blank"
            rel="noopener noreferrer"
            className="text-orange-400 font-bold underline hover:text-amber-300 transition-colors"
          >
            Liên hệ Khánh tại k.mio.io.vn
          </a>{' '}
          để nhận mã cấp phép tạo tài khoản.
        </div>
      </div>
    </div>
  );
}
