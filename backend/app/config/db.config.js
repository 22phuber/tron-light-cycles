module.exports = {
  HOST: "localhost",
  USER: "tron",
  PASSWORD: "tron",
  DB: "tron_light_cycles",
  dialect: "mysql",
  pool: {
    max: 5,
    min: 0,
    acquire: 30000,
    idle: 10000
  }
};
