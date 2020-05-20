import React, { useRef, useEffect } from "react";
import "./gameCanvasBR.styles.css";

/*
 * gameCanvasBR for Battle Royale mode
 */

var movementCounter = 0;

const GameCanvasBR = (props) => {
  const canvasRef = useRef(null);

  const canvasId = "gameCanvasBR";
  const {
    playersData,
    canvasConfig,
    clear,
    myPlayerId,
    walls,
    playersPositions,
  } = props;
  const { lineThickness } = canvasConfig;
  const width = 400;
  const height = 400;
  const halfWidth = width * 0.5;
  const halfHeight = height * 0.5;
  const movementLimit = 100;
  const clientPosOffset = 50;

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");
    ctx.clearRect(0, 0, width, height);
    movementCounter = 0;
  }, [clear]);

  useEffect(() => {
    const canvas = canvasRef.current;
    const ctx = canvas.getContext("2d");

    if (playersData) {
      let clientPosX;
      let clientPosY;

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
	  
	  
	  // SHIT DON'T GET IT
	  
	  if(clientPosX - walls.x + clientPosOffset < halfWidth && walls.x + walls.width - clientPosX - clientPosOffset > halfWidth){
		  clientPosX = walls.x + halfWidth - clientPosOffset;
	  }else if(clientPosX - walls.x - clientPosOffset > halfWidth && walls.x + walls.width - clientPosX + clientPosOffset < halfWidth){
		  clientPosX = walls.x + walls.width - halfWidth + clientPosOffset;
	  }
		  
	  if(clientPosY - walls.y + clientPosOffset < halfHeight && walls.y + walls.height - clientPosY - clientPosOffset > halfHeight){
		  clientPosY = walls.y + halfHeight - clientPosOffset;
	  }else if(clientPosY - walls.y - clientPosOffset > halfHeight && walls.y + walls.height - clientPosY + clientPosOffset< halfHeight){
		  clientPosY = walls.y + walls.height - halfHeight + clientPosOffset;
	  }
	  
      // var wallChangeX = walls.x - clientPosX + halfWidth;
      // var wallChangeY = walls.y - clientPosY + halfHeight;
      // console.log(wallChangeX);
      // console.log(wallChangeY);
      // // four outer rects
      // // top (works correct)
      // ctx.fillStyle = "black";
      // ctx.fillRect(0, 0, width, wallChangeY);
      // // left (works correct)
      // ctx.fillStyle = "green";
      // ctx.fillRect(0, 0, wallChangeX, height);
      // //right
      // ctx.fillStyle = "blue";
      // ctx.fillRect(width - walls.x, 0, width, height);
      // //bottom
      // ctx.fillStyle = "purple";
      // ctx.fillRect(0, height - walls.y, width, height);
      // END SHIT DON'T GET IT
	  
      // clear canvas
      ctx.clearRect(0, 0, width, height);
      // draw outer canvas
      ctx.fillStyle = "black";
      ctx.fillRect(0, 0, width, height);
      // draw inner canvas
      ctx.fillStyle = "white";
      ctx.fillRect(
        walls.x - clientPosX + halfWidth,
        walls.y - clientPosY + halfHeight,
        walls.width,
        walls.height
      );

      

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
              position.x - clientPosX + halfWidth,
              position.y - clientPosY + halfHeight,
              lineThickness,
              lineThickness
            );
          }
        });
        if (movementCounter >= movementLimit) {
          player.removeOldestPosition();
        } else {
          movementCounter += 1;
        }
      });
    }
  });

  return (
    <canvas id={canvasId} width={"400"} height={"400"} ref={canvasRef}></canvas>
  );
};

export default GameCanvasBR;
