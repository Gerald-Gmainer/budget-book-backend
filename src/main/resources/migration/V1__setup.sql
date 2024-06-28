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
    id        SERIAL PRIMARY KEY,
    name      text                                NOT NULL,
    icon_id   int references category_icons (id)  NOT NULL,
    color_id  int references category_colors (id) NOT NULL,
    type      varchar                             NOT NULL,
    parent_id INT REFERENCES categories (id) DEFAULT NULL
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


