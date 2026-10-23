import { NextResponse } from 'next/server';

export const dynamic = 'force-dynamic';

export async function GET() {
  try {
    const bridgeUrl = (process.env.BRIDGE_API_URL || 'http://168.107.66.164').replace(/\/$/, '');
    const targetUrl = `${bridgeUrl}/download/NSO.jar`;

    const response = await fetch(targetUrl, {
      cache: 'no-store',
      headers: {
        Accept: 'application/java-archive, application/octet-stream, */*',
      },
    });

    if (!response.ok) {
      console.warn(`[Download] VM Bridge returned status ${response.status} from ${targetUrl}`);
      return NextResponse.json(
        {
          success: false,
          error: `Không thể tải Client từ máy chủ game (Mã phản hồi: ${response.status}). Vui lòng liên hệ Admin hoặc thử lại sau!`,
        },
        { status: response.status === 404 ? 404 : 502 }
      );
    }

    const contentType = response.headers.get('content-type') || 'application/java-archive';
    const contentLength = response.headers.get('content-length');
    const arrayBuffer = await response.arrayBuffer();

    const headers = new Headers();
    headers.set('Content-Type', contentType);
    headers.set('Content-Disposition', 'attachment; filename="NSO.jar"');
    if (contentLength) {
      headers.set('Content-Length', contentLength);
    }
    headers.set('Cache-Control', 'public, max-age=60, s-maxage=60');

    return new Response(arrayBuffer, {
      status: 200,
      headers,
    });
  } catch (error) {
    console.error('[Download] Error streaming NSO.jar from VM:', error.message);
    return NextResponse.json(
      {
        success: false,
        error: 'Máy chủ tải game hiện không phản hồi hoặc đang bảo trì. Vui lòng thử lại sau!',
      },
      { status: 503 }
    );
  }
}
