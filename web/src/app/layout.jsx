import './globals.css';
import Mascot from './components/Mascot';

export const metadata = {
  title: 'NSO Private Server - Phi Lợi Nhuận | Mod bởi [Khánh]',
  description: 'Máy chủ Ninja School Online Private phi lợi nhuận tùy biến bởi [Khánh]. Không nạp thẻ, cày cuốc hoài niệm, nhận Giftcode tân thủ và tải game miễn phí.',
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
            Máy chủ NSO Private Phi Lợi Nhuận • Liên hệ{' '}
            <a
              href="https://k.mio.io.vn"
              target="_blank"
              rel="noopener noreferrer"
              className="underline font-bold text-amber-300 hover:text-white transition-colors"
            >
              [Khánh]
            </a>{' '}
            để nhận mã OTP cấp phép đăng ký
          </span>
        </div>

        {/* Navigation Bar */}
        <header className="sticky top-0 z-50 backdrop-blur-xl bg-[#07090e]/85 border-b border-white/5">
          <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 h-20 flex items-center justify-between">
            {/* Mascot & Brand Logo */}
            <div className="flex items-center gap-3">
              <Mascot size={46} />
              <a href="/" className="flex flex-col group">
                <span className="text-xl font-black tracking-wider bg-gradient-to-r from-white via-slate-100 to-orange-300 bg-clip-text text-transparent group-hover:to-orange-400 transition-colors">
                  NSO PRIVATE
                </span>
                <span className="text-[11px] tracking-wider uppercase font-semibold text-orange-400/90 -mt-0.5">
                  Phi lợi nhuận • Mod bởi{' '}
                  <a
                    href="https://k.mio.io.vn"
                    target="_blank"
                    rel="noopener noreferrer"
                    className="hover:underline text-amber-300 font-bold"
                  >
                    [Khánh]
                  </a>
                </span>
              </a>
            </div>

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
                Tải Game
              </a>
              <a
                href="https://k.mio.io.vn"
                target="_blank"
                rel="noopener noreferrer"
                className="px-4 py-2 rounded-full text-sm font-semibold text-amber-300 hover:text-white hover:bg-amber-500/10 transition-colors"
              >
                Liên hệ [Khánh] ↗
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
              <Mascot size={36} />
              <p className="text-sm">
                Máy chủ Ninja School Online Private phi lợi nhuận • Tùy biến và duy trì bởi{' '}
                <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="text-orange-400 font-bold hover:underline">
                  [Khánh]
                </a>.
              </p>
            </div>
            <div className="flex items-center gap-6 text-sm">
              <a href="/" className="hover:text-orange-400 transition-colors">Trang Chủ</a>
              <a href="/register" className="hover:text-orange-400 transition-colors">Đăng Ký</a>
              <a href="/giftcodes" className="hover:text-orange-400 transition-colors">Giftcode</a>
              <a href="/#download" className="hover:text-orange-400 transition-colors">Tải Client</a>
              <a href="https://k.mio.io.vn" target="_blank" rel="noopener noreferrer" className="hover:text-amber-300 transition-colors font-medium">[Khánh]</a>
            </div>
          </div>
        </footer>
      </body>
    </html>
  );
}
