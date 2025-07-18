
DELETE FROM expenses WHERE user_id IN (1,2);

-- 15 expenses for john (user_id=1), dates 2025-07-01 → 2025-07-05
INSERT INTO expenses (description, amount, created_at, user_id) VALUES
  ('Coffee', 4.50, '2025-07-01 09:15:00', 1),
  ('Lunch', 12.25, '2025-07-01 12:30:00', 1),
  ('Taxi', 23.10, '2025-07-01 18:45:00', 1),
  ('Groceries', 54.99, '2025-07-02 10:00:00', 1),
  ('Books', 30.00, '2025-07-02 15:20:00', 1),
  ('Movie Ticket', 11.00, '2025-07-02 20:00:00', 1),
  ('Gym Membership', 45.00, '2025-07-03 08:00:00', 1),
  ('Coffee', 3.75, '2025-07-03 10:30:00', 1),
  ('Dinner', 28.50, '2025-07-03 19:00:00', 1),
  ('Taxi', 18.00, '2025-07-04 09:00:00', 1),
  ('Groceries', 60.00, '2025-07-04 14:00:00', 1),
  ('Snacks', 5.00, '2025-07-04 16:30:00', 1),
  ('Book', 15.00, '2025-07-05 11:00:00', 1),
  ('Subscription', 9.99, '2025-07-05 13:00:00', 1),
  ('Coffee', 4.00, '2025-07-05 17:45:00', 1);

-- 15 expenses for sharath (user_id=2), dates 2025-07-06 → 2025-07-08
INSERT INTO expenses (description, amount, created_at, user_id) VALUES
  ('MacBook Air M3', 1299.99, '2025-07-06 10:00:00', 2),
  ('Coffee', 3.50, '2025-07-06 11:15:00', 2),
  ('Groceries', 45.00, '2025-07-06 13:30:00', 2),
  ('Lunch', 14.00, '2025-07-06 12:00:00', 2),
  ('Movie Ticket', 12.00, '2025-07-07 20:00:00', 2),
  ('Gym Gear', 75.00, '2025-07-07 09:00:00', 2),
  ('Taxi', 20.00, '2025-07-07 18:00:00', 2),
  ('Books', 25.00, '2025-07-07 15:00:00', 2),
  ('Coffee', 4.25, '2025-07-08 08:30:00', 2),
  ('Groceries', 50.00, '2025-07-08 14:00:00', 2),
  ('Dinner', 30.00, '2025-07-08 19:00:00', 2),
  ('Snacks', 6.00, '2025-07-08 16:00:00', 2),
  ('Subscription', 9.99, '2025-07-08 13:00:00', 2),
  ('Taxi', 18.50, '2025-07-08 10:30:00', 2),
  ('Coffee', 3.75, '2025-07-08 09:15:00', 2);