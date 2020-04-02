import React from "react";
import "./App.css";

import GameAnimation from "./components/gameAnimation/gameAnimation.component";

function App() {
  return (
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
      <div>
        <GameAnimation />
      </div>
    </div>
  );
}

export default App;
