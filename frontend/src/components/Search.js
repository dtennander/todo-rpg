import React, { useEffect, useState } from "react";
import { useTheme, createUseStyles } from "react-jss";

const useStyles = createUseStyles(theme => ({
  container: {
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: theme.primary,
    width: "100%",
    margin: 6,
    borderRadius: 6
  },
  searchInput: {
    width: "80%",
    fontSize: "1.5rem",
    border: "none",
    padding: 6,
    backgroundColor: theme.white
  }
}));

const Search = () => {
  const theme = useTheme();
  const { container, searchInput } = useStyles(theme);
  return (
    <div className={container}>
      <input className={searchInput} placeholder="Search todo..." />
    </div>
  );
};

export default Search;
