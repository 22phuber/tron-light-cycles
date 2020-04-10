import React from "react";
import "./App.css";
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
