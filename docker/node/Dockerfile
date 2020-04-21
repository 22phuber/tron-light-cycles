FROM node:13-alpine

WORKDIR /apps
RUN npm install ws mysql node-static
ADD files/websocket.server.js /apps/

EXPOSE 9091

ENTRYPOINT ["node", "websocket.server.js"]