package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.errorhandling.exception.AgentDataAccessException;
import cz.kavan.radek.agent.bitcoin.scheduler.IAgent;

public class BitconDBCleanerAgent implements IAgent {

    private static final Logger logger = LoggerFactory.getLogger(BitconDBCleanerAgent.class);

    private DataSource dataSource;
    private Connection conn;
    private Statement statement;

    @Override
    public void startAgent() {
        connectToDatabase();

        removeOldTickerData();
        removeOldBalanceData();
        removeOldEmaData();

        closeDatabase();

    }

    private void removeOldTickerData() {
        logger.info("Removing old bitstamp_ticker data");

        try {
            statement.execute("DELETE FROM bitstamp_ticker WHERE created < (NOW() - INTERVAL 1 MONTH)");
        } catch (SQLException e) {
            throw new AgentDataAccessException("Can't remove old rows", e);
        }

    }

    private void removeOldBalanceData() {
        logger.info("Removing old bitstamp_balance data");

        try {
            statement.execute("DELETE FROM bitstamp_balance WHERE created < (NOW() - INTERVAL 1 MONTH)");
        } catch (SQLException e) {
            throw new AgentDataAccessException("Can't remove old rows", e);
        }

    }

    private void removeOldEmaData() {
        logger.info("Removing old bitstamp_ema data");

        try {
            statement.execute("DELETE FROM bitstamp_ema WHERE created < (NOW() - INTERVAL 1 MONTH)");
        } catch (SQLException e) {
            throw new AgentDataAccessException("Can't remove old rows", e);
        }

    }

    private void connectToDatabase() {
        try {
            conn = dataSource.getConnection();
            statement = conn.createStatement();
        } catch (SQLException e) {
            throw new AgentDataAccessException("Can't connect to database", e);
        }

    }

    private void closeDatabase() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new AgentDataAccessException("Can't close database", e);
        }

    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
