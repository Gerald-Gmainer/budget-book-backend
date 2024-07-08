INSERT INTO categories (name, type, icon_id, color_id)
SELECT 'Food',
       'OUTCOME',
       (SELECT id FROM category_icons WHERE name = 'food'),
       (SELECT id FROM category_colors WHERE name = 'pink')
UNION ALL
SELECT 'Housing',
       'OUTCOME',
       (SELECT id FROM category_icons WHERE name = 'briefcase'),
       (SELECT id FROM category_colors WHERE name = 'purple')
UNION ALL
SELECT 'Other',
       'OUTCOME',
       (SELECT id FROM category_icons WHERE name = 'dots'),
       (SELECT id FROM category_colors WHERE name = 'gray')
UNION ALL
SELECT 'Salary',
       'INCOME',
       (SELECT id FROM category_icons WHERE name = 'book'),
       (SELECT id FROM category_colors WHERE name = 'green')
UNION ALL
SELECT 'Child Benefit',
       'INCOME',
       (SELECT id FROM category_icons WHERE name = 'school'),
       (SELECT id FROM category_colors WHERE name = 'orange');


INSERT INTO categories (name, type, icon_id, color_id, parent_id)
SELECT 'Groceries',
       'OUTCOME',
       (SELECT id FROM category_icons WHERE name = 'food'),
       (SELECT id FROM category_colors WHERE name = 'pink'),
       (SELECT id FROM categories WHERE name = 'Food')
UNION ALL
SELECT 'Eating Out',
       'OUTCOME',
       (SELECT id FROM category_icons WHERE name = 'food'),
       (SELECT id FROM category_colors WHERE name = 'peach'),
       (SELECT id FROM categories WHERE name = 'Food')
UNION ALL
SELECT 'Rent',
       'OUTCOME',
       (SELECT id FROM category_icons WHERE name = 'home'),
       (SELECT id FROM category_colors WHERE name = 'blue'),
       (SELECT id FROM categories WHERE name = 'Housing')
UNION ALL
SELECT 'Household',
       'OUTCOME',
       (SELECT id FROM category_icons WHERE name = 'home'),
       (SELECT id FROM category_colors WHERE name = 'purple'),
       (SELECT id FROM categories WHERE name = 'Housing');

--

INSERT INTO bookings (booking_date, description, amount, category_id, account_id)
VALUES ('2024-06-01', 'Work', 2300,
        (SELECT id FROM categories WHERE name = 'Salary'),
        (SELECT id FROM accounts WHERE name = 'Cash'));

INSERT INTO bookings (booking_date, description, amount, category_id, account_id)
VALUES ('2024-06-10', 'Kindergeld', 425.5,
        (SELECT id FROM categories WHERE name = 'Child Benefit'),
        (SELECT id FROM accounts WHERE name = 'Cash'));

INSERT INTO bookings (booking_date, description, amount, category_id, account_id)
VALUES ('2024-06-01', 'Grocery Shopping', 150.75,
        (SELECT id FROM categories WHERE name = 'Groceries'),
        (SELECT id FROM accounts WHERE name = 'Cash'));

INSERT INTO bookings (booking_date, description, amount, category_id, account_id)
VALUES ('2024-06-15', 'Dining Out', 60.50,
        (SELECT id FROM categories WHERE name = 'Eating Out'),
        (SELECT id FROM accounts WHERE name = 'Cash'));

INSERT INTO bookings (booking_date, description, amount, category_id, account_id)
VALUES ('2024-06-03', 'Rent Payment', 900.00,
        (SELECT id FROM categories WHERE name = 'Rent'),
        (SELECT id FROM accounts WHERE name = 'Debit Card'));

INSERT INTO bookings (booking_date, description, amount, category_id, account_id)
VALUES ('2024-06-05', 'Electricity Bill', 100.25,
        (SELECT id FROM categories WHERE name = 'Household'),
        (SELECT id FROM accounts WHERE name = 'Debit Card'));

INSERT INTO bookings (booking_date, description, amount, category_id, account_id)
VALUES ('2024-06-17', 'minor thing', 50,
        (SELECT id FROM categories WHERE name = 'Other'),
        (SELECT id FROM accounts WHERE name = 'Debit Card'));



