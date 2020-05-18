import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import { SnackbarProvider } from "notistack";
import { createMuiTheme, CssBaseline } from "@material-ui/core";
import { ThemeProvider } from "@material-ui/styles";

const theme = createMuiTheme({
  palette: {
    type: "dark",
  },
});

ReactDOM.render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <SnackbarProvider
        maxSnack={3}
        iconVariant={{
          success: "🏆",
          error: "☠️",
          warning: "⚰️",
          info: "🎮",
          default: "⚔️",
        }}
      >
        <App />
      </SnackbarProvider>
    </ThemeProvider>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
