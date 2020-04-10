import React, { Component } from "react";
//import logo from './logo.svg';
import "./App.css";
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

import { ThemeProvider } from "@material-ui/styles";
import { CssBaseline, Typography, createMuiTheme } from "@material-ui/core";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import TronAppBar from "./components/appBar/appBar.component";
import GameTable from "./components/table/table.component";

const theme = createMuiTheme({
  palette: {
    type: "dark",
  },
});

const App = () => {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <TronAppBar />
      <Container maxWidth="lg">
        <Box my={4}>
          <Typography variant="h2" component="h1" gutterBottom>
            Public games
          </Typography>
          <GameTable />
        </Box>
      </Container>
    </ThemeProvider>
  );
};

export default App;
