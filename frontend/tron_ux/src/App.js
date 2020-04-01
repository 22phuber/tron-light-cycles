import React from 'react';
//import logo from './logo.svg';
//import './App.css';
import { Grid } from "@material-ui/core";
import Initial from "./components/initial/initial";

const App = () => {
  return (
    <Grid container direction="column">
      <Grid item>
        <Initial/>
      </Grid>
      <Grid item container>
        <Grid item> 
          content
        </Grid>
      </Grid>
    </Grid>
  );
};

 /*function App() {
  return (
    
    <div className="App">
      <initial />
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
  );
}*/

export default App;
