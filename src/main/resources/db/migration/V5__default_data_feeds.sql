INSERT INTO DATA_FEED (NAME, GAME_ID) VALUES (
	'mtgjson.com',
	(SELECT ID FROM GAME WHERE NAME = 'Magic: the Gathering')
);