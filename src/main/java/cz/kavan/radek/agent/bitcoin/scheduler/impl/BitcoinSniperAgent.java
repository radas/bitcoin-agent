package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;
import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

public class BitcoinSniperAgent implements Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinSniperAgent.class);

    private BitstampClientImpl bitstamp;
    private AccountBalanceDAO balanceDAO;

    private BigDecimal btc_available;
    private BigDecimal usd_available;
    private String errorMessage;

    @Override
    public void startAgent() {
        try {
            populateTradeSniper();
        } catch (Exception e) {
            logger.error("Something is wrong with BitcoinSniperAgent: " + e);
        }

    }

    private void populateTradeSniper() {
        initAccountBalance();
        writeAccountInfo();
    }

    private void initAccountBalance() {
        AccountBalance accountBalance = bitstamp.getAccountBalance().getBody();

        if (accountBalance != null) {
            btc_available = accountBalance.getBtc_available();
            usd_available = accountBalance.getUsd_available();
            errorMessage = accountBalance.getError();
        }
    }

    private void writeAccountInfo() {
        if (btc_available == null || usd_available == null) {
            logger.error("Can't get account status: {}", errorMessage);
            throw new IllegalArgumentException("Can't get info about account status: " + errorMessage);
        }
        logger.debug("Status of account status. BTC: {} USD: {}", btc_available, usd_available);

        AccountBalanceEntity balance = new AccountBalanceEntity();
        balance.setBtc_available(btc_available);
        balance.setUsd_available(usd_available);

        balanceDAO.addAccountBalance(balance);

    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

    public void setBalanceDAO(AccountBalanceDAO balanceDAO) {
        this.balanceDAO = balanceDAO;
    }

}
