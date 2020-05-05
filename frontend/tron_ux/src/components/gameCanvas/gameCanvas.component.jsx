import React, { useRef, useEffect, useState } from "react";
import "./gameCanvas.styles.css";

const GameCanvas = (props) => {
  const canvasRef = useRef(null);

  const canvasId = "gameCanvas";
  const { playersData, canvasConfig, clear } = props;
  const { width, height, lineThickness } = canvasConfig;

  //var ctx = null;

  useEffect(() => {

    console.log("useEffect[] called");
  }, []);

  // useEffect(() => {
  //   console.log("useEffect[clear] called");
  //   clearCanvas();
  // }, [clear]);

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    console.log("useEffect called");
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

  // function clearCanvas() {
  //   ctx.clearRect(0, 0, width, height);
  // }

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
