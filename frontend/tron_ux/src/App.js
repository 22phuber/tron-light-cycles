import React, { useState, useEffect } from "react";
import "./App.css";
import { ThemeProvider, makeStyles } from "@material-ui/styles";
import {
  CssBaseline,
  Typography,
  createMuiTheme,
  Paper,
  Box,
  Container,
} from "@material-ui/core";

import TronAppBar from "./components/appBar/appBar.component";
import GameTable from "./components/table/table.component";
import CreateGame from "./components/createGame/createGame.component";
import Footer from "./components/footer/footer.component";
import GameAnimation from "./components/gameAnimation/gameAnimation.component";
import * as WebsocketClient from "./websocket/websocket.client";

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
});

/* APP */
const App = () => {
  const classes = useStyles();

  const [inGame, setInGame] = useState(false);
  const [ws, setWs] = useState(WebsocketClient.connectToWSGameServer());
  const [wsdata, setWsData] = useState(null);
  const [wserror, setWsError] = useState(false);

  const [publicGames, setPublicGames] = useState(null);

  let connectInterval;

  useEffect(() => {
    wsOnOpen();
  });

  const wsOnOpen = () => {
    ws.onopen = () => {
      setWs(ws);
      setWsError(false);
      console.log("Websocket connected");
      // clear Interval on onopen of websocket connection
      clearTimeout(connectInterval);
      loadGames();
    };
  };

  // Websocket: when a message has been received
  ws.onmessage = (message) => {
    let dataFromServer = null;
    let dataSubject = null;
    // parse
    try {
      console.log("Try to parse message received");
      dataFromServer = JSON.parse(message.data);
    } catch (e) {
      console.log("JSON parse error: " + e);
    }

    if (dataFromServer && dataFromServer.subject) {
      dataSubject = dataFromServer.subject;
      switch (dataSubject) {
        case "currentPublicGames":
          //console.log(dataSubject + ": data => " + JSON.stringify(dataFromServer.Games));
          setPublicGames(dataFromServer.Games);
          break;
        default:
          console.log("default subject");
          break;
      }
    }
  };

  // Websocket: onerror event listener
  ws.onerror = (err) => {
    console.error(
      "Websocket couldn't connect ",
      err.message,
      "Closing websocket"
    );
    setWsError(true);
    ws.close();
  };

  const sendWsData = (data) => {
    if (ws && ws.readyState === WebSocket.OPEN) {
      ws.send(JSON.stringify(data));
    } else {
      console.log("Websocket not ready");
    }
  };

  const loadGames = () => {
    console.log("loadGames");
    sendWsData(WebsocketClient.query.loadGames);
  };

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <header>
        <TronAppBar />
      </header>
      {!inGame ? (
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
          <GameAnimation />
        </section>
      )}
      <Footer />
    </ThemeProvider>
  );
};

export default App;
