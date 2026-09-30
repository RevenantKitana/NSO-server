import { NextResponse } from 'next/server';
import { fetchFromBridge } from '@/lib/bridge';

export const dynamic = 'force-dynamic';

export async function GET() {
  try {
    const data = await fetchFromBridge('/api/giftcodes');
    return NextResponse.json(data);
  } catch (error) {
    console.error('Giftcodes fetch error:', error.message);
    return NextResponse.json(
      { success: false, error: 'Không thể tải danh sách giftcode: ' + error.message, giftcodes: [] },
      { status: 500 }
    );
  }
}
