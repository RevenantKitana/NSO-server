/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  async rewrites() {
    return [
      {
        source: '/NSO.jar',
        destination: '/api/download',
      },
    ];
  },
};

export default nextConfig;
