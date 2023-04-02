PRAGMA foreign_keys = 1;

CREATE TABLE IF NOT EXISTS users (
     id INTEGER PRIMARY KEY,
     username TEXT UNIQUE NOT NULL,
     password TEXT NOT NULL,
     access_level TEXT DEFAULT 'basic' NOT NULL,
     CHECK (access_level IN ('full', 'basic'))
);

INSERT INTO users (username, password, access_level) VALUES ('master', 'master', 'full');
INSERT INTO users (username, password, access_level) VALUES ('employee1', 'employee1', 'basic');