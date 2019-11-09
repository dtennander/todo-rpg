import React, { useState, useEffect } from "react";
import { useTheme, createUseStyles } from "react-jss";

const useStyles = createUseStyles(theme => ({
  container: {
    backgroundColor: theme.secondary,
    color: theme.white,
    border: `4px solid ${theme.between}`,
    borderRadius: 12,
    padding: "12px 48px"
  },
  todo: {
    textAlign: "left",
    margin: "4px 0 4px 12px"
  },
  item: {
    backgroundColor: theme.between,
    color: theme.black,
    padding: 3,
    borderRadius: 3
  }
}));

const mockdata = {
  title: "Make todo-app",
  todos: [
    {
      title: "Frontend",
      todos: [
        {
          title: "Landing page",
          todos: [
            {
              title: "Dashboard",
              todos: [
                {
                  title: "Animations",
                  todos: []
                },
                {
                  title: "Routing",
                  todos: []
                }
              ]
            }
          ]
        }
      ]
    },
    {
      title: "Backend",
      todos: [
        {
          title: "Remove CORS",
          todos: []
        },
        {
          title: "DB-stuff",
          todos: [
            {
              title: "Users",
              todos: []
            },
            {
              title: "Todos",
              todos: []
            }
          ]
        }
      ]
    }
  ]
};

const ListItem = ({ title, todos }) => {
  const theme = useTheme();
  const { todo, item } = useStyles(theme);
  return (
    <div className={todo}>
      <div className={item}>{title}</div>
      {todos.map(todo => {
        return <ListItem {...todo} />;
      })}
    </div>
  );
};

const List = () => {
  const [data, setData] = useState(null);
  const theme = useTheme();
  const { container } = useStyles(theme);
  useEffect(() => {
    setData(mockdata);
  }, []);
  if (data === null) return <></>;
  return (
    <div className={container}>
      <h1>{data.title}</h1>
      {data.todos.map(todo => {
        return <ListItem {...todo} />;
      })}
    </div>
  );
};

export default List;
