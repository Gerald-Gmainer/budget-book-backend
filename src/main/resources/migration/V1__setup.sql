CREATE TABLE bookings
(
    id           SERIAL PRIMARY KEY,
    booking_date DATE           NOT NULL,
    amount       DECIMAL(10, 2) NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
