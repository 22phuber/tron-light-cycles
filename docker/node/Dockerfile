FROM node:13-alpine

COPY startup.sh /usr/local/bin/
RUN chmod a+x /usr/local/bin/startup.sh

WORKDIR /app
COPY files/ /app/
RUN npm install

EXPOSE 8080

ENTRYPOINT ["startup.sh"]
CMD ["node", "server.js"]