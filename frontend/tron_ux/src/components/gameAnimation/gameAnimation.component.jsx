import React, { useState, useEffect } from "react";
import GameCanvas from "../gameCanvas/gameCanvas.component";
import { makeStyles } from "@material-ui/core/styles";
import CircularProgress from "@material-ui/core/CircularProgress";

// TODO: refactor that url is a property
const wsURL = "ws://localhost:9000/ws";
let wsReconnectTimeout = 250;
// Allowed direction keys
const directionKeys = ["ArrowLeft", "ArrowRight", "ArrowUp", "ArrowDown"];

const useStyles = makeStyles(theme => ({
  circularProgress: {
    margin: "25px"
  }
}));

const GameAnimation = props => {
  const [ws, setWs] = useState(null);
  const [wsdata, setWsData] = useState(null);
  const [wserror, setWsError] = useState(false);
  const [gameCanvas, setGameCanvas] = useState(null);
  let rAF;

  useEffect(() => {
    return () => {
      cancelAnimationFrame(rAF);
      if(ws && ws.readyState === WebSocket.OPEN) ws.close();
    };
  }, [ws, rAF]);

  useEffect(handleWebsocket, []);

  useEffect(() => {
    document.addEventListener("keydown", handleKeyPress, false);
    return () => {
      document.removeEventListener("keydown", handleKeyPress, false);
    };
  });

  function handleData(data) {
    setWsData(data);
  }

  function handleKeyPress(event) {
    const pressedKey = event.key;
    const data = { subject: "update dir", key: pressedKey };
    if (directionKeys.includes(event.key)) {
      sendWsData(data);
    }
  }

  function sendWsData(data) {
    if(ws && ws.readyState === WebSocket.OPEN) ws.send(JSON.stringify(data));
  }

  function handleWebsocket() {
    var websocket = new WebSocket(wsURL);
    var connectInterval;

    // Websocket: onopen event listener
    websocket.onopen = () => {
      setWs(websocket);
      setWsError(false);
      console.log("Websocket connected");
      clearTimeout(connectInterval); // clear Interval on on open of websocket connection
    };

    // Websocket: onclose event listener
    websocket.onclose = e => {
      setWsData(null); // reset data (removes artifacts)
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

    // Websocket: when a message has been received
    websocket.onmessage = msg => {
      const data = JSON.parse(msg.data);
      if (data.width && data.height) {
        setGameCanvas({ height: data.height, width: data.width });
      } else {
        rAF = window.requestAnimationFrame(() => {
          handleData(data);
        });
      }
    };

    // Websocket: onerror event listener
    websocket.onerror = err => {
      console.error(
        "Websocket couldn't connect ",
        err.message,
        "Closing websocket"
      );
      setWsError(true);
      websocket.close();
    };
  }

  // Check if connection ist lost and try to reconnect
  function checkWsState() {
    if (!ws || ws.readyState === WebSocket.CLOSED) {
      handleWebsocket();
    }
  }

  let renderElement;
  const classes = useStyles();

  if (wsdata && gameCanvas && !wserror) {
    renderElement = (
      <GameCanvas
        width={gameCanvas.width}
        height={gameCanvas.height}
        playersData={wsdata.players}
      />
    );
  } else if (wserror) {
    renderElement = (
      <div>
        Game server seems to be offline or is not reachable.
        <br />
        Trying to reconnect...
        <br />
        <CircularProgress className={classes.circularProgress} />
      </div>
    );
  } else {
    renderElement = (
      <div>
        Connecting to Game server...
        <br />
        <CircularProgress className={classes.circularProgress} />
      </div>
    );
  }

  return <div>{renderElement}</div>;
};

export default GameAnimation;
