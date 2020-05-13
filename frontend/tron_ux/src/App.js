import React, { useState, useRef, useEffect } from "react";
import "./App.css";
import { WebsocketSubjectMissing } from "./helpers/exceptions";
import * as WSHelpers from "./helpers/websocket";
import {
  DIRECTIONKEYS,
  getMyPlayerData,
  getLobbyState,
  getGameId,
  getCanvasConfig,
  getPublicGames,
  getGameConfig,
} from "./helpers/helpers";
import { useInterval } from "./helpers/custom.hooks";
import { makeStyles } from "@material-ui/styles";
import {
  Typography,
  Paper,
  Box,
  Container,
  Button,
  Card,
  CardContent,
} from "@material-ui/core";
import TronAppBar from "./components/appBar/appBar.component";
import GameTable from "./components/publicGames/publicGames.component";
import CreateGame from "./components/createGame/createGame.component";
import Footer from "./components/footer/footer.component";
import GameCanvas from "./components/gameCanvas/gameCanvas.component";
import LobbyTable from "./components/lobby/lobby.component";
import JoinGameDialog from "./components/joinGameDialog/joinGameDialog.component";
import { useSnackbar } from "notistack";

const useStyles = makeStyles((theme) => ({
  box: {
    opacity: "0.975",
  },
  boxPlay: {
    opacity: "0.975",
  },
  paperPlay: {
    display: "flex",
    flexDirection: "column",
    justifyContent: "center",
    padding: theme.spacing(3),
  },
  playInfos: {
    display: "flex",
    justifyContent: "space-between",
    padding: theme.spacing(1),
    "& > *": {
      flexGrow: "1",
      marginBottom: theme.spacing(1),
      marginLeft: theme.spacing(2),
      marginRight: theme.spacing(2),
    },
    "& > *:first-child": {
      marginLeft: "0",
    },
    "& > *:last-child": {
      marginRight: "0",
    },
  },
  gameLog: {
    backgroundColor: "#636363",
    fontColor: "white",
    width: "60%",
  },
  typography: {
    textShadow: " 0px 0px 22px rgba(145, 253, 253, 1);",
  },
  circularProgress: {
    margin: "25px",
  },
  card: {
    backgroundColor: "#636363",
    textAlign: "center",
    minWidth: "35%",
  },
  cardTitle: {
    fontSize: 14,
  },
}));

/* APP */
const App = () => {
  const classes = useStyles();
  // Snackbar
  const { enqueueSnackbar } = useSnackbar();
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
  const [myPlayerData, setMyPlayerData] = useState(getMyPlayerData());
  var myPlayerId = "";
  const [gameConfig, setGameConfig] = useState(getGameConfig());
  const [gameId, setGameId] = useState(getGameId());
  const [lobbyState, setLobbyState] = useState(getLobbyState());
  const [canvasConfig, setCanvasConfig] = useState(getCanvasConfig());
  const [publicGames, setPublicGames] = useState(getPublicGames());
  const [joinGameState, setJoinGameState] = useState({
    gameId: null,
    gameName: null,
    openJoinGameDialog: false,
  });
  var [playData, setPlayData] = useState(null);
  const [clearCanvas, setClearCanvas] = useState({ clear: false });
  var roundInfoObject = {};
  const [roundState, setRoundState] = useState({});
  const [countdownState, setCountdownState] = useState(null);
  // const [scoreState, setScoreState] = useState({ score: 0 });

  // Request Animation Frame variable
  let rAF;

  useEffect(() => {
    localStorage.setItem("myPlayerData", JSON.stringify(myPlayerData));
  }, [myPlayerData]);

  useEffect(() => {
    localStorage.setItem("lobbyState", JSON.stringify(lobbyState));
  }, [lobbyState]);

  useEffect(() => {
    localStorage.setItem("gameId", JSON.stringify(gameId));
  }, [gameId]);

  useEffect(() => {
    localStorage.setItem("canvasConfig", JSON.stringify(canvasConfig));
  }, [canvasConfig]);

  useEffect(() => {
    localStorage.setItem("publicGames", JSON.stringify(publicGames));
  }, [publicGames]);

  useEffect(() => {
    localStorage.setItem("gameConfig", JSON.stringify(gameConfig));
  }, [gameConfig]);

  useEffect(() => {
    handleWebsocket();
    return () => {
      if (
        websocketClient.current &&
        websocketClient.current.readyState === WebSocket.OPEN
      )
        websocketClient.current.close();
    };
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    if (queryString.has("id")) {
      setJoinGameState({
        gameId: queryString.get("id"),
        gameName: queryString.has("name") ? queryString.get("name") : null,
        openJoinGameDialog: true,
      });
    }
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    return () => {
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
    // CONNECT
    //websocketClient.current = WSHelpers.connectToWSGameServer();
    websocketClient.current = WSHelpers.connectToWSNettyGameServer();
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
            setCountdownState(0);
            rAF = window.requestAnimationFrame(() => {
              setPlayData(dataFromServer.players);
            });
            break;
          case "lobbyState":
            console.log("WS[lobbyState]: " + JSON.stringify(dataFromServer));
            setLobbyState({
              players: dataFromServer.players,
              host: dataFromServer.host,
            });
            setGameConfig((prevGameConfig) => {
              return { ...prevGameConfig, ...dataFromServer.gameConfig };
            });
            break;
          case "currentPublicGames":
            // console.log(
            //   "WS[currentPublicGames]: " + JSON.stringify(dataFromServer)
            // );
            setPublicGames(dataFromServer.games);
            break;
          case "initialGameState":
            console.log(
              "WS[initialGameState]: " + JSON.stringify(dataFromServer)
            );
            setClearCanvas((prevClearCanvas) => {
              return {
                clear: !prevClearCanvas,
              };
            });
            setAppState({ playMode: true, lobbyMode: false });
            setPlayData(dataFromServer.players);
            break;
          case "countdown":
            console.log("WS[countdown]: " + JSON.stringify(dataFromServer));
            roundInfoObject = dataFromServer.round;
            setRoundState(roundInfoObject);
            setCountdownState(dataFromServer.count);
            // enqueueSnackbar(
            //   " Round: " +
            //     dataFromServer.round.current +
            //     " - Countdown: " +
            //     dataFromServer.count,
            //   {
            //     variant: "info",
            //     disableWindowBlurListener: true,
            //   }
            // );
            break;
          case "playerDeath":
            console.log("WS[playerDeath]: " + JSON.stringify(dataFromServer));
            handlePlayerDeath(dataFromServer, myPlayerId);
            break;
          case "roundScores":
            console.log("WS[roundScores]: " + JSON.stringify(dataFromServer));
            handleRoundScores(dataFromServer, myPlayerId);
            if (roundInfoObject.current === roundInfoObject.total) {
              setTimeout(function () {
                setAppState({ playMode: true, lobbyMode: true });
              }, 3000);
            }
            break;
          case "canvasConfig":
            // console.log("WS[canvasConfig]: " + JSON.stringify(dataFromServer));
            const { width, height, lineThickness } = dataFromServer;
            setCanvasConfig({
              height: height,
              width: width,
              lineThickness: lineThickness,
            });
            break;
          case "createGame":
            // console.log("WS[createGame]: " + JSON.stringify(dataFromServer));
            setGameId(dataFromServer.gameId);
            setAppState({ playMode: true, lobbyMode: true });
            break;
          case "clientId":
            // console.log("WS[clientId]: " + JSON.stringify(dataFromServer));
            myPlayerId = dataFromServer.id;
            setMyPlayerData((prevMyPlayerData) => {
              return { ...prevMyPlayerData, clientId: dataFromServer.id };
            });
            console.log("WS[clientId] id: " + dataFromServer.id);
            break;
          default:
            console.error("WARN: Unknown subject");
            console.log(
              "WS[Unknown subject]: " + JSON.stringify(dataFromServer)
            );
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
      setPlayData([]); // reset data (removes artifacts)
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
    // TODO: Remove?
    //if (!myPlayerData.clientId) sendWsData(WSHelpers.QUERY.CLIENTCONNECTED);
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

  function handleMyPlayerData(data) {
    console.log(data);
    for (const [key, value] of Object.entries(data)) {
      switch (key) {
        case "cycle_color":
          handleMyPlayer("color", value);
          break;
        default:
          handleMyPlayer(key, value);
          break;
      }
    }
  }

  function handleMyPlayer(key, val) {
    if (key in myPlayerData) {
      setMyPlayerData((prevMyPlayerData) => {
        return { ...prevMyPlayerData, [key]: val };
      });
    }
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
    const { key, keyCode } = event;
    // Prevent default action for arrow keys => scroll
    switch (keyCode) {
      case 37:
      case 39:
      case 38:
      case 40:
        event.preventDefault();
        break;
      default:
        break; // do not block other keys
    }
    if (DIRECTIONKEYS.includes(key)) {
      sendWsData({
        ...WSHelpers.QUERY.UPDATEDIRECTION,
        key: key,
        gameId: gameId,
      });
    }
  }

  // handle create new game/lobby data
  function handleCreateGame(event) {
    event.preventDefault();
    let tempGameConfig = {};
    for (const [key, value] of new FormData(event.target).entries()) {
      if (key === "visibility") {
        tempGameConfig["public"] = value === "public" ? true : false;
      } else {
        tempGameConfig[key] = value;
      }
    }
    sendWsData({
      ...WSHelpers.QUERY.CREATEGAME,
      gameConfig: tempGameConfig,
      host: {
        playerName: myPlayerData.username,
        color: myPlayerData.color,
      },
    });
    setGameConfig(tempGameConfig);
    setAppState({ playMode: true, lobbyMode: true });
  }

  // handle join game
  function handleJoinGame(joinData) {
    const gameId = joinData.gameId || joinGameState.gameId;
    const gameName = joinData.name || joinGameState.name;
    sendWsData({
      ...WSHelpers.QUERY.JOINGAME,
      gameId: gameId,
      playerName: myPlayerData.username,
      playerColor: myPlayerData.color,
    });
    setGameId(gameId);
    // find game id in public games to receive infos?
    setGameConfig({
      mode: null,
      name: gameName,
      playersAllowed: null,
      public: null,
    });
    hideJoinGameDialog();
    setAppState({ playMode: true, lobbyMode: true });
  }

  // cancel Lobby- & play Mode
  function cancelLobby() {
    setAppState({ playMode: false, lobbyMode: false });
    if (gameId && myPlayerData.clientId === lobbyState.host.clientId) {
      sendWsData({
        ...WSHelpers.QUERY.DELETEGAME,
        gameId: gameId,
      });
      console.log(
        "WS Client: " +
          JSON.stringify({
            ...WSHelpers.QUERY.DELETEGAME,
            gameId: gameId,
          })
      );
    }
    setGameId(null);
  }

  // hide join game and clear url from params
  const hideJoinGameDialog = () => {
    setJoinGameState({
      gameId: null,
      gameName: null,
      openJoinGameDialog: false,
    });
    // Remove all querystring params from location
    window.history.pushState({}, document.title, "/");
  };

  //
  function handleStartGame() {
    sendWsData({
      ...WSHelpers.QUERY.STARTGAME,
      gameId: gameId,
    });
    console.log("[WS]: Start game sent");
  }

  // player Death
  function handlePlayerDeath(data, myPlayerId) {
    const { playerId, posx, posy, playerName } = data;
    var message = "",
      variant = "info";
    if (playerId === myPlayerId) {
      message = " Oh No, YOU died! { x:" + posx + ", y:" + posy + " }";
      variant = "error";
    } else {
      message =
        " Player [" +
        (playerName || playerId) +
        "] died! { x:" +
        posx +
        ", y:" +
        posy +
        " }";
      variant = "warning";
    }
    enqueueSnackbar(message, {
      variant: variant,
      disableWindowBlurListener: true,
    });
    // setInGameMessages((prevInGameMessages) => [message, ...prevInGameMessages]);
  }

  // round scores
  function handleRoundScores(data, myPlayerId) {
    const { playerScores } = data;
    var message = " Hm, YOU didn't win this time ... but maybe next time!",
      variant = "default";
    playerScores.forEach((player) => {
      if (player.clientId === myPlayerId) {
        if (player.score >= playerScores.length) {
          message = " Yes, YOU won this round!";
          variant = "success";
        }
      }
    });
    enqueueSnackbar(message, {
      variant: variant,
      disableWindowBlurListener: true,
    });
  }

  return (
    <React.Fragment>
      <header>
        <TronAppBar
          handleMyPlayerData={handleMyPlayerData}
          myPlayer={myPlayerData}
        />
      </header>
      <JoinGameDialog
        open={joinGameState.openJoinGameDialog}
        joinGameState={joinGameState}
        handleJoinGame={handleJoinGame}
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
                <GameTable
                  handleJoinGame={handleJoinGame}
                  publicGames={publicGames}
                />
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
                      players={lobbyState.players}
                      host={lobbyState.host}
                      gameConfig={gameConfig}
                      gameId={gameId}
                      myPlayer={myPlayerData}
                      handleMyPlayer={handleMyPlayer}
                      handleStartGame={handleStartGame}
                    />
                  </Paper>
                </Box>
              </Container>
            </React.Fragment>
          ) : (
            <React.Fragment>
              <Container maxWidth="lg">
                <Box my={4} className={classes.boxPlay}>
                  <Typography
                    variant="h2"
                    component="h2"
                    gutterBottom
                    className={classes.typography}
                  >
                    Game: {gameConfig.name || "GAMENAME"}
                  </Typography>
                  <Paper className={classes.paperPlay}>
                    <div className={classes.playInfos}>
                      <Card className={classes.card}>
                        <CardContent>
                          <Typography
                            className={classes.cardTitle}
                            color="textSecondary"
                            gutterBottom
                          >
                            COUNTDOWN
                          </Typography>
                          <Typography variant="h5" component="h2">
                            {countdownState && countdownState > 0
                              ? countdownState
                              : countdownState === 0
                              ? "GO!"
                              : "-"}
                          </Typography>
                        </CardContent>
                      </Card>
                      <Button
                        variant="outlined"
                        color="secondary"
                        onClick={() =>
                          setAppState({
                            playMode: true,
                            lobbyMode: true,
                          })
                        }
                      >
                        Give up <br />( Back to Lobby )
                      </Button>
                      <Card className={classes.card}>
                        <CardContent>
                          <Typography
                            className={classes.cardTitle}
                            color="textSecondary"
                            gutterBottom
                          >
                            ROUND
                          </Typography>
                          <Typography variant="h5" component="h2">
                            {roundState
                              ? roundState.current + "/" + roundState.total
                              : "-"}
                          </Typography>
                        </CardContent>
                      </Card>
                    </div>
                    <GameCanvas
                      canvasConfig={canvasConfig}
                      playersData={playData}
                      clear={clearCanvas}
                    />
                  </Paper>
                </Box>
              </Container>
            </React.Fragment>
          )}
        </section>
      )}
      <Footer />
    </React.Fragment>
  );
};

export default App;
