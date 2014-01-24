CREATE TABLE bitstamp_ticker
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    sell    DECIMAL(10,2),
    buy     DECIMAL(10,2),
    created TIMESTAMP DEFAULT NOW()
);