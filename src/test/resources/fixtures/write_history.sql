TRUNCATE TABLE cell_history RESTART IDENTITY;

INSERT INTO cell_history (cell_id, redrawing_time, color, x, y)
VALUES
    (1, '2025-05-24 12:45:03'::timestamp, '#ff0000', 0, 0),
    (2, '2025-05-25 09:15:17'::timestamp, '#00ff00', 1, 0),
    (3, '2025-05-27 16:05:50'::timestamp, '#0000ff', 2, 0),
    (4, '2025-05-28 08:22:40'::timestamp, '#ff0000', 0, 1),
    (5, '2025-05-28 08:22:51'::timestamp, '#ff0000', 1, 1),
    (6, '2025-05-28 08:23:05'::timestamp, '#00ff00', 2, 1),
    (7, '2025-05-29 11:00:00'::timestamp, '#0000ff', 0, 2),
    (8, '2025-05-30 14:10:25'::timestamp, '#ff0000', 1, 2),
    (9, '2025-05-30 14:11:10'::timestamp, '#00ff00', 2, 2),
    (10, '2025-05-30 14:12:33'::timestamp, '#0000ff', 3, 2);