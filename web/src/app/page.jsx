'use client';

import { useState, useEffect } from 'react';

export default function HomePage() {
  const [serverStatus, setServerStatus] = useState({
    serverName: 'NSO Private (Mod bởi Khánh)',
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
      <section className="relative w-full max-w-7xl pt-14 pb-16 md:pt-20 md:pb-20 text-center flex flex-col items-center">
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
            Máy Chủ: <span className="text-white font-bold">{serverStatus.serverName || 'NSO Private'}</span>
          </span>
          <span className="text-xs px-2 py-0.5 rounded bg-white/10 font-bold text-amber-400 uppercase tracking-wider">
            {loading ? 'Đang kiểm tra...' : serverStatus.isOnline ? 'HOẠT ĐỘNG' : 'BẢO TRÌ'}
          </span>
          <span className="text-xs text-slate-400 border-l border-white/10 pl-2">
            Đang online: <strong className="text-emerald-400">{serverStatus.onlineUsers}</strong> / {serverStatus.totalUsers} thành viên
          </span>
        </div>

        {/* Title */}
        <h1 className="text-4xl sm:text-6xl lg:text-7xl font-black tracking-tight max-w-5xl leading-tight">
          NINJA SCHOOL{' '}
          <span className="bg-gradient-to-r from-orange-400 via-amber-300 to-red-500 bg-clip-text text-transparent">
            ONLINE
          </span>
        </h1>

        <p className="mt-6 text-base sm:text-lg text-slate-300 max-w-3xl font-normal leading-relaxed">
          Dự án máy chủ Ninja School Online được tùy biến và duy trì phi lợi nhuận bởi{' '}
          <a
            href="https://k.mio.io.vn"
            target="_blank"
            rel="noopener noreferrer"
            className="text-orange-400 font-bold underline hover:text-amber-300 transition-colors"
          >
            [Khánh]
          </a>{' '}
          nhằm mục đích lưu giữ kỷ niệm và chia sẻ không gian giải trí cùng bạn bè. Máy chủ hoàn toàn miễn phí, không thương mại hóa và không nạp thẻ.
        </p>

        {/* CTA Action Buttons */}
        <div className="mt-10 flex flex-wrap items-center justify-center gap-4">
          <a
            href="/register"
            className="px-7 py-3.5 rounded-2xl font-bold text-sm sm:text-base text-white bg-gradient-to-r from-orange-500 via-amber-500 to-red-500 shadow-xl shadow-orange-500/25 hover:shadow-orange-500/40 hover:scale-[1.03] active:scale-[0.98] transition-all duration-300 flex items-center gap-2.5"
          >
            <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
            </svg>
            Đăng Ký Tài Khoản
          </a>
          <a
            href="/#download"
            className="px-7 py-3.5 rounded-2xl font-bold text-sm sm:text-base text-white bg-white/[0.08] border border-orange-500/40 hover:bg-orange-500/20 hover:scale-[1.03] active:scale-[0.98] transition-all duration-300 flex items-center gap-2.5"
          >
            <svg className="w-5 h-5 text-orange-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
            </svg>
            Tải Client (NSO.jar)
          </a>
          <a
            href="/giftcodes"
            className="px-7 py-3.5 rounded-2xl font-bold text-sm sm:text-base text-slate-200 bg-white/[0.04] border border-white/10 hover:bg-white/10 hover:border-amber-500/40 hover:scale-[1.03] active:scale-[0.98] transition-all duration-300 flex items-center gap-2.5"
          >
            <svg className="w-5 h-5 text-amber-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
            </svg>
            Nhận Giftcode
          </a>
          <a
            href="https://k.mio.io.vn"
            target="_blank"
            rel="noopener noreferrer"
            className="px-6 py-3.5 rounded-2xl font-semibold text-xs sm:text-sm text-slate-400 hover:text-white bg-transparent border border-white/10 hover:border-white/20 transition-all flex items-center gap-1.5"
          >
            Liên hệ [Khánh] ↗
          </a>
        </div>
      </section>

      {/* Feature Highlights Grid */}
      <section className="w-full max-w-7xl py-10">
        <div className="text-center mb-10">
          <h2 className="text-2xl sm:text-3xl font-extrabold text-white">
            TIÊU CHÍ HOẠT ĐỘNG
          </h2>
          <p className="mt-2 text-slate-400 text-sm">Định hướng duy trì máy chủ minh bạch, ổn định và phi lợi nhuận</p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
          {/* Card 1 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-orange-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-12 h-12 rounded-2xl bg-orange-500/10 border border-orange-500/20 flex items-center justify-center text-orange-400 mb-5">
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
              </svg>
            </div>
            <h3 className="text-lg font-bold text-white mb-2">Hoàn Toàn Phi Lợi Nhuận</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Không có hệ thống nạp tiền, không thương mại hóa vật phẩm. Mọi người chơi đều có cơ hội trải nghiệm và cày cuốc công bằng như nhau.
            </p>
          </div>

          {/* Card 2 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-amber-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-12 h-12 rounded-2xl bg-amber-500/10 border border-amber-500/20 flex items-center justify-center text-amber-400 mb-5">
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
              </svg>
            </div>
            <h3 className="text-lg font-bold text-white mb-2">Hỗ Trợ Khởi Đầu &amp; Hoài Niệm</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Cung cấp mã Giftcode quà tặng tân thủ để người chơi dễ dàng làm quen lại lối chơi cổ điển mà không mất quá nhiều thời gian đầu tư.
            </p>
          </div>

          {/* Card 3 */}
          <div className="p-8 rounded-3xl bg-white/[0.02] border border-white/5 hover:border-red-500/30 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1">
            <div className="w-12 h-12 rounded-2xl bg-red-500/10 border border-red-500/20 flex items-center justify-center text-red-400 mb-5">
              <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
            </div>
            <h3 className="text-lg font-bold text-white mb-2">Kiểm Soát Tài Khoản Bằng OTP</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Mỗi tài khoản được đăng ký qua mã OTP cấp phép trực tiếp từ{' '}
              <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="text-orange-400 underline hover:text-white font-semibold">
                [Khánh]
              </a>
              , giúp kiểm soát spam bot và bảo đảm sự ổn định cho máy chủ.
            </p>
          </div>
        </div>
      </section>

      {/* How to Play Guide */}
      <section className="w-full max-w-5xl py-10">
        <div className="p-8 sm:p-10 rounded-3xl bg-gradient-to-br from-[#121622] to-[#0a0d14] border border-orange-500/20 relative overflow-hidden">
          <div className="relative z-10">
            <h3 className="text-xl sm:text-2xl font-black text-white flex items-center gap-3">
              <span className="w-8 h-8 rounded-xl bg-orange-500 flex items-center justify-center text-sm font-bold text-white">
                ✓
              </span>
              CÁC BƯỚC THAM GIA MÁY CHỦ
            </h3>
            <div className="mt-6 grid grid-cols-1 md:grid-cols-3 gap-6">
              <div className="flex gap-4">
                <div className="w-8 h-8 rounded-full bg-white/10 font-bold text-orange-400 flex items-center justify-center shrink-0">
                  1
                </div>
                <div>
                  <h4 className="font-bold text-white text-base">Nhận Mã OTP</h4>
                  <p className="text-xs text-slate-400 mt-1">
                    Liên hệ{' '}
                    <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="text-orange-400 underline font-semibold">
                      [Khánh]
                    </a>{' '}
                    để nhận mã cấp phép OTP 6 số (dùng 1 lần).
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
                    Vào trang Đăng Ký, điền tên đăng nhập, mật khẩu và nhập mã OTP vừa nhận để tạo tài khoản.
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
                    Tải file <code className="text-orange-300">NSO.jar</code> bên dưới, mở bằng giả lập tương ứng và đăng nhập vào game!
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
            Tải tệp game NSO.jar và trình giả lập phù hợp với thiết bị của bạn
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 max-w-3xl mx-auto">
          {/* JAR File Direct Download Card */}
          <div className="p-6 rounded-3xl bg-white/[0.03] border border-orange-500/30 flex flex-col justify-between gap-4 relative overflow-hidden">
            <div className="absolute top-0 right-0 px-3 py-1 bg-orange-500/20 border-b border-l border-orange-500/30 rounded-bl-xl text-[11px] font-bold text-orange-300">
              Khuyên dùng
            </div>
            <div className="flex items-start gap-4">
              <div className="w-12 h-12 rounded-2xl bg-orange-500/10 border border-orange-500/20 flex items-center justify-center text-orange-400 font-bold shrink-0">
                JAR
              </div>
              <div>
                <h4 className="font-bold text-white text-base">NSO.jar (Bản Chuẩn)</h4>
                <p className="text-xs text-slate-400 mt-1 leading-relaxed">
                  Tệp Java Client đã cấu hình sẵn kết nối đến máy chủ. Kích thước ~1.04 MB.
                </p>
              </div>
            </div>
            <div className="flex items-center justify-between pt-2 border-t border-white/5">
              <span className="text-[11px] text-slate-400">Dành cho PC &amp; Android</span>
              <a
                href="/NSO.jar"
                download="NSO.jar"
                className="px-5 py-2.5 rounded-xl text-xs font-bold bg-gradient-to-r from-orange-500 to-amber-500 hover:from-orange-600 hover:to-amber-600 text-white shadow-lg shadow-orange-500/20 transition-all hover:scale-105 flex items-center gap-1.5"
              >
                <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                </svg>
                Tải NSO.jar
              </a>
            </div>
          </div>

          {/* Android J2ME Guide Card */}
          <div className="p-6 rounded-3xl bg-white/[0.03] border border-white/10 flex flex-col justify-between gap-4">
            <div className="flex items-start gap-4">
              <div className="w-12 h-12 rounded-2xl bg-amber-500/10 border border-amber-500/20 flex items-center justify-center text-amber-400 font-bold shrink-0">
                APK
              </div>
              <div>
                <h4 className="font-bold text-white text-base">Giả Lập J2MELoader (Android)</h4>
                <p className="text-xs text-slate-400 mt-1 leading-relaxed">
                  Ứng dụng giả lập Java chuẩn trên điện thoại Android để mở file NSO.jar mượt mà.
                </p>
              </div>
            </div>
            <div className="flex items-center justify-between pt-2 border-t border-white/5">
              <span className="text-[11px] text-slate-400">Google Play Store</span>
              <a
                href="https://play.google.com/store/apps/details?id=ru.playsoftware.j2meloader"
                target="_blank"
                rel="noopener noreferrer"
                className="px-4 py-2.5 rounded-xl text-xs font-bold bg-white/10 hover:bg-white/20 text-white transition-all flex items-center gap-1.5"
              >
                Cài J2MELoader ↗
              </a>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
}
