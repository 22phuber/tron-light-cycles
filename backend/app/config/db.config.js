const dbHost = process.env.MYSQL_HOST || "localhost"
const dbUser = process.env.MYSQL_USER || "tron"
const dbPass = process.env.MYSQL_PASSWORD || "tron"
const dbName = process.env.MYSQL_DATABASE || "myDB"

module.exports = {
  HOST: dbHost,
  USER: dbUser,
  PASSWORD: dbPass,
  DB: dbName,
  dialect: "mysql",
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000
  }
};
