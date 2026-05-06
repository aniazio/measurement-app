CREATE TABLE IF NOT EXISTS measurement
(
    sensor_id             uuid,
    city_id               uuid,
    measurement_timestamp timestamp,
    no2                   numeric(38,2),
    co                    numeric(38,2),
    pm10                  numeric(38,2)
    );

INSERT INTO measurement(sensor_id, city_id, measurement_timestamp, no2, co, pm10)
VALUES (RANDOM_UUID(), '00000000-0000-0000-0000-000000000001', cast('2024-12-01 15:30:00'as timestamp), 1.1, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000001', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000001', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000002', cast('2024-12-01 15:30:00'as timestamp), 1.2, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000002', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000002', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000003', cast('2024-12-01 15:30:00'as timestamp), 1.3, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000003', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000003', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000004', cast('2024-12-01 15:30:00'as timestamp), 1.4, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000004', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000004', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000005', cast('2024-12-01 15:30:00'as timestamp), 1.5, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000005', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000005', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000006', cast('2024-12-01 15:30:00'as timestamp), 1.6, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000006', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000006', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000007', cast('2024-12-01 15:30:00'as timestamp), 1.7, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000007', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000007', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000008', cast('2024-12-01 15:30:00'as timestamp), 1.8, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000008', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000008', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000009', cast('2024-12-01 15:30:00'as timestamp), 1.9, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000009', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000009', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

       (RANDOM_UUID(), '00000000-0000-0000-0000-00000000000a', cast('2024-12-01 15:30:00'as timestamp), 2, 2.2, 3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-00000000000a', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-00000000000a', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

-- only 10 limit
       (RANDOM_UUID(), '00000000-0000-0000-0000-00000000000b', cast('2024-12-01 15:30:00'as timestamp), 0.2, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-00000000000b', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-00000000000b', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

-- city without measurement in first day of the month
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000011', cast('2024-12-02 15:30:00'as timestamp), 30, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000011', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000011', cast('2024-12-31 15:30:00'as timestamp), 20, 2.2,
        3.3),

-- city without measurement in last day of the month
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000012', cast('2024-12-01 15:30:00'as timestamp), 30, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000012', cast('2024-12-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000012', cast('2024-12-30 15:30:00'as timestamp), 20, 2.2,
        3.3),

-- city without measurement any measurement in the considered month
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000013', cast('2024-11-01 15:30:00'as timestamp), 30, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000013', cast('2024-10-25 15:30:00'as timestamp), 10, 2.2,
        3.3),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000013', cast('2024-09-30 15:30:00'as timestamp), 20, 2.2,
        3.3),

-- city for last 3 hours stats
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000014', DATEADD('MINUTE', -2, CURRENT_TIMESTAMP), 1, 11, 30),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000014', DATEADD('HOUR', -1, CURRENT_TIMESTAMP), 2, 22, 20),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000014', DATEADD('HOUR', -2, CURRENT_TIMESTAMP), 3, 33, 10),
-- values not taken into the account
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000014', DATEADD('MINUTE', -200, CURRENT_TIMESTAMP), 100, 1000,
        10000),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000014', DATEADD('HOUR', -4, CURRENT_TIMESTAMP), 200, 2000, 20000),
       (RANDOM_UUID(), '00000000-0000-0000-0000-000000000014', DATEADD('HOUR', -5, CURRENT_TIMESTAMP), 300, 3000, 30000);


