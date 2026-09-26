'use client';

import { useState, useEffect } from 'react';

export default function GiftcodesPage() {
  const [giftcodes, setGiftcodes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [copiedCode, setCopiedCode] = useState('');

  useEffect(() => {
    async function loadGiftcodes() {
      try {
        const res = await fetch('/api/giftcodes');
        const data = await res.json();
        if (data.success) {
          setGiftcodes(data.giftcodes || []);
        } else {
          setError(data.error || 'Không thể lấy danh sách giftcode');
        }
      } catch (err) {
        setError('Lỗi kết nối máy chủ: ' + err.message);
      } finally {
        setLoading(false);
      }
    }
    loadGiftcodes();
  }, []);

  const handleCopy = (code) => {
    navigator.clipboard.writeText(code);
    setCopiedCode(code);
    setTimeout(() => setCopiedCode(''), 2500);
  };

  return (
    <div className="flex-1 w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      {/* Header */}
      <div className="text-center max-w-3xl mx-auto mb-12">
        <div className="inline-flex items-center gap-2 px-3.5 py-1.5 rounded-full bg-amber-500/10 border border-amber-500/20 text-amber-400 text-xs font-bold uppercase tracking-wider mb-4">
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v13m0-13V6a2 2 0 112 2h-2zm0 0V5.5A2.5 2.5 0 109.5 8H12zm-7 4h14M5 12a2 2 0 110-4h14a2 2 0 110 4M5 12v7a2 2 0 002 2h10a2 2 0 002-2v-7" />
          </svg>
          Quà Tặng Từ Khánh
        </div>
        <h1 className="text-3xl sm:text-5xl font-black text-white tracking-tight">
          DANH SÁCH <span className="bg-gradient-to-r from-amber-400 to-orange-500 bg-clip-text text-transparent">GIFTCODE</span>
        </h1>
        <p className="mt-4 text-slate-400 text-sm sm:text-base">
          Sao chép mã bên dưới và nhập tại NPC Okanehashi trong game để nhận phần thưởng khởi đầu!
        </p>
      </div>

      {/* Guide Box */}
      <div className="mb-10 max-w-4xl mx-auto p-5 rounded-2xl bg-white/[0.02] border border-white/5 flex flex-col sm:flex-row items-center justify-between gap-4 text-xs sm:text-sm text-slate-300">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-xl bg-orange-500/10 border border-orange-500/20 flex items-center justify-center text-orange-400 font-bold shrink-0">
            ℹ
          </div>
          <div>
            <p className="font-semibold text-white">Cách nhập Giftcode trong game:</p>
            <p className="text-slate-400 text-xs mt-0.5">
              Gặp NPC Okanehashi tại các Làng &gt; Chọn mục &quot;Nhập Giftcode&quot; &gt; Dán mã và Xác nhận để nhận quà vào hành trang.
            </p>
          </div>
        </div>
      </div>

      {/* Content State */}
      {loading ? (
        <div className="flex flex-col items-center justify-center py-20 gap-4">
          <div className="w-10 h-10 border-4 border-orange-500/20 border-t-orange-500 rounded-full animate-spin" />
          <p className="text-slate-400 text-sm font-medium">Đang tải danh sách Giftcode...</p>
        </div>
      ) : error ? (
        <div className="p-6 rounded-2xl bg-red-500/10 border border-red-500/20 text-red-300 text-center max-w-lg mx-auto">
          <p className="font-semibold">{error}</p>
        </div>
      ) : giftcodes.length === 0 ? (
        <div className="text-center py-20 bg-white/[0.02] border border-white/5 rounded-3xl max-w-2xl mx-auto p-8">
          <div className="w-16 h-16 rounded-2xl bg-white/5 mx-auto flex items-center justify-center text-2xl text-slate-500 mb-4">
            🎁
          </div>
          <h3 className="text-lg font-bold text-white">Hiện chưa có Giftcode công khai</h3>
          <p className="text-xs text-slate-400 mt-2">
            Khánh sẽ cập nhật thêm các mã quà tặng sự kiện tại đây. Hãy theo dõi thường xuyên nhé!
          </p>
        </div>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {giftcodes.map((gc) => (
            <div
              key={gc.id}
              className="bg-white/[0.03] border border-white/10 hover:border-amber-500/40 rounded-3xl p-6 backdrop-blur-xl transition-all duration-300 hover:-translate-y-1 hover:shadow-xl hover:shadow-orange-500/5 flex flex-col justify-between group"
            >
              <div>
                {/* Card Top: Type & Expiration */}
                <div className="flex items-center justify-between gap-2 mb-4">
                  <span className="px-3 py-1 rounded-full text-[11px] font-bold bg-amber-500/10 border border-amber-500/30 text-amber-300">
                    {gc.type}
                  </span>
                  <span className="text-[11px] text-slate-400 font-medium">
                    {gc.expiresAt
                      ? `Hạn: ${new Date(gc.expiresAt).toLocaleDateString('vi-VN')}`
                      : 'Vô thời hạn'}
                  </span>
                </div>

                {/* Giftcode Box */}
                <div className="p-4 rounded-2xl bg-black/40 border border-white/10 flex items-center justify-between gap-3 mb-5">
                  <span className="font-mono font-black text-xl text-amber-300 tracking-wider">
                    {gc.code}
                  </span>
                  <button
                    onClick={() => handleCopy(gc.code)}
                    className="px-3 py-1.5 rounded-xl text-xs font-bold transition-all flex items-center gap-1.5 shadow-sm active:scale-95 bg-orange-500/20 hover:bg-orange-500/30 text-orange-300 border border-orange-500/30"
                  >
                    {copiedCode === gc.code ? (
                      <>
                        <svg className="w-4 h-4 text-emerald-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 13l4 4L19 7" />
                        </svg>
                        <span className="text-emerald-400">Đã chép!</span>
                      </>
                    ) : (
                      <>
                        <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M8 16H6a2 2 0 01-2-2V6a2 2 0 012-2h8a2 2 0 012 2v2m-6 12h8a2 2 0 002-2v-8a2 2 0 00-2-2h-8a2 2 0 00-2 2v8a2 2 0 002 2z" />
                        </svg>
                        Sao chép
                      </>
                    )}
                  </button>
                </div>

                {/* Currency Rewards */}
                <div className="space-y-2 mb-4">
                  <div className="text-xs font-bold text-slate-400 uppercase tracking-wider">
                    Phần thưởng tiền tệ:
                  </div>
                  <div className="grid grid-cols-3 gap-2">
                    <div className="p-2 rounded-xl bg-white/[0.02] border border-white/5 text-center">
                      <div className="text-[10px] text-slate-400 font-semibold">Lượng</div>
                      <div className="text-xs font-extrabold text-amber-400">{gc.gold.toLocaleString()}</div>
                    </div>
                    <div className="p-2 rounded-xl bg-white/[0.02] border border-white/5 text-center">
                      <div className="text-[10px] text-slate-400 font-semibold">Xu</div>
                      <div className="text-xs font-extrabold text-yellow-300">{gc.coin.toLocaleString()}</div>
                    </div>
                    <div className="p-2 rounded-xl bg-white/[0.02] border border-white/5 text-center">
                      <div className="text-[10px] text-slate-400 font-semibold">Yên</div>
                      <div className="text-xs font-extrabold text-cyan-300">{gc.yen.toLocaleString()}</div>
                    </div>
                  </div>
                </div>

                {/* Items List (if any) */}
                {gc.items && gc.items.length > 0 && (
                  <div className="space-y-2">
                    <div className="text-xs font-bold text-slate-400 uppercase tracking-wider">
                      Vật phẩm kèm theo ({gc.items.length}):
                    </div>
                    <div className="max-h-32 overflow-y-auto space-y-1 pr-1 custom-scrollbar">
                      {gc.items.map((item, idx) => (
                        <div
                          key={idx}
                          className="flex items-center justify-between text-xs px-2.5 py-1.5 rounded-lg bg-white/[0.02] border border-white/5 text-slate-300"
                        >
                          <span className="font-medium">Item #{item.id || item.item_id || 'N/A'}</span>
                          <span className="font-bold text-orange-400">x{item.quantity || item.count || 1}</span>
                        </div>
                      ))}
                    </div>
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
