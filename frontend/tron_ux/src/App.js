import React, { useState, useRef, useEffect } from "react";
import "./App.css";
import { WebsocketSubjectMissing } from "./helpers/exceptions";
import * as WSHelpers from "./helpers/websocket";
import { DIRECTIONKEYS,  getRandomName } from "./helpers/helpers";
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
import JoinGameDialog from "./components/joinGameDialog/joinGameDialog.component";

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

/* APP */
const App = () => {
  const classes = useStyles();
  // get and parse queryString
  const queryString = new URLSearchParams(window.location.search);
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
  // State: My Player
  const [myPlayerData, setMyPlayerData] = useState(
    JSON.parse(localStorage.getItem("myPlayerData")) || {
      name: getRandomName(),
      clientId: null,
      color: null,
      ready: true
    }
  );
  // State: Game data
  const [gameData, setGameData] = useState({
    gameConfig: {
      name: "theGameName",
      public: true,
      mode: "classic",
      playersAllowed: 0,
      playing: false,
      host: "1234567890",
      gameId: null,
    },
    canvasConfig: { height: 400, width: 400, lineThickness: 5 },
    publicGames: null,
    lobbyState: { players: [] },
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
  // const [playData, setPlayData] = useState({
  //   gameState: {
  //     gameId: "theGameId",
  //     players: [
  //       {
  //         clientId: "theClientId",
  //         posx: 3,
  //         posy: 3,
  //         dir: 3,
  //         color: "rgb(22,22,22)",
  //       },
  //     ],
  //   },
  //   playerDeath: {
  //     gameId: "theGameId",
  //     clientId: "theDeadPlayerId",
  //     posx: 66,
  //     posy: 66,
  //   },
  // });
  const [joinGameState, setJoinGameState] = useState({
    gameId: null,
    openJoinGameDialog: false,
  });
  const [wsplayerdata, setWsPlayerData] = useState(null); // -> playData
  // Request Animation Frame variable
  let rAF;

  useEffect(() => {
    localStorage.setItem("myPlayerData", JSON.stringify(myPlayerData));
  }, [myPlayerData]);

  useEffect(() => {
    handleWebsocket();
    if (queryString.has("id")) {
      console.log("Found gameid: " + queryString.get("id"));
      setJoinGameState({
        gameId: queryString.get("id"),
        openJoinGameDialog: true,
      });
    }
    return () => {
      console.log("useEffect ws.close() called");
      if (
        websocketClient.current &&
        websocketClient.current.readyState === WebSocket.OPEN
      )
        websocketClient.current.close();
    };
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    return () => {
      console.log("cancelAnimationFrame called");
      cancelAnimationFrame(rAF);
    };
  }, [rAF]);

  useEffect(() => {
    if (appState.playMode && !appState.lobbyMode) {
      document.addEventListener("keydown", handleKeyPress, false);
    } else {
      document.removeEventListener("keydown", handleKeyPress, false);
    }
    return () => {
      document.removeEventListener("keydown", handleKeyPress, false);
    };
    // eslint-disable-next-line
  }, [appState]);

  useInterval(() => {
    fetchStateFromGameServer();
  }, 1000 * 10);

  // handles websocket connection
  function handleWebsocket() {
    var connectInterval;
    websocketClient.current = WSHelpers.connectToWSGameServer();
    //ws.current = WebsocketHelpers.connectToWSNettyGameServer();
    // OPEN
    websocketClient.current.onopen = () => {
      console.log("Websocket connected");
      websocketState.wsError &&
        setWebsocketState({ ...websocketState, wsError: false });
      clearTimeout(connectInterval);
      fetchStateFromGameServer();
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
          case "clientId":
            setMyPlayerData((prevMyPlayerData) => {
              return { ...prevMyPlayerData, clientId: dataFromServer.id };
            });
            console.log("WS[clientId]: " + dataFromServer.id);
            break;
          case "currentPublicGames":
            setGameData((prevGameData) => {
              return { ...prevGameData, publicGames: dataFromServer.games };
            });
            //console.log("WS[currentPublicGames]: " + JSON.stringify(dataFromServer.games));
            break;
          case "canvasConfig":
            setGameData((prevGameData) => {
              return {
                ...prevGameData,
                canvasConfig: {
                  height: dataFromServer.height,
                  width: dataFromServer.width,
                  lineThickness: dataFromServer.lineThickness,
                },
              };
            });
            console.log("WS[canvasConfig]: " + JSON.stringify(dataFromServer));
            break;
          case "createGame":
            setGameData((prevGameData) => {
              return {
                ...prevGameData,
                gameConfig: {
                  ...gameData.gameConfig,
                  gameId: dataFromServer.gameId,
                },
              };
            });
            setAppState({ playMode: true, lobbyMode: true });
            // TODO: send back joinGame
            console.log("WS[createGame]: " + dataFromServer.gameId);
            console.log(window.location.href);
            break;
          case "lobbyState":
            setGameData((prevGameData) => {
              return {
                ...prevGameData,
                lobbyState: {
                  players: dataFromServer.players,
                },
              };
            });
            console.log(
              "WS[lobbyState]: " + JSON.stringify(dataFromServer.players)
            );
            break;
          default:
            console.error("WARN: Unknown subject");
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
      setWebsocketState((prevWebsocketState) => {
        return { ...prevWebsocketState, wsError: true };
      });
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

  // load public games and send client connected
  function fetchStateFromGameServer() {
    // Remove?
    // if (!myPlayerData.clientId) sendWsData(WSHelpers.QUERY.CLIENTCONNECTED);
    if (!appState.playMode && !appState.lobbyMode) {
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
      handleWebsocket();
    }
  }

  function handleMyPlayer(key, val) {
    setMyPlayerData((prevMyPlayerData) => {
      return { ...prevMyPlayerData, [key]: val };
    });
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
      try {
        websocketClient.current.send(JSON.stringify(data));
      } catch (e) {
        console.error("ERROR: WebSocket couldn't send data: " + e);
      }
    } else {
      console.error("WARN: WebSocket not connected");
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
    let tempGameConfig = gameData.gameConfig;
    for (const [key, value] of new FormData(event.target).entries()) {
      if (key === "visibility") {
        tempGameConfig["public"] = value === "public" ? true : false;
      } else {
        tempGameConfig[key] = value;
      }
    }
    sendWsData({ ...WSHelpers.QUERY.CREATEGAME, gameConfig: tempGameConfig });
    setGameData((prevGameData) => {
      return {
        ...prevGameData,
        gameConfig: tempGameConfig,
      };
    });
    setAppState({ playMode: true, lobbyMode: true });
  }

  // cancel Lobby- & play Mode
  function cancelLobby() {
    setAppState({ playMode: false, lobbyMode: false });
    if (gameData.gameId) {
      sendWsData({
        ...WSHelpers.QUERY.DELETEGAME,
        gameId: gameData.gameConfig.gameId,
      });
    }
    setGameData((prevGameData) => {
      return {
        ...prevGameData,
        gameConfig: { ...prevGameData.gameConfig, gameId: null },
        lobbyState: {
          players: [],
        },
      };
    });
  }

  const hideJoinGameDialog = () => {
    setJoinGameState({
      gameId: null,
      openJoinGameDialog: false,
    });
    // Remove all querystring params from location
    window.history.pushState({}, document.title, "/");
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <header>
        <TronAppBar />
      </header>
      <JoinGameDialog
        open={joinGameState.openJoinGameDialog}
        gameId={joinGameState.gameId}
        handleClose={hideJoinGameDialog}
      />
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
                      players={gameData.lobbyState.players}
                      gameConfig={gameData.gameConfig}
                      myPlayer={myPlayerData}
                      handleMyPlayer={handleMyPlayer}
                    />
                  </Paper>
                </Box>
              </Container>
            </React.Fragment>
          ) : (
            <React.Fragment>
              {wsplayerdata && !websocketState.wsError ? (
                <GameCanvas
                  width={gameData.canvasConfig.width}
                  height={gameData.canvasConfig.height}
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
            </React.Fragment>
          )}
        </section>
      )}
      <Footer />
    </ThemeProvider>
  );
};

export default App;
