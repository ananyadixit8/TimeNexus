CREATE TABLE user_account(
	user_id SERIAL PRIMARY KEY,
	first_name varchar(50) NOT NULL,
	last_name varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	password varchar(50) NOT NULL
);

CREATE TABLE meeting(
	meeting_id SERIAL PRIMARY KEY,
	duration int NOT NULL,
	subject varchar(50) NOT NULL,
	meeting_time TIMESTAMP NOT NULL,
	extra_info varchar(50) NOT NULL
);

CREATE TABLE user_meeting(
	user_id int NOT NULL,
	meeting_id int NOT NULL,
	is_host Boolean NOT NULL,
	PRIMARY KEY(user_id, meeting_id),
	CONSTRAINT fk_user
      FOREIGN KEY(user_id)
        REFERENCES user_account(user_id),
	CONSTRAINT fk_meeting
		FOREIGN KEY(meeting_id)
			REFERENCES meeting(meeting_id)
);