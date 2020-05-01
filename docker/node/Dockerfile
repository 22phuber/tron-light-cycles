FROM node:13-alpine

WORKDIR /app
COPY files/ /app/
RUN npm install

EXPOSE 8080

ENTRYPOINT ["node", "server.js"]