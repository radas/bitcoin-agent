CREATE DATABASE bitcoin_agent;

GRANT ALL PRIVILEGES ON bitcoin_agent.* TO 'bitagent'@'127.0.0.1' IDENTIFIED BY 'secret';

use bitcoin_agent;

DROP TABLE IF EXISTS bitstamp_ticker;

CREATE TABLE bitstamp_ticker
(
    id      BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    sell    DECIMAL(10,2),
    buy     DECIMAL(10,2),
    created TIMESTAMP NOT NULL DEFAULT NOW(),
    trade_time timestamp
);

DROP TABLE IF EXISTS bitstamp_api;

CREATE TABLE bitstamp_api
(
    id      BIGINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    api_key   VARCHAR(200),
    secret   VARCHAR(200)
    client_id INT UNSIGNED
);

INSERT INTO bitstamp_api (api_key, secret, client_id) VALUES ('api_key', 'secret', '123456');

