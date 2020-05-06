import React, { useRef, useEffect } from "react";
import "./gameCanvas.styles.css";

const GameCanvas = (props) => {
  const canvasRef = useRef(null);

  const canvasId = "gameCanvas";
  const { playersData, canvasConfig, clear } = props;
  const { width, height, lineThickness } = canvasConfig;

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, width, height);
  }, [clear]);

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    if (playersData) {
      playersData.forEach((player) => {
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
      width={width}
      height={height}
      ref={canvasRef}
    ></canvas>
  );
};

export default GameCanvas;
