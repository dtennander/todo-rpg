import React from "react";
import { useTheme, createUseStyles } from "react-jss";
import Profile from "../../components/Profile.js";

const useStyles = createUseStyles(theme => ({
  container: {
    display: "grid",
    gridTemplateRows: "auto 1fr auto",
    height: "100vh"
  },
  header: {
    display: "flex",
    justifyContent: "space-between"
  },
  content: {
    backgroundColor: theme.between,
    color: theme.black
  }
}));

const Dashboard = props => {
  const theme = useTheme();
  console.log(theme);
  const { container, header, content } = useStyles(theme);

  return (
    <div className={container}>
      <header className={header}>
        <div>Search</div>
        <Profile />
      </header>
      <div className={content}>Content</div>
      <footer>Footer</footer>
    </div>
  );
};

export default Dashboard;
