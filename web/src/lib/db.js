import mysql from 'mysql2/promise';

let pool;

export function getDbPool() {
  if (!pool) {
    pool = mysql.createPool({
      host: process.env.MYSQL_HOST || '161.118.202.174',
      port: Number(process.env.MYSQL_PORT) || 3306,
      user: process.env.MYSQL_USER || 'nso_web',
      password: process.env.MYSQL_PASSWORD || 'NsoWebDb2026!@#',
      database: process.env.MYSQL_DATABASE || 'nso_test',
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
