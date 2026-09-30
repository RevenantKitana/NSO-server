import { NextResponse } from 'next/server';
import { fetchFromBridge } from '@/lib/bridge';

export const dynamic = 'force-dynamic';

export async function GET() {
  try {
    const data = await fetchFromBridge('/api/status');
    return NextResponse.json(data);
  } catch (error) {
    console.warn('Server status fetch error:', error.message);
    return NextResponse.json(
      {
        success: false,
        server: {
          serverName: 'NSO Custom (Mod by Khánh)',
          status: 'OFFLINE',
          isOnline: false,
          totalUsers: 0,
          onlineUsers: 0,
          lastChecked: new Date().toISOString(),
        },
      },
      { status: 200 }
    );
  }
}
