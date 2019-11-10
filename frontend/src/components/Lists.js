import React, { useEffect, useState } from "react";
import { ListsApi } from "todo-rpg-api";
import { useTheme, createUseStyles } from "react-jss";

const useStyles = createUseStyles(theme => ({
  listContainer: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))",
    gridGap: 12,
    margin: 6
  },
  widget: {
    backgroundColor: theme.secondary,
    padding: 12,
    boxSizing: "border-box",
    borderRadius: 6
  }
}));

const ListWidget = ({ id, name, experience }) => {
  const theme = useTheme();
  const { widget } = useStyles(theme);
  return (
    <div className={widget}>
      {name}
      <br />
      {experience.progress}/{experience.max}
    </div>
  );
};

const Lists = ({ toggleDialog }) => {
  const [list, setList] = useState([]);
  const theme = useTheme();
  const { listContainer } = useStyles(theme);

  useEffect(() => {
    const listsApi = new ListsApi();
    listsApi.getLists().then(res => {
      const ids = [1, 2, 3, 4, 5, 6, 7];
      const list = ids.map(id => ({ ...res[0], id }));
      setList(list);
    });
  }, []);
  return (
    <div>
      <div>
        <button onClick={() => toggleDialog()}>Create list</button>
      </div>
      <div className={listContainer}>
        {list.map(todoList => (
          <ListWidget {...todoList} />
        ))}
      </div>
    </div>
  );
};

export default Lists;
