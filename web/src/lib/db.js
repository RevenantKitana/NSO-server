import mysql from 'mysql2/promise';

let pool;

export function getDbPool() {
  if (!pool) {
    const host = process.env.MYSQL_HOST;
    const port = Number(process.env.MYSQL_PORT) || 3306;
    const user = process.env.MYSQL_USER;
    const password = process.env.MYSQL_PASSWORD;
    const database = process.env.MYSQL_DATABASE;

    if (!host || !user || !password || !database) {
      throw new Error(
        'Thiếu cấu hình biến môi trường Database (MYSQL_HOST, MYSQL_USER, MYSQL_PASSWORD, MYSQL_DATABASE). Vui lòng cấu hình file .env.local hoặc Vercel Environment Variables!'
      );
    }

    pool = mysql.createPool({
      host,
      port,
      user,
      password,
      database,
      waitForConnections: true,
      connectionLimit: 10,
      queueLimit: 0,
      connectTimeout: 10000,
    });
  }
  return pool;
}

export async function queryDb(sql, params = []) {
  const db = getDbPool();
  const [rows] = await db.execute(sql, params);
  return rows;
}
