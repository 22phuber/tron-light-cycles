import React, { useRef, useEffect } from "react";
import "./gameCanvasBR.styles.css";

/*
 * gameCanvasBR for Battle Royale mode
 */

var Position = function (x, y) {
  this.x = x;
  this.y = y;
};

var Player = function (id, color) {
  this.id = id;
  this.positions = [];
  this.color = color;
};

// Player.prototype.deletePositions = function () {
//   this.positions = [];
// };

Player.prototype.addPositions = function (x, y) {
  this.positions.push(new Position(x, y));
};

Player.prototype.getPositions = function () {
  return this.positions;
};

Player.prototype.removeOldestPosition = function () {
  this.positions.shift();
};

const GameCanvasBR = (props) => {
  const canvasRef = useRef(null);

  const canvasId = "gameCanvas";
  const {
    playersData,
    canvasConfig,
    clear,
    myPlayerId,
    walls,
    playersPositions,
  } = props;
  const { width, height, lineThickness } = canvasConfig;

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, width, height);
  }, [clear, width, height]);

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");

    if (playersData) {
      let clientPosX;
      let clientPosY;
      let movementLimit = 100;
      let movementCounter = 0;

      playersData.forEach((player) => {
        if (myPlayerId === player.id) {
          clientPosX = player.posx;
          clientPosY = player.posy;
        }
        for (let client of playersPositions) {
          if (client.id === player.id) {
            client.addPositions(player.posx, player.posy);
          }
        }
      });
      // draw outer canvas
      ctx.clearRect(0, 0, width, height);
      ctx.beginPath();
      ctx.strokeStyle = "black";
      ctx.fillStyle = "black";
      ctx.fillRect(0, 0, width, height);
      // draw inner canvas
      // ctx.stroke();
      ctx.beginPath();
      ctx.strokeStyle = "black";
      ctx.fillStyle = "white";
      ctx.fillRect(
        walls.x - clientPosX + width / 2,
        walls.y - clientPosY + height / 2,
        walls.width,
        walls.height
      );
      // ctx.stroke();
      playersPositions.forEach((player) => {
        player.getPositions().forEach((position) => {
          if (
            clientPosX - position.x <= width &&
            clientPosX - position.x >= 0 - width &&
            clientPosY - position.y <= height &&
            clientPosY - position.y >= 0 - height
          ) {
            ctx.fillStyle = player.color;
            ctx.fillRect(
              position.x - clientPosX + width / 2,
              position.y - clientPosY + height / 2,
              lineThickness,
              lineThickness
            );
          }
        });
      });

      if (movementCounter >= movementLimit) {
        playersPositions.forEach((player) => {
          player.removeOldestPosition();
        });
      } else {
        movementCounter++;
      }
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
