/**
 * Description: Websocket helpers
 */

// Connections
const WSURL = Object.freeze({
  nettyGameServer: "ws://localhost:9000/ws", // java netty (gameserver)
  gameServer: "ws://localhost:9090/ws", // local nodejs websocket test server
});

export function connectToWSNettyGameServer() {
  return new WebSocket(WSURL.nettyGameServer);
}

export function connectToWSGameServer() {
  return new WebSocket(WSURL.gameServer);
}

// Settings

// JSON query objects
// see: https://github.com/22phuber/tron-light-cycles/blob/develop/documents/client-server-messaging/json_messages.md
export const QUERY = Object.freeze({
  CLIENTCONNECTED: { subject: "clientConnected" },
  UPDATEPUBLICGAMES: { subject: "currentPublicGames" },
  UPDATEDIRECTION: { subject: "updateDirection", key: "" },
  CREATEGAME: {
    subject: "createGame",
    gameConfig: {
      name: "theChosenGameName",
      public: true,
      mode: "classic",
      playersAllowed: 5,
      playing: false,
      host: "0123456789",
    },
  },
  DELETEGAME: {
    subject: "deleteGame",
    gameId: "theGameId",
  },
  JOINGAME: {
    subject: "joinGame",
    clientId: "theClientId",
    gameId: "theGameId",
    color: "playerColor",
  },
  STARTGAME: { subject: "startGame" },
});
