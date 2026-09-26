'use client';

import { useState, useEffect } from 'react';

export default function HomePage() {
  const [serverStatus, setServerStatus] = useState({
    status: 'CHECKING',
    isOnline: false,
    host: '161.118.202.174',
    port: 14444,
    totalUsers: 0,
    onlineUsers: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function fetchStatus() {
      try {
        const res = await fetch('/api/server-status');
        const data = await res.json();
        if (data.success && data.server) {
          setServerStatus(data.server);
        }
      } catch (err) {
        console.error('Failed to fetch server status:', err);
      } finally {
        setLoading(false);
      }
    }
    fetchStatus();
    const timer = setInterval(fetchStatus, 30000);
    return () => clearInterval(timer);
  }, []);

  return (
    <div className="flex flex-col items-center justify-center px-4 sm:px-6 lg:px-8">
      {/* Hero Section */}
      <section className="relative w-full max-w-7xl pt-16 pb-20 md:pt-24 md:pb-28 text-center flex flex-col items-center">
        {/* Live Server Status Badge */}
        <div className="inline-flex items-center gap-2.5 px-4 py-2 rounded-full bg-slate-900/80 border border-white/10 shadow-inner backdrop-blur-md mb-8">
          <span className="relative flex h-3 w-3">
            {serverStatus.isOnline ? (
              <>
                <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-emerald-400 opacity-75"></span>
                <span className="relative inline-flex rounded-full h-3 w-3 bg-emerald-500"></span>
              </>
            ) : (
              <span className="relative inline-flex rounded-full h-3 w-3 bg-red-500"></span>
            )}
          </span>
          <span className="text-xs font-semibold text-slate-300">
            Máy Chủ: <span className="text-white font-bold">{serverStatus.host}:{serverStatus.port}</span>
          </span>
          <span className="text-xs px-2 py-0.5 rounded bg-white/10 font-bold text-amber-400 uppercase tracking-wider">
            {loading ? 'Đang kiểm tra...' : serverStatus.isOnline ? 'ONLINE' : 'BẢO TRÌ'}
          </span>
          <span className="text-xs text-slate-400 border-l border-white/10 pl-2">
            Đang chơi: <strong className="text-emerald-400">{serverStatus.onlineUsers}</strong> / {serverStatus.totalUsers} tài khoản
          </span>
        </div>

        {/* Big Catchy Title */}
        <h1 className="text-4xl sm:text-6xl lg:text-7xl font-black tracking-tight max-w-5xl leading-tight">
          CHIẾN TRƯỜNG <span className="bg-gradient-to-r from-orange-400 via-amber-300 to-red-500 bg-clip-text text-transparent">NINJA SCHOOL</span>
          <br />
          HUYỀN THOẠI TRỞ LẠI
        </h1>

        <p className="mt-6 text-lg sm:text-xl text-slate-400 max-w-3xl font-medium leading-relaxed">
          Trải nghiệm thế giới Ninja kinh điển với hệ thống máy chủ đám mây ổn định, cơ chế bảo mật cấp phép OTP 6 số chống spam tuyệt đối, cùng ngập tràn Giftcode tân thủ!
        </p>

        {/* CTA Action Buttons */}
        <div className="mt-10 flex flex-wrap items-center justify-center gap-4">
          <a
            href="/register"
            className="px-8 py-4 rounded-2xl font-bold text-base text-white bg-gradient-to-r from-orange-500 via-amber-500 to-red-500 shadow-xl shadow-orange-500/25 hover:shadow-orange-500/40 hover:scale-[1.03] active:scale-[0.98] transition-all duration-300 flex items-center gap-3"
          >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
            </svg>
            Đăng Ký Tài Khoản (Cần OTP)
          </a>
          <a
            href="/giftcodes"
            className="px-8 py-4 rounded-2xl font-bold text-base text-slate-200 bg-white/[0.06] border border-white/10 hover:bg-white/10 hover:border-orange-500/40 hover:scale-[1.03] active:scale-[0.98] transition-all duration-300 flex items-center gap-3"
          >
            <svg className="w-5 h-5 text-amber-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
            </svg>
            Xem Danh Sách Giftcode
          </a>
        </div>
      </section>

      {/* Feature Highlights Grid */}
      <section className="w-full max-w-7xl py-12">
        <div className="text-center mb-12">
          <h2 className="text-2xl sm:text-3xl font-extrabold text-white">
            ĐẶC ĐIỂM NỔI BẬT CỦA MÁY CHỦ
          </h2>
          <p className="mt-2 text-slate-400 text-sm">Hệ thống tối ưu, bảo mật và thân thiện với người chơi</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {/* Card 1 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-orange-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-14 h-14 rounded-2xl bg-orange-500/10 border border-orange-500/20 flex items-center justify-center text-orange-400 mb-6">
              <svg className="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-white mb-3">Mã Cấp Phép OTP 6 Số</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Mỗi tài khoản đăng ký bắt buộc phải có mã OTP 6 số do Admin tạo trực tiếp từ SSH backend. Mã chỉ dùng được 1 lần và có hạn 90 phút, chặn đứng hoàn toàn clone tool và spam ảo.
            </p>
          </div>

          {/* Card 2 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-amber-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-14 h-14 rounded-2xl bg-amber-500/10 border border-amber-500/20 flex items-center justify-center text-amber-400 mb-6">
              <svg className="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-white mb-3">Tự Động Sao Lưu Dữ Liệu</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Máy chủ được cấu hình sao lưu cơ sở dữ liệu tự động mỗi 12 giờ lưu 7 bản mới nhất, cùng tính năng backup thủ công độc lập lưu về máy tính của Admin. An toàn dữ liệu 100%.
            </p>
          </div>

          {/* Card 3 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-red-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-14 h-14 rounded-2xl bg-red-500/10 border border-red-500/20 flex items-center justify-center text-red-400 mb-6">
              <svg className="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-white mb-3">Quà Tân Thủ & Giftcode</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Hệ thống Giftcode được cập nhật thường xuyên với nhiều phần thưởng giá trị (Yên, Xu, Lượng, Trang bị VIP, Rương cao cấp) giúp tân thủ nhanh chóng hòa nhập thế giới Ninja.
            </p>
          </div>
        </div>
      </section>

      {/* How to Register Guide */}
      <section className="w-full max-w-5xl py-12">
        <div className="p-8 sm:p-10 rounded-3xl bg-gradient-to-br from-[#121622] to-[#0a0d14] border border-orange-500/20 relative overflow-hidden">
          <div className="relative z-10">
            <h3 className="text-2xl font-black text-white flex items-center gap-3">
              <span className="w-8 h-8 rounded-xl bg-orange-500 flex items-center justify-center text-sm font-bold text-white">
                ?
              </span>
              HƯỚNG DẪN ĐĂNG KÝ TÀI KHOẢN VỚI MÃ OTP
            </h3>
            <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="flex gap-4">
                <div className="w-8 h-8 rounded-full bg-white/10 font-bold text-orange-400 flex items-center justify-center shrink-0">
                  1
                </div>
                <div>
                  <h4 className="font-bold text-white text-base">Liên Hệ Nhận OTP</h4>
                  <p className="text-xs text-slate-400 mt-1">
                    Nhắn tin cho Admin hoặc tham gia kênh cộng đồng để nhận mã cấp phép OTP 6 số.
                  </p>
                </div>
              </div>
              <div className="flex gap-4">
                <div className="w-8 h-8 rounded-full bg-white/10 font-bold text-orange-400 flex items-center justify-center shrink-0">
                  2
                </div>
                <div>
                  <h4 className="font-bold text-white text-base">Nhập Form Đăng Ký</h4>
                  <p className="text-xs text-slate-400 mt-1">
                    Vào trang Đăng Ký, điền Tên tài khoản, Mật khẩu và Mã OTP 6 chữ số vừa nhận được.
                  </p>
                </div>
              </div>
              <div className="flex gap-4">
                <div className="w-8 h-8 rounded-full bg-white/10 font-bold text-orange-400 flex items-center justify-center shrink-0">
                  3
                </div>
                <div>
                  <h4 className="font-bold text-white text-base">Vào Game Trải Nghiệm</h4>
                  <p className="text-xs text-slate-400 mt-1">
                    Mở Client game, chọn máy chủ và đăng nhập tài khoản vừa tạo thành công để chơi ngay!
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Download Client Section */}
      <section id="download" className="w-full max-w-7xl py-12 scroll-mt-24">
        <div className="text-center mb-10">
          <h2 className="text-2xl sm:text-3xl font-extrabold text-white">
            TẢI CLIENT MÁY CHỦ
          </h2>
          <p className="mt-2 text-slate-400 text-sm">
            Client đã được cài đặt sẵn địa chỉ kết nối tới máy chủ <span className="text-orange-400 font-semibold">{serverStatus.host}:{serverStatus.port}</span>
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 max-w-3xl mx-auto">
          {/* JAR File Card */}
          <div className="p-6 rounded-2xl bg-white/[0.03] border border-white/10 flex items-center justify-between gap-4">
            <div className="flex items-center gap-4">
              <div className="w-12 h-12 rounded-xl bg-orange-500/10 border border-orange-500/20 flex items-center justify-center text-orange-400 font-bold">
                JAR
              </div>
              <div>
                <h4 className="font-bold text-white">Bản JAR Chuẩn</h4>
                <p className="text-xs text-slate-400">Dành cho PC (MicroEmulator / Kemulator) & Android (J2meLoader)</p>
              </div>
            </div>
            <a
              href="#"
              onClick={(e) => { e.preventDefault(); alert('Vui lòng sử dụng file JAR trong thư mục .client/JAR_local.jar hoặc tải từ kênh thông báo của Admin!'); }}
              className="px-4 py-2 rounded-xl text-xs font-bold bg-orange-500 hover:bg-orange-600 text-white transition-colors"
            >
              Tải JAR
            </a>
          </div>

          {/* J2ME Guide Card */}
          <div className="p-6 rounded-2xl bg-white/[0.03] border border-white/10 flex items-center justify-between gap-4">
            <div className="flex items-center gap-4">
              <div className="w-12 h-12 rounded-xl bg-amber-500/10 border border-amber-500/20 flex items-center justify-center text-amber-400 font-bold">
                APK
              </div>
              <div>
                <h4 className="font-bold text-white">Giả Lập Android</h4>
                <p className="text-xs text-slate-400">Cài đặt app JL-Mod hoặc J2meLoader trên Google Play Store</p>
              </div>
            </div>
            <a
              href="https://play.google.com/store/apps/details?id=ru.playsoftware.j2meloader"
              target="_blank"
              rel="noopener noreferrer"
              className="px-4 py-2 rounded-xl text-xs font-bold bg-white/10 hover:bg-white/20 text-white transition-colors"
            >
              Play Store
            </a>
          </div>
        </div>
      </section>
    </div>
  );
}
