import React, { useState, useEffect } from "react";
import { useTheme, createUseStyles } from "react-jss";
import Sound from "react-sound";
import pop from "../assets/sounds/cork_pop.wav";

const useStyles = createUseStyles(theme => ({
  container: {
    backgroundColor: theme.secondary,
    color: theme.white,
    border: `4px solid ${theme.between}`,
    borderRadius: 12,
    padding: "12px 48px",
    maxHeight: "90vh",
    display: "grid",
    gridTemplateRows: "auto 1fr auto",
    gridGap: 12
  },
  listContainer: {
    overflow: "scroll"
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
  },
  popping: {
    transition: "3s background-color",
    backgroundColor: theme.primary
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
    },
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
  const [status, setStatus] = useState("passive");
  const [event, setEvent] = useState(null);
  const theme = useTheme();
  const { todo, item, popping } = useStyles(theme);
  return (
    <div
      className={todo}
      onMouseDown={() => {
        setStatus("hold");
        const e = setTimeout(() => setStatus("done"), 2000);
        setEvent(e);
      }}
      onMouseUp={() => {
        clearTimeout(event);
        setEvent(null);
        setStatus("passive");
      }}
      onMouseOut={() => {
        clearTimeout(event);
        setEvent(null);
        setStatus("passive");
      }}
    >
      {status === "done" && (
        <Sound
          url={pop}
          playStatus={Sound.status.PLAYING}
          onFinishedPlaying={() => setStatus(false)}
        />
      )}
      <div className={`${item} ${status === "hold" && popping}`}>{title}</div>
      {todos.map(todo => {
        return <ListItem {...todo} />;
      })}
    </div>
  );
};

const List = () => {
  const [data, setData] = useState(null);
  const theme = useTheme();
  const { container, listContainer } = useStyles(theme);
  useEffect(() => {
    setData(mockdata);
  }, []);
  if (data === null) return <></>;
  return (
    <div className={container}>
      <h1>{data.title}</h1>
      <div className={listContainer}>
        {data.todos.map(todo => {
          return <ListItem {...todo} />;
        })}
      </div>
      <button>Save</button>
    </div>
  );
};

export default List;
