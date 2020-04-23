## Client-Server-Messaging

### On opening ws-connection

|Description|JSON|further information|
|---|---|---|
|Game server sends client the id of the just created connection.|`{ "subject": "clientId", "id": "theClientId" }`||
|Client requests all currently publicly open games.|`{ "subject": "currentPublicGames" }`|
|Game server sends all currently publicly open games and their associated properties to client who requested them.|`{ "subject": "currentPublicGames", "games": [ { "id": "theGameId", "name": "theGameName", "playersJoined": int, "playersAllowed": int, "mode": "gameMode", }, {...}, ...] }`|gameMode = "classic" or "battleRoyale"|


### On game creation
|Description|JSON|further information|
|---|---|---|
|Client requests to create a new game.|`{ "subject": "createGame", "gameConfig": { "name": "theChosenGameName", "visibility": "theChosenVisibility", "mode": "theChosenGameMode", "playersAllowed": int }, "hostName": "theClientsName" }`|gameMode = "classic" or "battleRoyale", visibility = "public" or "private"|
|GameServer sends id of the just created game to client who requested the creation.|`{ "subject": "createGame", "gameId": "theGameId" }`|


### Before game start
|Description|JSON|further information|
|---|---|---|
|Host Client can send Lobby-Configs to the Game server.|`{ "subject": "lobbyConfig", "lobbyConfig": [ { "play": false, "public": false , "game": "classic"}] }`|*@deniz: duplicate? see on game creation*|
|Game server continously updates all joined clients by broadcasting lobby-state.|`{ "subject": "lobbyState", players: [ { "clientId": "theClientId", "name": "thePlayersName" , "ready": "false"}, {...}, ...] }`|**continous broadcast**|
|Client requests to join a specific game.|`{ "subject": "joinGame", "clientId": "theClientId", "gameId": "theGameId" }`|
|Game server sends the client the game configurations the client needs to know about in order to display the game correctly.|`{ "subject": "gameConfig", "width": int, "height": int, "lineThickness": int }`||
|Client that is host of game to be started orders game server to start the game.| `{ "subject": "startGame" }`|


### On game start
|Description|JSON|further information|
|---|---|---|
|Game server broadcasts initial game state to all players being part of the starting game.|`{ "subject": "initialGameState", "gameId": "theGameId" ,"players": [ { "id": "theClientId", "posx": int, "posy": int, "dir": int, "color": "rgb(int,int,int)" }, {...}, ... ] }`|**broadcast**|
|Game server broadcasts countdown to all players being part of the starting game.|`{ "subject": "countdown", "count": int }`|**continous broadcast**|


### While game's running
|Description|JSON|further information|
|---|---|---|
|Game server continously broadcasts current game state to all clients being part of running game.|`{ "subject": "gameState", "gameId": "theGameId", "players": [ { "clientId": "theClientId", "posx": int, "posy": int, "dir": int, "color": "rgb(int,int,int)" }, {...}, ... ] }`|**continous broadcast**|
|Client sends direction update on key event.|`{ subject: "updateDirection", "key": "key" }`|
|Game server broadcasts occuring deaths to all clients being poart of running game.|`{ "subject": "playerDeath", "gameId": "theGameId", "playerId": "theDeadPlayerId", "posx": int, "posy": int }`|**broadcast**|
