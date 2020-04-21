import React, { useState, useRef, useEffect } from "react";
import "./App.css";
import { ThemeProvider, makeStyles } from "@material-ui/styles";
import {
  CssBaseline,
  Typography,
  createMuiTheme,
  Paper,
  Box,
  Container,
  CircularProgress,
  Button,
} from "@material-ui/core";

import TronAppBar from "./components/appBar/appBar.component";
import GameTable from "./components/table/table.component";
import CreateGame from "./components/createGame/createGame.component";
import Footer from "./components/footer/footer.component";
//import GameAnimation from "./components/gameAnimation/gameAnimation.component";
import GameCanvas from "./components/gameCanvas/gameCanvas.component";
import * as WebsocketHelpers from "./websocket/websocket.helpers";
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

const directionKeys = ["ArrowLeft", "ArrowRight", "ArrowUp", "ArrowDown"];
let wsReconnectTimeout = 250;

/* APP */
const App = () => {
  const classes = useStyles();

  const [playMode, setPlayMode] = useState(false);
  const [lobbyMode, setLobbyMode] = useState(false);
  // websocket
  const ws = useRef(null);
  const [wserror, setWsError] = useState(false);

  // game
  const [wsplayerdata, setWsPlayerData] = useState(null);
  const [gameCanvas, setGameCanvas] = useState({ height: 400, width: 400 });
  let rAF;
  // load games
  const [publicGames, setPublicGames] = useState(null);
  // load players
  const [lobbyPlayers, setLobbyPlayers] = useState(null);

  useEffect(() => {
    handleWebsocket();
    document.addEventListener("keydown", handleKeyPress, false);
    return () => {
      console.log("useEffect ws.close() called");
      if (ws.current && ws.current.readyState === WebSocket.OPEN) ws.current.close();
      console.log("useEffect remove eventistener called");
      document.removeEventListener("keydown", handleKeyPress, false);
    };
  }, []);

  useEffect(() => {
    return () => {
      console.log("cancelAnimationFrame called");
      cancelAnimationFrame(rAF);
    };
  }, [rAF]);

  function handleWebsocket() {
    var connectInterval;
    ws.current = WebsocketHelpers.connectToWSGameServer();
    //ws.current = WebsocketClient.connectToWSNettyGameServer();
    // open
    ws.current.onopen = () => {
      setWsError(false);
      console.log("Websocket connected");
      // clear Interval on onopen of websocket connection
      clearTimeout(connectInterval);
      if (!playMode) {
        loadGames();
      }
    };

    // message
    ws.current.onmessage = (message) => {
      let dataFromServer = null;
      let dataSubject = null;
      // parse
      try {
        dataFromServer = JSON.parse(message.data);
      } catch (e) {
        console.log("JSON parse error: " + e);
      }

      if (dataFromServer && dataFromServer.subject) {
        dataSubject = dataFromServer.subject;
        switch (dataSubject) {
          case "player update":
            rAF = window.requestAnimationFrame(() => {
              handlePlayerData(dataFromServer.players);
            });
            break;
          case "currentPublicGames":
            setPublicGames(dataFromServer.Games);
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
      }
    };

    // error
    ws.current.onerror = (err) => {
      console.error(
        "Websocket couldn't connect ",
        err.message,
        "Closing websocket"
      );
      setWsError(true);
      ws.current.close();
    };

    // close
    ws.current.onclose = (e) => {
      setWsPlayerData(null); // reset data (removes artifacts)
      console.log(
        `Websocket is closed. Reconnect will be attempted in ${Math.min(
          10000 / 1000,
          (wsReconnectTimeout + wsReconnectTimeout) / 1000
        )} second.`,
        e.reason
      );
      wsReconnectTimeout = wsReconnectTimeout + wsReconnectTimeout;
      connectInterval = setTimeout(
        checkWsState,
        Math.min(10000, wsReconnectTimeout)
      ); //call check function after timeout
    };
  }

  // Check if connection ist lost and try to reconnect
  function checkWsState() {
    if (!ws.current || ws.current.readyState === WebSocket.CLOSED) {
      handleWebsocket(ws);
    }
  }

  function handlePlayerData(data) {
    setWsPlayerData(data);
  }

  function sendWsData(data) {
    if (ws.current && ws.current.readyState === WebSocket.OPEN) {
      ws.current.send(JSON.stringify(data));
    } else {
      console.log("Websocket not ready");
    }
  }

  function handleKeyPress(event) {
    const pressedKey = event.key;
    if (directionKeys.includes(pressedKey)) {
      sendWsData({
        ...WebsocketHelpers.query.updateDirection,
        key: pressedKey,
      });
    }
  }

  function loadGames() {
    console.log("loadGames");
    sendWsData(WebsocketHelpers.query.loadGames);
  }

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <header>
        <TronAppBar />
      </header>
      {!playMode ? (
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
                <GameTable publicGames={publicGames} />
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
                  <CreateGame />
                </Paper>
              </Box>
            </Container>
          </section>
        </React.Fragment>
      ) : (
        <section>
          {wsplayerdata && !wserror ? (
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
      {!lobbyMode? (
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
              <LobbyTable lobbyPlayers={lobbyPlayers} />
              
              <Button variant="contained" color="primary" type="submit" >
              Start
            </Button>
            </Box>
          </Container>
        </section>
        </React.Fragment>
      ) :({})}
      <Footer />
    </ThemeProvider>
  );
};

export default App;
