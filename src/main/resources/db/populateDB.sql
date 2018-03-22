DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

  INSERT INTO meals (datetime, description, calories )VALUES
    ('2018-01-01 10:00:00', 'Завтрак',500),
    ('2018-01-01 13:00:00', 'Обед',1000),
    ('2018-01-01 20:00:00', 'Ужин',500),

    ('2018-01-02 10:00:00', 'Завтрак',1000),
    ('2018-01-02 13:00:00', 'Обед',500),
    ('2018-01-02 20:00:00', 'Ужин',510),

    ('2018-01-03 13:00:00', 'Обед',555),
    ('2018-01-03 20:00:00', 'Ужин',666),

    ('2018-01-04 10:00:00', 'Завтрак',1231),
    ('2018-01-04 13:00:00', 'Обед',236),
    ('2018-01-04 20:00:00', 'Ужин',896);

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);
