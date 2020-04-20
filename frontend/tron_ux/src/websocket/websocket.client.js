/**
 * Description: Websocket client variables and helpers
 */

const wsURLs = {
  // gameServer: "ws://localhost:9000/ws", // java netty (gameserver)
  gameServer: "ws://localhost:9090/ws", // local nodejs websocket test server
  uxServer: "ws://localhost:9091/ws", // TODO: Serverside nodejs ws server
};

export function connectToWSGameServer() {
  const wsGS = new WebSocket(wsURLs.gameServer);
  return wsGS;
}

export function connectToWSUXServer(token) {
  const wsUX = new WebSocket(wsURLs.uxServer);
  return wsUX;
}

// JSON
export const query = {
  loadGames: { subject: "updatePublicGames" }
}

