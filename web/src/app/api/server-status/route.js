import { NextResponse } from 'next/server';
import net from 'net';
import { queryDb } from '@/lib/db';

export const dynamic = 'force-dynamic';

function checkSocket(host, port, timeout = 2500) {
  return new Promise((resolve) => {
    const socket = new net.Socket();
    let isConnected = false;

    socket.setTimeout(timeout);

    socket.on('connect', () => {
      isConnected = true;
      socket.destroy();
      resolve(true);
    });

    socket.on('timeout', () => {
      socket.destroy();
      resolve(false);
    });

    socket.on('error', () => {
      socket.destroy();
      resolve(false);
    });

    socket.connect(port, host);
  });
}

export async function GET() {
  try {
    const serverHost = process.env.GAME_SERVER_HOST || process.env.MYSQL_HOST || '127.0.0.1';
    const serverPort = Number(process.env.GAME_SERVER_PORT) || 14444;

    const isOnline = await checkSocket(serverHost, serverPort);

    let totalUsers = 0;
    let onlineUsers = 0;

    try {
      const stats = await queryDb(
        `SELECT 
           COUNT(*) as total,
           SUM(CASE WHEN online = 1 THEN 1 ELSE 0 END) as online
         FROM users`
      );
      if (stats.length > 0) {
        totalUsers = Number(stats[0].total) || 0;
        onlineUsers = Number(stats[0].online) || 0;
      }
    } catch (dbErr) {
      console.warn('DB stats query error in status:', dbErr.message);
    }

    // Never expose raw IP or Port to the client/frontend
    return NextResponse.json({
      success: true,
      server: {
        serverName: 'NSO Custom (Mod by Khánh)',
        status: isOnline ? 'ONLINE' : 'OFFLINE',
        isOnline,
        totalUsers,
        onlineUsers,
        lastChecked: new Date().toISOString(),
      },
    });
  } catch (error) {
    return NextResponse.json(
      {
        success: false,
        server: { serverName: 'NSO Custom (Mod by Khánh)', status: 'OFFLINE', isOnline: false, totalUsers: 0, onlineUsers: 0 },
      },
      { status: 500 }
    );
  }
}
