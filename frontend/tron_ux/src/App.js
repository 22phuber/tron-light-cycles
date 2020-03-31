import React from "react";
import "./App.css";

import WSClient from "./components/wsClient/wsClient.component";

function App() {
  return (
    <div>
      <div className="App">
        <header className="App-header">
          <p>
            Edit <code>src/App.js</code> and save to reload.
          </p>
          <a
            className="App-link"
            href="https://reactjs.org"
            target="_blank"
            rel="noopener noreferrer"
          >
            Learn Frodo hallo
          </a>
        </header>
      </div>
      {/* <div>
        <WSClient />
      </div> */}
    </div>
  );
}

export default App;
