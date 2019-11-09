import React, {useState} from "react";
import {ThemeProvider} from "theming";
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


const MainPage = ({token, handleToken}) => {
  console.log("RENDERING");
  const loggedIn = token !== null;
  if (loggedIn) {
    return <Dashboard token={token}/>
  } else {
    return  <Landing clientId={GOOGLE_CLIENT_ID} storeToken={handleToken} handleError={console.log}/>
  }
};

function App() {
  const [token, setToken] = useState(null);
  return (
    <ThemeProvider theme={theme}>
      <div className="App">
        <MainPage token={token} handleToken={setToken}/>
      </div>
    </ThemeProvider>
  );
}


export default App;
