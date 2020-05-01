module.exports = (sequelize, Sequelize) => {
  const User = sequelize.define("users", {
    username: {
      type: Sequelize.STRING,
    },
    email: {
      type: Sequelize.STRING,
    },
    password: {
      type: Sequelize.STRING,
    },
    firstname: {
      type: Sequelize.STRING,
    },
    name: {
      type: Sequelize.STRING,
    },
    cycle_color: {
      type: Sequelize.STRING,
    },
    ranking: {
      type: Sequelize.BIGINT.UNSIGNED,
    },
  });

  return User;
};
