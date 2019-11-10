DROP TABLE todo;

CREATE TABLE todo_list (
  id SERIAL PRIMARY KEY,
  name TEXT
);

CREATE TABLE todo (
  id SERIAL PRIMARY KEY,
  description TEXT,
  ordering INTEGER,
  done BOOLEAN,
  list_id SERIAL,
  FOREIGN KEY (list_id) REFERENCES todo_list (id) ON DELETE CASCADE
);
