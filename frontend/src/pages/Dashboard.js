import React, { useState } from "react";
import { useTheme, createUseStyles } from "react-jss";
import Profile from "../components/Profile.js";
import Search from "../components/Search.js";
import Lists from "../components/Lists.js";
import List from "../components/List.js";

const useStyles = createUseStyles(theme => ({
  container: {
    display: "grid",
    gridTemplateRows: "auto 1fr auto",
    height: "100vh",
    backgroundColor: theme.between,
    color: theme.black
  },
  header: {
    display: "flex",
    justifyContent: "space-between"
  },
  dialog: {
    position: "fixed",
    top: "50%",
    bottom: "50%",
    right: 0,
    left: 0,
    transition: "top 1s, bottom 1s",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    overflow: "hidden"
  },
  overlay: {
    position: "absolute",
    zIndex: -1,
    height: "100%",
    width: "100%",
    backgroundColor: "rgba(0,0,0,0.5)",
    cursor: "pointer"
  },
  open: {
    top: 0,
    bottom: 0
  }
}));

const Dashboard = props => {
  const [listOpen, setListOpen] = useState(false);
  const theme = useTheme();
  const { container, header, dialog, open, overlay } = useStyles(theme);

  return (
    <div className={container}>
      <header className={header}>
        <Search />
        <Profile />
      </header>
      <Lists toggleDialog={() => setListOpen(true)} />
      <footer>Footer</footer>
      <div className={`${dialog} ${listOpen && open}`}>
        <div className={overlay} onClick={() => setListOpen(false)} />
        <List />
      </div>
    </div>
  );
};

export default Dashboard;
