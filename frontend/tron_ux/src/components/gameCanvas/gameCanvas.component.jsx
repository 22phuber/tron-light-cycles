import React, { Component } from "react";
import './gameCanvas.styles.css';

class GameCanvas extends Component {
  constructor(props) {
    super(props);
    this.canvasRef = React.createRef();
    this.lineThickness = 5;
  }

  componentDidUpdate() {
    const { playersData, width, height } = this.props;
    //console.log(playersData + " width:" + width + " height:" + height);
    const canvas = this.canvasRef.current;
    const ctx = canvas.getContext("2d");
    if (playersData) {
      playersData.forEach(player => {
        ctx.fillStyle = player.color;
        ctx.fillRect(
          player.posx % width,
          player.posy % height,
          this.lineThickness,
          this.lineThickness
        );
      });
    }
  }

  render() {
    return (
      <canvas id={"gameCanvas"}
        width={this.props.width}
        height={this.props.height}
        ref={this.canvasRef}
      ></canvas>
    );
  }
}

export default GameCanvas;

