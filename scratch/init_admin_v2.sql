CREATE TEMPORARY TABLE tmp_player SELECT * FROM players WHERE id=7;
UPDATE tmp_player SET id=8, user_id=5, name='admin_v2', server_id=2, class=0, map='[22,1741,264]';
DELETE FROM players WHERE id=8 OR name='admin_v2';
INSERT INTO players SELECT * FROM tmp_player;
DROP TEMPORARY TABLE tmp_player;
SELECT id, user_id, server_id, name, class, map FROM players WHERE id=8;

