-- Setup admin user in nso_v2
DELETE FROM nso_v2.users WHERE username = 'admin';
INSERT INTO nso_v2.users (
    username, password, activated, luong, role, status, tanthu, created_at, updated_at
) VALUES (
    'admin', '$2a$12$wjvakMxNHH5Ap.v.hxhA6.paq/ZEieweuXrlS0fvLJBqYuQuUi0LC', 1, 100000, 99, 0, 0, NOW(), NOW()
);

-- Get the inserted user ID and insert admin_v2 character into nso_v2.players
SET @admin_id = (SELECT id FROM nso_v2.users WHERE username = 'admin');

DELETE FROM nso_v2.players WHERE name = 'admin_v2';
INSERT INTO nso_v2.players (
    user_id, server_id, name, gender, clan, class, data, point, potential, spoint,
    skill, equiped, fashion, bijuu, map, saveCoordinate, head, head2, weapon, body, leg,
    xu, xuInBox, yen, numberCellBag, numberCellBox, bag, box, onCSkill, onOSkill, onKSkill,
    mount, effect, friends, enemies, taskId, task, rewardPB, event_point, spending_point,
    created_at, updated_at
) VALUES (
    @admin_id, 2, 'admin_v2', 1, 0, 0, '[10, 1000, 1000, 0, 0, 0, 0, 0, 0, 0]', 50, '[15, 5, 5, 5]', 10,
    '[]', '[]', '[]', '[]', '[22, 1741, 264]', 22, 14, -1, -1, -1, -1,
    1000000, 0, 500000, 30, 30, '[]', '[]', '[]', '[-1,-1,-1,-1,-1]', '[-1,-1,-1]',
    '[]', '[]', '[]', '[]', 0, NULL, 0, '[0,0,0]', 0,
    NOW(), NOW()
);

