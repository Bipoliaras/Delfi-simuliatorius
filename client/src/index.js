import React from "react";
import ReactDOM from "react-dom";
import Router from "./components/Router";
import {
  createMuiTheme,
  ThemeProvider,
  responsiveFontSizes,
} from "@material-ui/core/styles";
import * as serviceWorker from "./serviceWorker";

let theme = createMuiTheme();
theme = responsiveFontSizes(theme);

ReactDOM.render(
  <ThemeProvider theme={theme}>
    <Router />
  </ThemeProvider>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
