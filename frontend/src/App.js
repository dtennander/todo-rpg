import React, {useState} from "react";
import { Router, navigate } from "@reach/router";
import { ThemeProvider } from "theming";
import Dashboard from "./pages/Dashboard.js";
import "./App.css";
import Landing from "./pages/Landing";

const theme = {
  primary: "#D33F49",
  secondary: "#8B8BAE",
  between: "#D7C0D0",
  black: "#262730",
  white: "#EFF0D1"
};

const GOOGLE_CLIENT_ID = "848941796451-a83bla4b2vk7oqvrbti9tat7urvqd41r.apps.googleusercontent.com";


function App() {
  const [token, setToken] = useState(null);

  const handleToken = token => {
    setToken(token);
    navigate("/dashboard")
  };

  return (
    <ThemeProvider theme={theme}>
      <div className="App">
        <Router>
          <Landing path="/" clientId={GOOGLE_CLIENT_ID} storeToken={handleToken} handleError={console.log}/>
          <Dashboard path="/dashboard" token={token}/>
        </Router>
      </div>
    </ThemeProvider>
  );
}



export default App;
