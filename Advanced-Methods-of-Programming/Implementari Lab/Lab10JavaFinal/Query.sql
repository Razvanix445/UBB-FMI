CREATE EXTENSION pgcrypto;

CREATE TABLE Users (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	first_name CHARACTER VARYING NOT NULL,
	last_name CHARACTER VARYING NOT NULL,
	username CHARACTER VARYING NOT NULL,
	password CHARACTER VARYING NOT NULL,
	email CHARACTER VARYING
);

CREATE TABLE Friendships (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	userID1 BIGINT NOT NULL,
	userID2 BIGINT NOT NULL,
	friendsFrom DATE DEFAULT current_date,
	status CHARACTER VARYING NOT NULL,
	FOREIGN KEY (userID1) REFERENCES users (id) ON DELETE CASCADE,
	FOREIGN KEY (userID2) REFERENCES users (id) ON DELETE CASCADE,
	CHECK (userID1 < userID2),
	CHECK (status IN ('pending', 'approved', 'declined')),
	UNIQUE (userID1, userID2)
);

CREATE TABLE Messages (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	senderID BIGINT NOT NULL,
	messageText TEXT NOT NULL,
	sentAt TIMESTAMP DEFAULT current_timestamp,
	messageType VARCHAR(20),
	repliedMessageId BIGINT,
	FOREIGN KEY (senderID) REFERENCES Users (id) ON DELETE CASCADE,
	FOREIGN KEY (repliedMessageId) REFERENCES Messages(id)
);

CREATE TABLE MessagesReceivers (
	messageID BIGINT NOT NULL,
	receiverID BIGINT NOT NULL,
	FOREIGN KEY (messageID) REFERENCES Messages (id) ON DELETE CASCADE,
	FOREIGN KEY (receiverID) REFERENCES Users (id) ON DELETE CASCADE,
	PRIMARY KEY (messageID, receiverID)
);

CREATE TABLE FriendRequests (
	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
	fromUserId BIGINT NOT NULL,
	toUserId BIGINT NOT NULL,
	status CHARACTER VARYING NOT NULL,
	CHECK (status IN ('pending', 'approved', 'declined'))
);


INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Ion', 'Pop', 'ionica94', crypt('94ion', gen_salt('bf')), 'ion.pop@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Vasile', 'Bogdan', 'vasilica', crypt('vasile20', gen_salt('bf')), 'vasile.bogdan@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Ion', 'Petre', 'petre98', crypt('petre', gen_salt('bf')), 'ion.petre@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('George', 'Pop', 'georgeTheBest', crypt('popgeorge', gen_salt('bf')), 'george.pop@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Andrei', 'Ioan', 'andreioan', crypt('ioan94', gen_salt('bf')), 'andrei.ioan@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Cristian', 'Muresan', 'cristi', crypt('muru', gen_salt('bf')), 'cristian.muresan@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Ionel', 'Pop', 'ionicapopica', crypt('popica', gen_salt('bf')), 'ionel.pop@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Violeta', 'Ciorpan', 'violeta_ciorpan', crypt('vio94', gen_salt('bf')), 'violeta.ciorpan@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Victor', 'Bene', 'victorasbine', crypt('vb90', gen_salt('bf')), 'victor.bene@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Cosmin', 'Muresan', '1994cosmin', crypt('1994cosmin', gen_salt('bf')), 'cosmin.muresan@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Zoltan', 'Nagy', 'zolly', crypt('nagybanya', gen_salt('bf')), 'zoltan.nagy@yahoo.com');
INSERT INTO Users (first_name, last_name, username, password, email) VALUES ('Maria', 'Vasilescu', 'maria_vasilescu', crypt('vasile25', gen_salt('bf')), 'maria.vasilescu@yahoo.com');

INSERT INTO friendships (userID1, userID2, status) VALUES (4, 8, 'approved');
INSERT INTO friendships (userID1, userID2, status) VALUES (1, 5, 'approved');
INSERT INTO friendships (userID1, userID2, status) VALUES (2, 5, 'approved');
INSERT INTO friendships (userID1, userID2, status) VALUES (2, 6, 'approved');
INSERT INTO friendships (userID1, userID2, status) VALUES (3, 5, 'approved');
INSERT INTO friendships (userID1, userID2, status) VALUES (4, 7, 'approved');
INSERT INTO friendships (userID1, userID2, status) VALUES (10, 11, 'approved');
INSERT INTO friendships (userID1, userID2, status) VALUES (1, 13, 'declined');
INSERT INTO friendships (userID1, userID2, status) VALUES (2, 13, 'pending');
INSERT INTO friendships (userID1, userID2, status) VALUES (3, 13, 'approved');
INSERT INTO friendships (userID1, userID2, status) VALUES (4, 13, 'pending');

INSERT INTO Messages (senderID, messageText, sentAt, messageType) VALUES (1, 'Hello!', current_timestamp, 'Message');
INSERT INTO Messages (senderID, messageText, sentAt, messageType) VALUES (3, 'Nice to meet you!', current_timestamp, 'Message');
INSERT INTO Messages (senderID, messageText, sentAt, messageType) VALUES (12, 'I''m glad I can talk to you!', current_timestamp, 'Message');
INSERT INTO Messages (senderID, messageText, sentAt, messageType) VALUES (2, 'I like it too!', current_timestamp, 'Message');
INSERT INTO Messages (senderID, messageText, sentAt, messageType) VALUES (2, 'Hi!', current_timestamp, 'Message');
INSERT INTO Messages (senderID, messageText, sentAt, messageType) VALUES (12, 'It''s so funny!', current_timestamp, 'Message');
INSERT INTO Messages (senderID, messageText, sentAt, messageType) VALUES (2, 'Indeed!', current_timestamp, 'Message');

INSERT INTO MessagesReceivers (messageID, receiverID) VALUES (1, 2);
INSERT INTO MessagesReceivers (messageID, receiverID) VALUES (2, 4);
INSERT INTO MessagesReceivers (messageID, receiverID) VALUES (3, 2);
INSERT INTO MessagesReceivers (messageID, receiverID) VALUES (4, 12);
INSERT INTO MessagesReceivers (messageID, receiverID) VALUES (5, 1);
INSERT INTO MessagesReceivers (messageID, receiverID) VALUES (6, 2);
INSERT INTO MessagesReceivers (messageID, receiverID) VALUES (7, 12);

INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (3, 13, 'approved');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (13, 2, 'approved');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (13, 1, 'declined');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (13, 5, 'declined');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (4, 13, 'approved');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (6, 13, 'pending');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (13, 7, 'approved');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (13, 8, 'pending');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (13, 9, 'approved');
INSERT INTO FriendRequests (fromUserId, toUserId, status) VALUES (13, 10, 'pending');


SELECT * FROM Users;

DROP TABLE users;

SELECT * FROM Users limit 2 offset 4;

SELECT * FROM friendships;

DROP TABLE friendships;

SELECT * FROM Messages;

SELECT * FROM Messages WHERE senderID = 3 OR senderID = 13 ORDER BY id

SELECT M.* FROM Messages M
                JOIN MessagesReceivers MR ON M.id = MR.messageID
                WHERE (M.senderID = 3 AND MR.receiverID = 13) OR (M.senderID = 13 AND MR.receiverID = 3)
                ORDER BY M.id

DROP TABLE Messages;

SELECT * FROM MessagesReceivers;

DROP TABLE MessagesReceivers;

SELECT * FROM FriendRequests;

DROP TABLE FriendRequests;

SELECT
    u1.id AS user_id,
    u1.first_name AS user_first_name,
    u1.last_name AS user_last_name,
    u2.id AS friend_id,
    u2.first_name AS friend_first_name,
    u2.last_name AS friend_last_name
FROM friendships AS f
JOIN users AS u1 ON f.userID1 = u1.id
JOIN users AS u2 ON f.userID2 = u2.id;

SELECT id FROM Users WHERE username = 'svr' AND password = crypt('svr', password);