import './globals.css';

export const metadata = {
  title: 'NSO Custom Server - Modded by Khánh | k.mio.io.vn',
  description: 'Máy chủ Ninja School Online Custom mod bởi Khánh. Hệ thống đăng ký bảo mật OTP 6 số, nhận Giftcode tân thủ và tải game miễn phí.',
};

export default function RootLayout({ children }) {
  return (
    <html lang="vi" className="dark">
      <head>
        <link rel="preconnect" href="https://fonts.googleapis.com" />
        <link rel="preconnect" href="https://fonts.gstatic.com" crossOrigin="anonymous" />
        <link
          href="https://fonts.googleapis.com/css2?family=Cinzel:wght@700;900&family=Plus+Jakarta+Sans:wght@400;500;600;700;800&display=swap"
          rel="stylesheet"
        />
      </head>
      <body className="min-h-screen bg-[#07090e] text-slate-100 flex flex-col selection:bg-orange-500 selection:text-white font-sans antialiased overflow-x-hidden">
        {/* Background glowing effects */}
        <div className="fixed inset-0 pointer-events-none -z-10 overflow-hidden">
          <div className="absolute -top-40 left-1/2 -translate-x-1/2 w-[700px] h-[400px] bg-orange-600/15 blur-[120px] rounded-full" />
          <div className="absolute top-[30%] -left-32 w-[450px] h-[450px] bg-amber-600/10 blur-[130px] rounded-full" />
          <div className="absolute top-[60%] -right-32 w-[500px] h-[500px] bg-red-600/10 blur-[140px] rounded-full" />
          <div className="absolute inset-0 bg-[radial-gradient(#1e293b_1px,transparent_1px)] [background-size:24px_24px] opacity-20" />
        </div>

        {/* Top Notification / Banner */}
        <div className="bg-gradient-to-r from-orange-600/20 via-amber-500/20 to-orange-600/20 border-b border-orange-500/20 py-1.5 px-4 text-center text-xs sm:text-sm font-medium text-orange-300">
          <span className="inline-flex items-center gap-2">
            <span className="flex h-2 w-2 relative">
              <span className="animate-ping absolute inline-flex h-full w-full rounded-full bg-orange-400 opacity-75"></span>
              <span className="relative inline-flex rounded-full h-2 w-2 bg-orange-500"></span>
            </span>
            Máy chủ NSO Custom mod bởi Khánh • Nhận mã OTP cấp phép tại{' '}
            <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="underline font-bold text-amber-300 hover:text-white transition-colors">
              k.mio.io.vn
            </a>
          </span>
        </div>

        {/* Navigation Bar */}
        <header className="sticky top-0 z-50 backdrop-blur-xl bg-[#07090e]/80 border-b border-white/5">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-20 flex items-center justify-between">
            {/* Logo */}
            <a href="/" className="group flex items-center gap-3 transition-transform duration-200 hover:scale-[1.02]">
              <div className="w-11 h-11 rounded-xl bg-gradient-to-br from-orange-500 via-amber-500 to-red-600 p-0.5 shadow-lg shadow-orange-500/20 flex items-center justify-center">
                <div className="w-full h-full bg-[#0d1117] rounded-[10px] flex items-center justify-center font-serif text-xl font-black text-transparent bg-clip-text bg-gradient-to-br from-orange-400 to-amber-200">
                  忍
                </div>
              </div>
              <div className="flex flex-col">
                <span className="text-xl font-extrabold tracking-wider bg-gradient-to-r from-white via-slate-200 to-orange-300 bg-clip-text text-transparent">
                  NSO CUSTOM
                </span>
                <span className="text-[10px] tracking-widest uppercase font-semibold text-orange-400/90 -mt-1">
                  Mod by Khánh • k.mio.io.vn
                </span>
              </div>
            </a>

            {/* Navigation Links */}
            <nav className="hidden md:flex items-center gap-1 bg-white/[0.03] border border-white/5 px-3 py-1.5 rounded-full backdrop-blur-md">
              <a
                href="/"
                className="px-4 py-2 rounded-full text-sm font-semibold text-slate-300 hover:text-white hover:bg-white/5 transition-colors"
              >
                Trang Chủ
              </a>
              <a
                href="/register"
                className="px-4 py-2 rounded-full text-sm font-semibold text-orange-400 hover:text-orange-300 hover:bg-orange-500/10 transition-colors"
              >
                Đăng Ký
              </a>
              <a
                href="/giftcodes"
                className="px-4 py-2 rounded-full text-sm font-semibold text-slate-300 hover:text-white hover:bg-white/5 transition-colors"
              >
                Giftcode
              </a>
              <a
                href="/#download"
                className="px-4 py-2 rounded-full text-sm font-semibold text-slate-300 hover:text-white hover:bg-white/5 transition-colors"
              >
                Tải Client
              </a>
              <a
                href="https://k.mio.io.vn"
                target="_blank"
                rel="noopener noreferrer"
                className="px-4 py-2 rounded-full text-sm font-semibold text-amber-300 hover:text-white hover:bg-amber-500/10 transition-colors"
              >
                Khánh (k.mio.io.vn) ↗
              </a>
            </nav>

            {/* Right Action */}
            <div className="flex items-center gap-3">
              <a
                href="/register"
                className="relative group overflow-hidden px-5 py-2.5 rounded-xl text-sm font-bold text-white bg-gradient-to-r from-orange-500 to-amber-500 hover:from-orange-600 hover:to-amber-600 shadow-lg shadow-orange-500/25 transition-all duration-300 hover:shadow-orange-500/40 hover:-translate-y-0.5"
              >
                <span className="relative z-10 flex items-center gap-2">
                  <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M18 9v3m0 0v3m0-3h3m-3 0h-3m-2-5a4 4 0 11-8 0 4 4 0 018 0zM3 20a6 6 0 0112 0v1H3v-1z" />
                  </svg>
                  Tạo Tài Khoản
                </span>
                <div className="absolute inset-0 bg-white/20 translate-y-full group-hover:translate-y-0 transition-transform duration-300" />
              </a>
            </div>
          </div>
        </header>

        {/* Main Content Area */}
        <main className="flex-1 w-full flex flex-col">
          {children}
        </main>

        {/* Footer */}
        <footer className="border-t border-white/5 bg-[#05070a] mt-20 py-12 px-4 sm:px-6 lg:px-8 text-slate-400">
          <div className="max-w-7xl mx-auto flex flex-col md:flex-row items-center justify-between gap-6">
            <div className="flex items-center gap-3">
              <div className="w-8 h-8 rounded-lg bg-orange-500/10 border border-orange-500/30 flex items-center justify-center font-bold text-orange-400 text-sm">
                忍
              </div>
              <p className="text-sm">
                Bản Custom Server mod từ tựa game gốc bởi{' '}
                <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="text-orange-400 font-bold hover:underline">
                  Khánh (k.mio.io.vn)
                </a>.
              </p>
            </div>
            <div className="flex items-center gap-6 text-sm">
              <a href="/" className="hover:text-orange-400 transition-colors">Trang Chủ</a>
              <a href="/register" className="hover:text-orange-400 transition-colors">Đăng Ký</a>
              <a href="/giftcodes" className="hover:text-orange-400 transition-colors">Giftcode</a>
              <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="hover:text-amber-300 transition-colors font-medium">k.mio.io.vn</a>
            </div>
          </div>
        </footer>
      </body>
    </html>
  );
}
