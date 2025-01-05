/** @type {import('next').NextConfig} */
const nextConfig = {
    async rewrites() {
      return [
        {
          source: '/api/:path*', // フロントエンドから`/api`で始まるリクエストを受け取る
          destination: 'http://localhost:8080/:path*', // バックエンドのURLに転送
        },
      ];
    },
  };
  
  export default nextConfig;
  