import React, { useEffect, useState } from "react";
import { useTheme, createUseStyles } from "react-jss";

const mockdata = {
  name: "Theresa",
  level: 3,
  avatar:
    "https://cdn2.iconfinder.com/data/icons/avatar-colorize-i/100/people_character_avatar_smile_1-16-512.png"
};

const useStyles = createUseStyles(theme => ({
  container: {
    padding: 12,
    margin: 6,
    borderRadius: 6,
    backgroundColor: theme.primary
  },
  firstRow: {
    display: "flex",
    alignItems: "center"
  },
  image: {
    height: 48,
    width: 48,
    borderRadius: "50%",
    border: `2px solid ${theme.between}`,
    backgroundColor: theme.secondary,
    margin: 6
  }
}));

const Profile = () => {
  const [data, setData] = useState({});
  const theme = useTheme();
  useEffect(() => {
    setData(mockdata);
  }, []);
  const { container, firstRow, image } = useStyles(theme);

  return (
    <span className={container}>
      <div className={firstRow}>
        <img className={image} src={data.avatar} alt="User avatar." />
        <span>{data.name}</span>
      </div>
      <div>Level: {data.level}</div>
    </span>
  );
};

export default Profile;
