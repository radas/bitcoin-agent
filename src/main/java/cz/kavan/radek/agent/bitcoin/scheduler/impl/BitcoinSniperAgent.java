package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;
import cz.kavan.radek.agent.bitcoin.mapper.impl.AccountBalanceMapper;
import cz.kavan.radek.agent.bitcoin.scheduler.Agent;

public class BitcoinSniperAgent extends Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinSniperAgent.class);

    private AccountBalanceDAO balanceDAO;

    AccountBalance accountBalance;
    AccountBalanceEntity balance;

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
            balance = new AccountBalanceEntity();
            AccountBalanceMapper.mapAccountBalanceResponse(accountBalance, balance);
        }
    }

    private void writeAccountInfo() {
        if (balance.getBtc_available() == null || balance.getUsd_available() == null) {
            logger.error("Can't get account status: {}", accountBalance.getError());
            throw new IllegalArgumentException("Can't get info about account status: " + accountBalance.getError());
        }
        logger.debug("Status of account status. BTC: {} USD: {}", balance.getBtc_available(),
                balance.getUsd_available());

        balanceDAO.addAccountBalance(balance);

    }

    public void setBalanceDAO(AccountBalanceDAO balanceDAO) {
        this.balanceDAO = balanceDAO;
    }

}
