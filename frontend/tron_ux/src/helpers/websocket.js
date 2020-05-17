/**
 * Description: Websocket helpers
 */

// Connections
const WSURL = Object.freeze({
  // gameServer: "ws://localhost:9090/ws", // java netty (gameserver)
  gameServer: "ws://212.51.138.112:9090/ws", // deployed to supportblog.ch
});

export function connectToWSGameServer() {
  return new WebSocket(WSURL.gameServer);
}
// Settings

// JSON query objects
// see: https://github.com/22phuber/tron-light-cycles/blob/develop/documents/client-server-messaging/json_messages.md
export const QUERY = Object.freeze({
  // CLIENTCONNECTED: { subject: "clientConnected" }, // TODO: REMOVE
  UPDATEPUBLICGAMES: { subject: "currentPublicGames" },
  UPDATEDIRECTION: {
    subject: "updateDirection",
    gameId: "theGameId",
    key: "keyPressed",
  },
  CREATEGAME: {
    subject: "createGame",
    gameConfig: {
      name: "theChosenGameName",
      public: true,
      mode: "classic",
      playersAllowed: 5,
    },
    host: {
      playerName: "theChosenPlayerName",
      color: "rgb(0,0,0)",
    },
  },
  DELETEGAME: {
    subject: "deleteGame",
    gameId: "theGameId",
  },
  JOINGAME: {
    subject: "joinGame",
    gameId: "theGameId",
    playerName: "thePlayersName",
    playerColor: "rgb(0,0,0)",
  },
  LEAVEGAME: { subject: "leaveGame", gameId: "theGameId" },
  STARTGAME: { subject: "startGame", gameId: "theGameId" },
  PLAYERCONFIGUPDATE: {
    subject: "playerConfigUpdate",
    playerName: "thePlayersName",
    playerColor: "rgb(int,int,int)",
    gameId: "theGameId",
  },
});
