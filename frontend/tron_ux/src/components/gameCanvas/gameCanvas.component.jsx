import React, { useRef, useEffect } from "react";
import "./gameCanvas.styles.css";

const GameCanvas = props => {
  const canvasRef = useRef(null);
  const lineThickness = 5;
  const canvasId = "gameCanvas";

  useEffect(() => {
    const { playersData, width, height } = props;
    //console.log(playersData + " width:" + width + " height:" + height);
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    if (playersData) {
      playersData.forEach(player => {
        ctx.fillStyle = player.color;
        ctx.fillRect(
          player.posx % width,
          player.posy % height,
          lineThickness,
          lineThickness
        );
      });
    }
  });

  return (
    <canvas
      id={canvasId}
      width={props.width}
      height={props.height}
      ref={canvasRef}
    ></canvas>
  );
};

export default GameCanvas;
