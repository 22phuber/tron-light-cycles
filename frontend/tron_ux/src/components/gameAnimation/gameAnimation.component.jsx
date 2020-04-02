import React, { Component } from "react";
import GameCanvas from "../gameCanvas/gameCanvas.component";

// TODO: refactor that url is a property
const wsURL = "ws://localhost:9000/ws";
const wsReconnectTimeout = 250;
// Allowed direction keys
const directionKeys = ["ArrowLeft", "ArrowRight", "ArrowUp", "ArrowDown"];

class GameAnimation extends Component {
  constructor(props) {
    super(props);
    this.state = { ws: null, wsdata: null };
    this.handleData = this.handleData.bind(this);
  }

  componentDidMount() {
    document.addEventListener("keydown", this.handleKeyPress, false);
    this.handleWebsocket();
  }

  componentWillUnmount() {
    document.removeEventListener("keydown", this.escFunction, false);
    const { ws } = this.state;
    cancelAnimationFrame(this.rAF);
    ws.close();
  }

  handleData = data => {
    this.setState({ wsdata: data });
  };

  handleKeyPress = event => {
    let pressedKey = event.key;
    const data = { subject: "update dir", key: pressedKey };
    if (directionKeys.includes(event.key)) {
      this.sendwsData(data);
    }
  };

  sendwsData = data => {
    const { ws } = this.state;
    if (ws) {
      ws.send(JSON.stringify(data));
    }
  };

  timeout = wsReconnectTimeout;

  handleWebsocket = () => {
    var ws = new WebSocket(wsURL);
    let that = this; // cache the this
    var connectInterval;

    // Websocket: onopen event listener
    ws.onopen = () => {
      console.log("Websocket connected");
      this.setState({ ws: ws });
      that.timeout = 250; // reset timer to 250 on open of websocket connection
      clearTimeout(connectInterval); // clear Interval on on open of websocket connection
    };

    // Websocket: onclose event listener
    ws.onclose = e => {
      console.log(
        `Websocket is closed. Reconnect will be attempted in ${Math.min(
          10000 / 1000,
          (that.timeout + that.timeout) / 1000
        )} second.`,
        e.reason
      );
      that.timeout = that.timeout + that.timeout;
      connectInterval = setTimeout(this.check, Math.min(10000, that.timeout)); //call check function after timeout
    };

    // Websocket: when a message has been received
    ws.onmessage = msg => {
      const data = JSON.parse(msg.data);
      if (data.width && data.height) {
        this.setState({
          gameCanvas: { height: data.height, width: data.width }
        });
      } else {
        this.rAF = window.requestAnimationFrame(() => {
          this.handleData(data);
        });
      }
    };

    // Websocket: onerror event listener
    ws.onerror = err => {
      console.error(
        "Websocket encountered error: ",
        err.message,
        "Closing websocket"
      );
      ws.close();
    };
  };

  // Check if connection ist lost and try to reconnect
  check = () => {
    const { ws } = this.state;
    if (!ws || ws.readyState === WebSocket.CLOSED) this.handleWebsocket();
  };

  render() {
    const isData = this.state.wsdata;
    let canvas;

    if (isData) {
      canvas = (
        <GameCanvas
          width={this.state.gameCanvas.width}
          height={this.state.gameCanvas.height}
          playersData={this.state.wsdata.players}
        />
      );
    } else {
      canvas = <GameCanvas width={400} height={400} playersData={null} />;
    }

    return <div>{canvas}</div>;
  }
}

export default GameAnimation;
