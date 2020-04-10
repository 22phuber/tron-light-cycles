import React, { Component } from "react";
//import logo from './logo.svg';
import './App.css';
import { Grid } from "@material-ui/core";
import { withStyles } from "@material-ui/core/styles";
import Initial from "./components/initial/initial";
import Dialog from "@material-ui/core/Dialog";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import DialogActions from "@material-ui/core/DialogActions";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Login from "./components/login/login";
import Register from "./components/register/register";


const styles = theme => ({
  "@global": {
    body: {
      backgroundImage: "url('https://cdn.prod.www.spiegel.de/images/a4960e7b-0001-0004-0000-000000172729_w1528_r1.7794253938832252_fpx29.17_fpy49.96.jpg')",
      backgroundRepeat: "no-repeat",
      backgroundPosition: "center center",
      backgroundSize: "cover",
      backgroundAttachment: "fixed",
      height: "100%"
    },
    root: {
      background: "black"
    },
    input: {
      color: "white"
    }

  }
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
        </Grid>
      </Grid>
    </Grid>
    //</div>
  );
};

export default withStyles(styles)(App);


