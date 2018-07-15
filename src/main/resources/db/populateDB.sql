DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('30-05-2015 10:00', 'Завтрак', 1000, 100000),
  ('30-05-2015 13:00', 'Обед', 1000, 100000),
  ('30-05-2015 20:00', 'Ужин', 500, 100000),
  ('30-06-2015 10:00', 'Завтрак', 850, 100000),
  ('31-05-2015 10:00', 'Breakfast admin', 750, 100001),
  ('31-05-2015 13:00', 'Lunch admin', 700, 100001),
  ('31-05-2015 20:00', 'Dinner admin', 500, 100001);
