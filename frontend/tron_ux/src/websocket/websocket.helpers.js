/**
 * Description: Websocket client variables and helpers
 */

const wsURLs = {
  nettyGameServer: "ws://localhost:9000/ws", // java netty (gameserver)
  gameServer: "ws://localhost:9090/ws", // local nodejs websocket test server
  uxServer: "ws://localhost:9091/ws", // TODO: Serverside nodejs ws server
};

export function connectToWSNettyGameServer() {
  console.log("connectToWSNettyGameServer called");
  const wsNGS = new WebSocket(wsURLs.nettyGameServer);
  return wsNGS;
}

export function connectToWSGameServer() {
  const wsGS = new WebSocket(wsURLs.gameServer);
  return wsGS;
}

export function connectToWSUXServer() {
  const wsUX = new WebSocket(wsURLs.uxServer);
  return wsUX;
}

// handle commands
export function handleWSServerData(subject, data) {
  if (subject && data) {
    switch (subject) {
      case "player update":
        if(data.players) return data.players;
        break;
      case "currentPublicGames":
        if(data.Games) return data.Games;
        break;
      case "canvas config":
        if (data.width && data.height) {
          return {
            height: data.height,
            width: data.width,
          };
        }
        break;
      default:
        console.log("default subject");
        break;
    }
  }
  return false;
}

// JSON queries
export const query = {
  loadGames: { subject: "updatePublicGames" },
  updateDirection: { subject: "update dir", key: "" },
};
