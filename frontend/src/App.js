import React from "react";
import { Router } from "@reach/router";
import { ThemeProvider } from "theming";
import Dashboard from "./pages/Dashboard.js";
import "./App.css";

let Landing = () => <div>Login</div>;

const theme = {
  primary: "#D33F49",
  secondary: "#8B8BAE",
  between: "#D7C0D0",
  black: "#262730",
  white: "#EFF0D1"
};

function App() {
  return (
    <ThemeProvider theme={theme}>
      <div className="App">
        <Router>
          <Landing path="/" />
          <Dashboard path="/dashboard" />
        </Router>
      </div>
    </ThemeProvider>
  );
}

export default App;
