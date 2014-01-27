CREATE DATABASE bitcoin_agent;

GRANT ALL PRIVILEGES ON bitcoin_agent.* TO 'bitagent'@'127.0.0.1' IDENTIFIED BY 'secret';

use bitcoin_agent;

DROP TABLE IF EXISTS bitstamp_ticker;

CREATE TABLE bitstamp_ticker
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    sell    DECIMAL(10,2),
    buy     DECIMAL(10,2),
    created TIMESTAMP NOT NULL DEFAULT NOW(),
    trade_time timestamp
);