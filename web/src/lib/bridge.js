// ===============================================================================
// NSO WEB - SECURE BRIDGE API CLIENT (VERCEL / WEB TO VM)
// All database operations are proxied through VM's HTTP Bridge (Port 3306 is 100% closed)
// ===============================================================================

export async function fetchFromBridge(endpoint, options = {}) {
  const bridgeUrl = (process.env.BRIDGE_API_URL || 'http://168.107.66.164:8020').replace(/\/$/, '');
  const secretKey = process.env.BRIDGE_SECRET_KEY || 'NsoBridgeSecret2026!@#';

  const url = `${bridgeUrl}${endpoint.startsWith('/') ? endpoint : '/' + endpoint}`;

  const headers = {
    'Content-Type': 'application/json',
    'x-bridge-token': secretKey,
    ...(options.headers || {}),
  };

  const response = await fetch(url, {
    ...options,
    headers,
    cache: 'no-store',
  });

  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.error || `Bridge API returned status ${response.status}`);
  }

  return data;
}
