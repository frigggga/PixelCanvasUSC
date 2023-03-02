drop table canvas_info;
drop table users_info;

CREATE TABLE users_info(
	username varchar(60) NOT NULL UNIQUE,
    password varchar(60) NOT NULL,
    email varchar(50) PRIMARY KEY
);

CREATE TABLE canvas_info(
    x_coordinate INT,
    y_coordinate INT,
    color VARCHAR(50) NOT NULL,
    email VARCHAR(60),
    FOREIGN KEY (email) REFERENCES users_info(email),
    PRIMARY KEY (x_coordinate, y_coordinate)
);

SELECT password
FROM users_info 
WHERE email = ?;

INSERT INTO users_info VALUES (?, ?, ?);
INSERT INTO canvas_info VALUES (x, y, color, email);

show tables;

call initialize_canvasinfo();

select * from canvas_info;