const WebSocket = require("ws");
var mysql = require("mysql");

var dbconnection = mysql.createConnection({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
});

// create websocket server
const wss = new WebSocket.Server({ port: 9091 });
console.log("Server started");

wss.on("connection", function connection(ws, req) {
  // id
  const id = req.headers["sec-websocket-key"];
  console.log("Client connected: " + id);

  // receive a message
  ws.on("message", function incoming(message) {
    console.log("received: %s", message);

    if (message == "db") {
      try {
        ws.send(testDB());
      } catch (error) {
        console.log("Error: " + error);
      }
    }

    // echo message back to client
    ws.send("Server received from client: " + message);
  });

  ws.on("close", function close() {
    console.log("Client disconnected" + id);
  });
});

function testDB() {
  dbconnection.connect(function (err) {
    if (err) {
      console.error("error connecting: " + err.stack);
      return;
    }
    console.log("connected as id " + dbconnection.threadId);
  });
  dbconnection.query("SHOW DATABASES", function (error, results, fields) {
    if (error) throw error;
    results.forEach(function(result) {
        console.log(result);
      });
    return results;
  });
  dbconnection.end(function (err) {
    console.log("disconnected client with id " + dbconnection.threadId);
  });
}
