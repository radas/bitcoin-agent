package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.math.BigDecimal;

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
    private BigDecimal moneyGain;

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
        shotBySniper();
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

    private void shotBySniper() {
        logger.info("I'm trying to shot on the market!");
        if (isSellable()) {
            logger.info("Ok, I can sell {} BTC", balance.getBtc_available());
            logger.info("I can sell BTC when actual buy is bigger then: {}", getBuyRatingInfo());
            logger.info("Bigger then: {}", limitForSellingBtc());
        } else {
            logger.info("Ok, I can buy BTC with price {} USD", balance.getUsd_available());
            logger.info("I can buy when actual sell is lower then: {}", getSellRatingInfo());
            logger.info("Lower then: {}", limitForBuyingBtc());
        }

    }

    boolean isSellable() {
        return (balance.getBtc_available().compareTo(balance.getUsd_available())) == 1;
    }

    BigDecimal limitForSellingBtc() {
        return getBuyRatingInfo().add(moneyGain).add(balance.getFee());
    }

    BigDecimal limitForBuyingBtc() {
        return getBuyRatingInfo().subtract(moneyGain).subtract(balance.getFee());
    }

    public void setBalanceDAO(AccountBalanceDAO balanceDAO) {
        this.balanceDAO = balanceDAO;
    }

    public void setMoneyGain(BigDecimal moneyGain) {
        this.moneyGain = moneyGain;
    }

}
