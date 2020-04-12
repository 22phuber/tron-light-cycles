import React from "react";
import "./App.css";
import { ThemeProvider, makeStyles } from "@material-ui/styles";
import {
  CssBaseline,
  Typography,
  createMuiTheme,
  Paper,
} from "@material-ui/core";
import Container from "@material-ui/core/Container";
import Box from "@material-ui/core/Box";
import TronAppBar from "./components/appBar/appBar.component";
import GameTable from "./components/table/table.component";
import CreateGame from "./components/createGame/createGame.component";


const theme = createMuiTheme({
  palette: {
    type: "dark",
  },
});

const useStyles = makeStyles({
  box: {
    opacity: "0.975",
  },
  typography: {
    textShadow: " 0px 0px 22px rgba(145, 253, 253, 1);",
  },
});

const App = () => {
  const classes = useStyles();

  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <TronAppBar />
      <section>
        <Container maxWidth="lg">
          <Box my={4} className={classes.box}>
            <Typography
              variant="h2"
              component="h2"
              gutterBottom
              className={classes.typography}
            >
              Public games
            </Typography>
            <GameTable />
          </Box>
        </Container>
      </section>
      <section>
        <Container maxWidth="lg">
          <Box my={4} className={classes.box}>
            <Typography
              variant="h2"
              component="h2"
              gutterBottom
              className={classes.typography}
            >
              Create Game
            </Typography>
            <Paper>
              <CreateGame />
            </Paper>
          </Box>
        </Container>
      </section>
    </ThemeProvider>
  );
};

export default App;
