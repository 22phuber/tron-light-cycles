import React from 'react';
//import logo from './logo.svg';
//import './App.css';
import { Grid } from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import Initial from "./components/initial/initial";

/*const styles = {
  paperContainer: {
     //height: 850,
     backgroundRepeat: "no-repeat",
			backgroundPosition: "center center",
			backgroundSize: "cover",
			backgroundAttachment: "fixed",
			height: "100%",
      backgroundImage: `url(${"https://cdn.prod.www.spiegel.de/images/a4960e7b-0001-0004-0000-000000172729_w1528_r1.7794253938832252_fpx29.17_fpy49.96.jpg"})`
  }
};*/
const styles = theme => ({
	"@global": {
		body: {
			backgroundImage: "url('https://cdn.prod.www.spiegel.de/images/a4960e7b-0001-0004-0000-000000172729_w1528_r1.7794253938832252_fpx29.17_fpy49.96.jpg')",
			backgroundRepeat: "no-repeat",
			backgroundPosition: "center center",
			backgroundSize: "cover",
			backgroundAttachment: "fixed",
			height: "100%"
		}}
  });
const App = () => {
  return (
   //<div style={styles.paperContainer}>
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
    //</div>
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

export default withStyles(styles)(App);
