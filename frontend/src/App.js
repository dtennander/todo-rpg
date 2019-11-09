import React from "react";
import { Router } from "@reach/router";
import "./App.css";

let Landing = () => <div>Login</div>;
let Dashboard = () => <div>Dashboard</div>;

function App() {
  return (
    <div className="App">
      <Router>
        <Landing path="/" />
        <Dashboard path="/dashboard" />
      </Router>
    </div>
  );
}

export default App;
