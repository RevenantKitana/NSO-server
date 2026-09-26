import { NextResponse } from 'next/server';
import { queryDb } from '@/lib/db';

export const dynamic = 'force-dynamic';

export async function GET() {
  try {
    const rows = await queryDb(
      `SELECT id, code, type, gold, coin, yen, items, expires_at, created_at 
       FROM gift_codes 
       WHERE status = 0 AND (expires_at IS NULL OR expires_at > NOW()) 
       ORDER BY id DESC LIMIT 50`
    );

    const giftcodes = rows.map((row) => {
      let parsedItems = [];
      try {
        if (row.items) {
          parsedItems = typeof row.items === 'string' ? JSON.parse(row.items) : row.items;
        }
      } catch (e) {
        parsedItems = [];
      }

      return {
        id: row.id,
        code: row.code,
        type: row.type === 1 ? 'Mỗi nhân vật 1 lần' : 'Dùng chung',
        gold: row.gold || 0,
        coin: row.coin || 0,
        yen: row.yen || 0,
        itemsCount: parsedItems.length,
        items: parsedItems,
        expiresAt: row.expires_at,
      };
    });

    return NextResponse.json({ success: true, giftcodes });
  } catch (error) {
    console.error('Giftcodes fetch error:', error);
    return NextResponse.json(
      { success: false, error: 'Không thể tải danh sách giftcode: ' + error.message, giftcodes: [] },
      { status: 500 }
    );
  }
}
