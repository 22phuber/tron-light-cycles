# Tron light cycles computer game

*ZHAW PSIT3 Project*

## Team
* Deniz Akca (akcaden1@students.zhaw.ch)
* Christian Holenstein (holenchr@students.zhaw.ch)
* Patrick Huber (huberpa4@students.zhaw.ch)
* Mike Iten (itenmik1@students.zhaw.ch)

# Development environment

## Java Gameserver

* Use Intellij for java development. Open project folder: `gameserver`

1. Start `GameServer.main()` in Intellij
2. Goto `http://localhost:9000` to connect to the Gameserver

## React frontend

1. Install nodejs (npm)
2. Open `frontend/tron_ux` folder in cli
3. run `npm install`
4. run `npm start` to start the development server on `http://localhost:3000`

Use Visual Studio Code or another Javascript/React compatible IDE. Open project folder `frontend/tron_ux`

## Node backend

1. Install nodejs (npm)
2. Open `backend` folder in cli
3. run `npm install`
4. run `node server.js` to start the node server on `http://localhost:8080`

Original source guide: https://bezkoder.com/node-js-jwt-authentication-mysql/

# Build and run docker compose

This starts a production ready docker-compose

1. Install Docker and docker-compose
2. Open `docker/` folder
3. run `./pre-build.sh`
4. run `docker-compose build --no-cache`
5. run `docker-compose up`
6. Open `http://localhost/` in your browser

