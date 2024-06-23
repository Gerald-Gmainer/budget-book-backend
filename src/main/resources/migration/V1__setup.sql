CREATE TYPE category_type AS ENUM ('income', 'outcome');

CREATE TABLE account_icons
(
    id       SERIAL PRIMARY KEY,
    name     text NOT NULL,
    ui_order int DEFAULT -1
);

CREATE TABLE account_colors
(
    id       SERIAL PRIMARY KEY,
    code     text NOT NULL,
    name     text,
    ui_order int DEFAULT -1
);

CREATE TABLE accounts
(
    id       SERIAL PRIMARY KEY,
    name     text                               NOT NULL,
    icon_id  int references account_icons (id)  NOT NULL,
    color_id int references account_colors (id) NOT NULL
);

CREATE TABLE category_icons
(
    id       SERIAL PRIMARY KEY,
    name     text NOT NULL,
    ui_order int DEFAULT -1
);

CREATE TABLE category_colors
(
    id       SERIAL PRIMARY KEY,
    code     text NOT NULL,
    name     text,
    ui_order int DEFAULT -1
);

CREATE TABLE categories
(
    id       SERIAL PRIMARY KEY,
    name     text                                NOT NULL,
    icon_id  int references category_icons (id)  NOT NULL,
    color_id int references category_colors (id) NOT NULL,
    type     category_type                       NOT NULL
);

CREATE TABLE bookings
(
    id           SERIAL,
    booking_date date                           NOT NULL,
    description  varchar(20),
    amount       numeric(12, 3) DEFAULT 0       NOT NULL,
    category_id  int REFERENCES categories (id) NOT NULL,
    account_id   int REFERENCES accounts (id)   NOT NULL,
    created_at   timestamp      DEFAULT now(),
    updated_at   timestamp      DEFAULT now(),
    PRIMARY KEY (id)
);

---

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

--

