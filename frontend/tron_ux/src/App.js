import React, { useState, useRef, useEffect } from "react";
import "./App.css";
import { WebsocketSubjectMissing } from "./helpers/exceptions";
import * as WSHelpers from "./helpers/websocket";
import { DIRECTIONKEYS } from "./helpers/helpers";
import { useInterval } from "./helpers/custom.hooks";
import { ThemeProvider, makeStyles } from "@material-ui/styles";
import {
  CssBaseline,
  Typography,
  createMuiTheme,
  Paper,
  Box,
  Container,
  CircularProgress,
} from "@material-ui/core";
import TronAppBar from "./components/appBar/appBar.component";
import GameTable from "./components/table/table.component";
import CreateGame from "./components/createGame/createGame.component";
import Footer from "./components/footer/footer.component";
import GameCanvas from "./components/gameCanvas/gameCanvas.component";
import LobbyTable from "./components/lobby/lobby.component";

const theme = createMuiTheme({
  palette: {
    type: "dark",
  },
});

const useStyles = makeStyles({
  box: {
    opacity: "0.975",
  },
  typography: {
    textShadow: " 0px 0px 22px rgba(145, 253, 253, 1);",
  },
  circularProgress: {
    margin: "25px",
  },
});

// fake it
const lobbyPlayersArray = [
  {
    clientid: "c-47Bs2323d2xdcxwd23qdex23qd_zTEOZ7-U7TigC7g",
    name: "Ready Player 1",
    readyState: "true",
    color: "blue",
  },
  {
    clientid: "T6FVRKq32ed23d2dxwekSH0e-lDdL7FtH_w",
    name: "IAMGROOT",
    readyState: "false",
    color: "gray",
  },
  {
    id: "1234567890",
    name: "this is me!",
    readyState: "false",
    color: "black",
  },
  {
    clientid: "tlyoDxNs3232cxdaV1EucmSlcfM-9yA",
    name: "Irish shizzle",
    readyState: "true",
    color: "green",
  },
  {
    id: "h5diOYzwdEd23d23d23d23SSmWE6yubojQ",
    name: "mike van dike",
    readyState: "false",
    color: "pink",
  },
];

const playerIdInitial = "1234567890";

/* APP */
const App = () => {
  const classes = useStyles();
  // State: Application modes
  const [appState, setAppState] = useState({
    playMode: false,
    lobbyMode: false,
  });
  // State: websocket & reference
  const websocketClient = useRef(null);
  const [websocketState, setWebsocketState] = useState({
    websocketClient: websocketClient,
    reconnectTimeout: 250,
    wsError: false,
  });
  // State: Game data
  const [gameData, setGameData] = useState({
    player: { playerName: "thePlayerName", clientId: "theClientIds" },
    gameConfig: { height: 400, width: 400, lineThickness: 5 },
    publicGames: null,
    lobbyState: {
      players: [
        { clientId: "theClientId", name: "thePlayerName", ready: false },
      ],
    },
    initialGameState: {
      gameId: "theGameId",
      players: [
        {
          clientId: "theClientId",
          posx: 3,
          posy: 3,
          dir: 3,
          color: "rgb(22,22,22)",
        },
      ],
    },
    countdown: { count: 5 },
  });
  const [playData, setPlayData] = useState({
    gameState: {
      gameId: "theGameId",
      players: [
        {
          clientId: "theClientId",
          posx: 3,
          posy: 3,
          dir: 3,
          color: "rgb(22,22,22)",
        },
      ],
    },
    playerDeath: {
      gameId: "theGameId",
      clientId: "theDeadPlayerId",
      posx: 66,
      posy: 66,
    },
  });
  const [wsplayerdata, setWsPlayerData] = useState(null); // -> playData
  // Request Animation Frame variable
  let rAF;

  const [playerId, setPlayerId] = useState(null); // => player: { name: "", id: "" }
  const [gameCanvas, setGameCanvas] = useState({ height: 400, width: 400 }); // => gameConfig
  // lobby players
  const [lobbyPlayers, setLobbyPlayers] = useState(null); // inside lobbyState
  const [lobbyData, setLobbyData] = useState({}); // lobbyState

  useEffect(() => {
    setLobbyPlayers(lobbyPlayersArray);
    setPlayerId(playerIdInitial);

    handleWebsocket();
    document.addEventListener("keydown", handleKeyPress, false);
    return () => {
      console.log("useEffect ws.close() called");
      if (
        websocketClient.current &&
        websocketClient.current.readyState === WebSocket.OPEN
      )
        websocketClient.current.close();
      console.log("useEffect remove eventistener called");
      document.removeEventListener("keydown", handleKeyPress, false);
    };
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    return () => {
      console.log("cancelAnimationFrame called");
      cancelAnimationFrame(rAF);
    };
  }, [rAF]);

  useInterval(() => {
    loadGames();
  }, 1000 * 20);

  // handles websocket connection
  function handleWebsocket() {
    var connectInterval;
    websocketClient.current = WSHelpers.connectToWSGameServer();
    //ws.current = WebsocketHelpers.connectToWSNettyGameServer();
    // OPEN
    websocketClient.current.onopen = () => {
      websocketState.wsError &&
        setWebsocketState({ ...websocketState, wsError: false });
      console.log("Websocket connected");
      clearTimeout(connectInterval);
      loadGames();
    };

    // ONMESSAGE
    websocketClient.current.onmessage = (message) => {
      let dataFromServer = null;
      try {
        dataFromServer = JSON.parse(message.data);
      } catch (error) {
        console.error("JSON parse error: " + error);
      }
      if (dataFromServer && dataFromServer.subject) {
        switch (dataFromServer.subject) {
          case "gameState":
            rAF = window.requestAnimationFrame(() => {
              handlePlayerData(dataFromServer.players);
            });
            break;
          case "currentPublicGames":
            console.log(dataFromServer.Games);
            console.log(gameData.publicGames);
            // if(gameData.publicGames !== dataFromServer.Games )
            setGameData({ ...gameData, publicGames: dataFromServer.games });
            break;
          case "canvas config":
            if (dataFromServer.width && dataFromServer.height) {
              setGameCanvas({
                height: dataFromServer.height,
                width: dataFromServer.width,
              });
            }
            break;
          default:
            console.log("default subject");
            break;
        }
      } else {
        throw new WebsocketSubjectMissing();
      }
    };

    // error
    websocketClient.current.onerror = (error) => {
      console.error(
        "Websocket couldn't connect ",
        error.message,
        "Closing websocket"
      );
      setWebsocketState({ ...websocketState, wsError: true });
      websocketClient.current.close();
    };

    // close
    websocketClient.current.onclose = (e) => {
      setWsPlayerData(null); // reset data (removes artifacts)
      console.log(
        `Websocket is closed. Reconnect will be attempted in ${Math.min(
          10000 / 1000,
          (websocketState.reconnectTimeout + websocketState.reconnectTimeout) /
            1000
        )} second.`,
        e.reason
      );
      websocketState.reconnectTimeout =
        websocketState.reconnectTimeout + websocketState.reconnectTimeout;
      connectInterval = setTimeout(
        checkWsState,
        Math.min(10000, websocketState.reconnectTimeout)
      ); //call check function after timeout
    };
  }

  // load game for public game list
  function loadGames() {
    if (
      !appState.playMode &&
      !appState.lobbyMode &&
      websocketClient.current.readyState === WebSocket.OPEN
    ) {
      console.log("loadGames");
      sendWsData(WSHelpers.QUERY.UPDATEPUBLICGAMES);
    }
  }

  // Check if connection ist lost and try to reconnect
  function checkWsState() {
    if (
      !websocketClient.current ||
      websocketClient.current.readyState === WebSocket.CLOSED
    ) {
      handleWebsocket(websocketClient);
    }
  }

  // playerData for game rendering
  function handlePlayerData(data) {
    setWsPlayerData(data);
  }

  // sends data to websocket server
  function sendWsData(data) {
    if (
      websocketClient.current &&
      websocketClient.current.readyState === WebSocket.OPEN
    ) {
      websocketClient.current.send(JSON.stringify(data));
    } else {
      console.log("Websocket not ready");
    }
  }

  // key press for game directions
  function handleKeyPress(event) {
    const pressedKey = event.key;
    if (DIRECTIONKEYS.includes(pressedKey)) {
      sendWsData({
        ...WSHelpers.QUERY.UPDATEDIRECTION,
        key: pressedKey,
      });
    }
  }

  // handle create new game/lobby data
  function handleCreateGame(event) {
    event.preventDefault();
    let createGameTempData = {};
    for (const [key, value] of new FormData(event.target).entries()) {
      createGameTempData[key] = value;
    }
    setLobbyData(createGameTempData);
    setAppState({ gameMode: true, lobbyMode: true });
  }

  // cancel Lobby & gameMode
  function cancelLobby() {
    setAppState({ gameMode: false, lobbyMode: false });
  }

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <header>
        <TronAppBar />
      </header>
      {!appState.playMode ? (
        <React.Fragment>
          <section>
            <Container maxWidth="lg">
              <Box my={4} className={classes.box}>
                <Typography
                  variant="h2"
                  component="h2"
                  gutterBottom
                  className={classes.typography}
                >
                  Public games
                </Typography>
                <GameTable publicGames={gameData.publicGames} />
              </Box>
            </Container>
          </section>
          <section>
            <Container maxWidth="lg">
              <Box my={4} className={classes.box}>
                <Typography
                  variant="h2"
                  component="h2"
                  gutterBottom
                  className={classes.typography}
                >
                  Create Game
                </Typography>
                <Paper>
                  <CreateGame handleSubmit={handleCreateGame} />
                </Paper>
              </Box>
            </Container>
          </section>
        </React.Fragment>
      ) : (
        <section>
          {appState.lobbyMode ? (
            <React.Fragment>
              <section>
                <Container maxWidth="lg">
                  <Box my={4} className={classes.box}>
                    <Typography
                      variant="h2"
                      component="h2"
                      gutterBottom
                      className={classes.typography}
                    >
                      Lobby
                    </Typography>
                    <Paper>
                      <LobbyTable
                        exitLobby={cancelLobby}
                        lobbyPlayers={lobbyPlayers}
                        lobbyData={lobbyData}
                        myPlayerId={playerId}
                      />
                    </Paper>
                  </Box>
                </Container>
              </section>
            </React.Fragment>
          ) : (
            <section>
              {wsplayerdata && !websocketState.wsError ? (
                <GameCanvas
                  width={gameCanvas.width}
                  height={gameCanvas.height}
                  playersData={wsplayerdata}
                />
              ) : (
                <div>
                  Connecting to Game server...
                  <br />
                  <CircularProgress
                    color="inherit"
                    className={classes.circularProgress}
                  />
                </div>
              )}
            </section>
          )}
        </section>
      )}

      <Footer />
    </ThemeProvider>
  );
};

export default App;
