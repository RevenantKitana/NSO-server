'use client';

import { useState, useEffect } from 'react';

export default function HomePage() {
  const [serverStatus, setServerStatus] = useState({
    serverName: 'NSO Custom (Mod by Khánh)',
    status: 'CHECKING',
    isOnline: false,
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
      <section className="relative w-full max-w-7xl pt-16 pb-16 md:pt-20 md:pb-24 text-center flex flex-col items-center">
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
            Máy Chủ: <span className="text-white font-bold">{serverStatus.serverName || 'NSO Custom'}</span>
          </span>
          <span className="text-xs px-2 py-0.5 rounded bg-white/10 font-bold text-amber-400 uppercase tracking-wider">
            {loading ? 'Đang kiểm tra...' : serverStatus.isOnline ? 'HOẠT ĐỘNG' : 'BẢO TRÌ'}
          </span>
          <span className="text-xs text-slate-400 border-l border-white/10 pl-2">
            Đang online: <strong className="text-emerald-400">{serverStatus.onlineUsers}</strong> / {serverStatus.totalUsers} thành viên
          </span>
        </div>

        {/* Catchy Title */}
        <h1 className="text-4xl sm:text-6xl lg:text-7xl font-black tracking-tight max-w-5xl leading-tight">
          NINJA SCHOOL <span className="bg-gradient-to-r from-orange-400 via-amber-300 to-red-500 bg-clip-text text-transparent">CUSTOM</span>
          <br />
          MOD BỞI KHÁNH
        </h1>

        <p className="mt-6 text-lg sm:text-xl text-slate-300 max-w-3xl font-normal leading-relaxed">
          Phiên bản máy chủ tùy biến từ tựa game gốc, được phát triển bởi{' '}
          <a
            href="https://k.mio.io.vn"
            target="_blank"
            rel="noopener noreferrer"
            className="text-orange-400 font-bold underline hover:text-amber-300 transition-colors"
          >
            Khánh (k.mio.io.vn)
          </a>
          . Lối chơi cày cuốc cân bằng, hỗ trợ tân thủ tối đa và kết nối ổn định mượt mà.
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
            Đăng Ký Tài Khoản
          </a>
          <a
            href="/giftcodes"
            className="px-8 py-4 rounded-2xl font-bold text-base text-slate-200 bg-white/[0.06] border border-white/10 hover:bg-white/10 hover:border-orange-500/40 hover:scale-[1.03] active:scale-[0.98] transition-all duration-300 flex items-center gap-3"
          >
            <svg className="w-5 h-5 text-amber-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
            </svg>
            Nhận Giftcode Tân Thủ
          </a>
          <a
            href="https://k.mio.io.vn"
            target="_blank"
            rel="noopener noreferrer"
            className="px-6 py-4 rounded-2xl font-semibold text-sm text-slate-400 hover:text-white bg-transparent border border-white/10 hover:border-white/20 transition-all flex items-center gap-2"
          >
            Liên hệ Khánh (k.mio.io.vn) ↗
          </a>
        </div>
      </section>

      {/* Feature Highlights Grid */}
      <section className="w-full max-w-7xl py-12">
        <div className="text-center mb-12">
          <h2 className="text-2xl sm:text-3xl font-extrabold text-white">
            ĐẶC SẮC MÁY CHỦ
          </h2>
          <p className="mt-2 text-slate-400 text-sm">Trải nghiệm Ninja trọn vẹn với các tinh chỉnh tối ưu</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {/* Card 1 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-orange-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-14 h-14 rounded-2xl bg-orange-500/10 border border-orange-500/20 flex items-center justify-center text-orange-400 mb-6">
              <svg className="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13 10V3L4 14h7v7l9-11h-7z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-white mb-3">Lối Chơi Cân Bằng</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Tỷ lệ kinh nghiệm EXP, tỉ lệ rớt vật phẩm và cường hóa trang bị được tinh chỉnh hợp lý, giữ trọn vẹn cảm giác cày cuốc thú vị của tựa game gốc.
            </p>
          </div>

          {/* Card 2 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-amber-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-14 h-14 rounded-2xl bg-amber-500/10 border border-amber-500/20 flex items-center justify-center text-amber-400 mb-6">
              <svg className="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-white mb-3">Quà Tặng Tân Thủ Dồi Dào</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Khởi đầu thuận lợi với các gói Giftcode chứa Yên, Xu, Lượng cùng nhiều vật phẩm hữu ích để dễ dàng nâng cấp trang bị và thăng tiến cấp độ.
            </p>
          </div>

          {/* Card 3 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-red-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-14 h-14 rounded-2xl bg-red-500/10 border border-red-500/20 flex items-center justify-center text-red-400 mb-6">
              <svg className="w-7 h-7" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <h3 className="text-xl font-bold text-white mb-3">Cấp Phép Bởi Khánh</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Tài khoản được đăng ký thông qua mã xác nhận OTP từ Khánh (
              <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="text-orange-400 underline hover:text-white">
                k.mio.io.vn
              </a>
              ), đảm bảo một cộng đồng người chơi văn minh, an toàn và công bằng.
            </p>
          </div>
        </div>
      </section>

      {/* How to Play Guide */}
      <section className="w-full max-w-5xl py-12">
        <div className="p-8 sm:p-10 rounded-3xl bg-gradient-to-br from-[#121622] to-[#0a0d14] border border-orange-500/20 relative overflow-hidden">
          <div className="relative z-10">
            <h3 className="text-2xl font-black text-white flex items-center gap-3">
              <span className="w-8 h-8 rounded-xl bg-orange-500 flex items-center justify-center text-sm font-bold text-white">
                ✓
              </span>
              HƯỚNG DẪN THAM GIA TRÒ CHƠI
            </h3>
            <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="flex gap-4">
                <div className="w-8 h-8 rounded-full bg-white/10 font-bold text-orange-400 flex items-center justify-center shrink-0">
                  1
                </div>
                <div>
                  <h4 className="font-bold text-white text-base">Nhận Mã OTP</h4>
                  <p className="text-xs text-slate-400 mt-1">
                    Liên hệ Khánh tại{' '}
                    <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="text-orange-400 underline">
                      k.mio.io.vn
                    </a>{' '}
                    để nhận mã cấp phép OTP 6 số.
                  </p>
                </div>
              </div>
              <div className="flex gap-4">
                <div className="w-8 h-8 rounded-full bg-white/10 font-bold text-orange-400 flex items-center justify-center shrink-0">
                  2
                </div>
                <div>
                  <h4 className="font-bold text-white text-base">Đăng Ký Tài Khoản</h4>
                  <p className="text-xs text-slate-400 mt-1">
                    Vào mục Đăng Ký, điền tên tài khoản, mật khẩu và nhập mã OTP vừa nhận được.
                  </p>
                </div>
              </div>
              <div className="flex gap-4">
                <div className="w-8 h-8 rounded-full bg-white/10 font-bold text-orange-400 flex items-center justify-center shrink-0">
                  3
                </div>
                <div>
                  <h4 className="font-bold text-white text-base">Tải Game &amp; Trải Nghiệm</h4>
                  <p className="text-xs text-slate-400 mt-1">
                    Tải bản Client bên dưới, đăng nhập tài khoản và bắt đầu hành trình Ninja!
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
            Tải phiên bản phù hợp với thiết bị của bạn để vào game
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
                <p className="text-xs text-slate-400">Dành cho PC (MicroEmulator / Kemulator) &amp; Android (J2MELoader)</p>
              </div>
            </div>
            <a
              href="#"
              onClick={(e) => { e.preventDefault(); alert('Vui lòng liên hệ Khánh tại k.mio.io.vn để nhận link tải Client mới nhất!'); }}
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
                <p className="text-xs text-slate-400">Cài đặt app J2MELoader từ Google Play Store để chơi bản JAR trên Android</p>
              </div>
            </div>
            <a
              href="https://play.google.com/store/apps/details?id=ru.playsoftware.j2meloader"
              target="_blank"
              rel="noopener noreferrer"
              className="px-4 py-2 rounded-xl text-xs font-bold bg-white/10 hover:bg-white/20 text-white transition-colors"
            >
              Google Play
            </a>
          </div>
        </div>
      </section>
    </div>
  );
}
