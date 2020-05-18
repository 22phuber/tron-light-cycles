import React, { useRef, useEffect } from "react";
import "./gameCanvasBR.styles.css";

/*
 * gameCanvasBR for Battle Royale mode
 */

const GameCanvasBR = (props) => {
  const canvasRef = useRef(null);

  const canvasId = "gameCanvas";
  const { playersData, canvasConfig, clear, myPlayerId, walls } = props;
  const { width, height, lineThickness } = canvasConfig;

  let clientPosX;
  let clientPosY;

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, width, height);
  }, [clear, width, height]);

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    if (playersData) {
      playersData.forEach((player) => {
        //
        if (myPlayerId === player.id) {
          clientPosX = player.posx;
          clientPosY = player.posy;
        }

        ctx.clearRect(0, 0, width, height);
        ctx.beginPath();
        ctx.strokeStyle = "black";
        ctx.fillStyle = "black";
        ctx.fillRect(0, 0, width, height);
        ctx.stroke();
        ctx.beginPath();
        ctx.strokeStyle = "black";
        ctx.fillStyle = "white";
        ctx.fillRect(walls.x - clientPosX + (canvas.width / 2), walls.y - clientPosY + (canvas.height / 2), walls.width, walls.height);
        ctx.stroke();

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

export default GameCanvasBR;
