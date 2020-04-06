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

## NGiNX Reverse Proxy and Database

Start the react frontend prior to these steps.
*Optionally*: You can also start the java Gameserver if needed

1. Install Docker and docker-compose
2. Open `docker/` folder
3. run `docker-compose build`
4. run `docker-compose up`
5. Open `http://localhost/` in your browser


