INSERT INTO account_colors (code, name, ui_order)
VALUES ('#FF5733', 'red', 1),
       ('#33FF57', 'green', 2),
       ('#3366FF', 'blue', 3),
       ('#c2c226', 'yellow', 4),
       ('#FF33FF', 'pink', 5),
       ('#FF6633', 'orange', 6),
       ('#9933FF', 'purple', 7),
       ('#33FFFF', 'cyan', 8),
       ('#6cb622', 'lime_green', 9),
       ('#FF9966', 'peach', 10),
       ('#3399CC', 'teal', 11),
       ('#808080', 'gray', 12);

INSERT INTO account_icons (name, ui_order)
VALUES ('cash-multiple', 1),
       ('wallet-giftcard', 2),
       ('credit-card-outline', 3),
       ('bank', 4),
       ('coin', 5),
       ('credit-card-plus', 6),
       ('wallet', 7),
       ('shopping', 8),
       ('credit-card-clock', 9),
       ('atm', 10);

INSERT INTO accounts (name, icon_id, color_id)
VALUES ('Cash',
        (SELECT id FROM account_icons WHERE name = 'cash-multiple'),
        (SELECT id FROM account_colors WHERE name = 'green'));

INSERT INTO accounts (name, icon_id, color_id)
VALUES ('Debit Card',
        (SELECT id FROM account_icons WHERE name = 'wallet-giftcard'),
        (SELECT id FROM account_colors WHERE name = 'orange'));

---

INSERT INTO category_colors (code, name, ui_order)
VALUES ('#FF5733', 'red', 1),
       ('#33FF57', 'green', 2),
       ('#3366FF', 'blue', 3),
       ('#c2c226', 'yellow', 4),
       ('#FF33FF', 'pink', 5),
       ('#FF6633', 'orange', 6),
       ('#9933FF', 'purple', 7),
       ('#33FFFF', 'cyan', 8),
       ('#6cb622', 'lime_green', 9),
       ('#FF9966', 'peach', 10),
       ('#3399CC', 'teal', 11),
       ('#808080', 'gray', 12);

INSERT INTO category_icons (name, ui_order)
VALUES ('account', 1),
       ('airplane', 2),
       ('book', 3),
       ('car', 4),
       ('cash-multiple', 5),
       ('credit-card', 6),
       ('food', 7),
       ('gift', 8),
       ('home', 9),
       ('lightbulb-outline', 10),
       ('movie', 11),
       ('music', 12),
       ('phone', 13),
       ('rocket', 14),
       ('school', 15),
       ('shopping', 16),
       ('train', 17),
       ('umbrella', 18),
       ('wallet-giftcard', 19),
       ('water', 20),
       ('wallet-membership', 21),
       ('umbrella-outline', 22),
       ('ticket-account', 23),
       ('shopping-music', 24),
       ('dots', 25),
       ('briefcase', 26),
       ('camera', 27),
       ('guitar', 28),
       ('medical-bag', 29),
       ('t-shirt-crew-outline', 30),
       ('food-apple', 31),
       ('palette', 32),
       ('watch', 33),
       ('train-car', 34),
       ('basketball', 35);

-- Insert Parent Categories
INSERT INTO public.categories (name, type, icon_id, color_id)
SELECT 'Food',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'food'),
       (SELECT id FROM public.category_colors WHERE name = 'red')
UNION ALL
SELECT 'Housing',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'home'),
       (SELECT id FROM public.category_colors WHERE name = 'blue')
UNION ALL
SELECT 'Personal',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'school'),
       (SELECT id FROM public.category_colors WHERE name = 'orange')
UNION ALL
SELECT 'Transport',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'car'),
       (SELECT id FROM public.category_colors WHERE name = 'yellow')
UNION ALL
SELECT 'Gift',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'gift'),
       (SELECT id FROM public.category_colors WHERE name = 'pink')
UNION ALL
SELECT 'Other',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'dots'),
       (SELECT id FROM public.category_colors WHERE name = 'gray');

-- Insert Child Categories
INSERT INTO public.categories (name, type, icon_id, color_id, parent_id)
SELECT 'Groceries',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'food'),
       (SELECT id FROM public.category_colors WHERE name = 'orange'),
       (SELECT id FROM public.categories WHERE name = 'Food')
UNION ALL
SELECT 'Enjoyment',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'music'),
       (SELECT id FROM public.category_colors WHERE name = 'purple'),
       (SELECT id FROM public.categories WHERE name = 'Food')
UNION ALL
SELECT 'Eating Out',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'food'),
       (SELECT id FROM public.category_colors WHERE name = 'peach'),
       (SELECT id FROM public.categories WHERE name = 'Food')
UNION ALL
SELECT 'Alcohol',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'water'),
       (SELECT id FROM public.category_colors WHERE name = 'cyan'),
       (SELECT id FROM public.categories WHERE name = 'Food')
UNION ALL
SELECT 'Rent',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'home'),
       (SELECT id FROM public.category_colors WHERE name = 'red'),
       (SELECT id FROM public.categories WHERE name = 'Housing')
UNION ALL
SELECT 'Insurance',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'medical-bag'),
       (SELECT id FROM public.category_colors WHERE name = 'lime_green'),
       (SELECT id FROM public.categories WHERE name = 'Housing')
UNION ALL
SELECT 'Internet+Phone',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'phone'),
       (SELECT id FROM public.category_colors WHERE name = 'teal'),
       (SELECT id FROM public.categories WHERE name = 'Housing')
UNION ALL
SELECT 'Furniture',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'shopping'),
       (SELECT id FROM public.category_colors WHERE name = 'purple'),
       (SELECT id FROM public.categories WHERE name = 'Housing')
UNION ALL
SELECT 'Pet',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'umbrella'),
       (SELECT id FROM public.category_colors WHERE name = 'orange'),
       (SELECT id FROM public.categories WHERE name = 'Housing')
UNION ALL
SELECT 'Household',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'lightbulb-outline'),
       (SELECT id FROM public.category_colors WHERE name = 'blue'),
       (SELECT id FROM public.categories WHERE name = 'Housing')
UNION ALL
SELECT 'Daiku',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'briefcase'),
       (SELECT id FROM public.category_colors WHERE name = 'red'),
       (SELECT id FROM public.categories WHERE name = 'Personal')
UNION ALL
SELECT 'Sport',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'basketball'),
       (SELECT id FROM public.category_colors WHERE name = 'lime_green'),
       (SELECT id FROM public.categories WHERE name = 'Personal')
UNION ALL
SELECT 'Investment',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'ticket-account'),
       (SELECT id FROM public.category_colors WHERE name = 'cyan'),
       (SELECT id FROM public.categories WHERE name = 'Personal')
UNION ALL
SELECT 'Entertainment',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'movie'),
       (SELECT id FROM public.category_colors WHERE name = 'pink'),
       (SELECT id FROM public.categories WHERE name = 'Personal')
UNION ALL
SELECT 'Hygiene',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'medical-bag'),
       (SELECT id FROM public.category_colors WHERE name = 'gray'),
       (SELECT id FROM public.categories WHERE name = 'Personal')
UNION ALL
SELECT 'Cloth',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 't-shirt-crew-outline'),
       (SELECT id FROM public.category_colors WHERE name = 'purple'),
       (SELECT id FROM public.categories WHERE name = 'Personal')
UNION ALL
SELECT 'Car',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'car'),
       (SELECT id FROM public.category_colors WHERE name = 'yellow'),
       (SELECT id FROM public.categories WHERE name = 'Transport')
UNION ALL
SELECT 'Public Transit',
       'outcome'::public.category_type,
       (SELECT id FROM public.category_icons WHERE name = 'train'),
       (SELECT id FROM public.category_colors WHERE name = 'teal'),
       (SELECT id FROM public.categories WHERE name = 'Transport');
