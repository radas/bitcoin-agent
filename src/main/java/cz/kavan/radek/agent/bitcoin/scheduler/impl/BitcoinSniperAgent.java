package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.EmaDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;
import cz.kavan.radek.agent.bitcoin.domain.entity.EmaEntity;
import cz.kavan.radek.agent.bitcoin.mapper.impl.AccountBalanceMapper;
import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.strategy.EmaStrategy;

public class BitcoinSniperAgent extends Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinSniperAgent.class);

    private AccountBalanceDAO balanceDAO;
    private BigDecimal moneyGain;

    private AccountBalance accountBalance;
    private AccountBalanceEntity balance;

    private EmaEntity ema;
    private EmaDAO emaDao;

    private BigDecimal lastAsk;
    private BigDecimal lastBid;

    @Override
    public void startAgent() {
        try {
            populateTradeSniper();
        } catch (Exception e) {
            logger.error("Something is wrong with BitcoinSniperAgent: " + e);
        }

    }

    private void populateTradeSniper() {
        initBitstampValues();
        writeAccountInfo();
        shotBySniper();
    }

    private void initBitstampValues() {
        AccountBalance accountBalance = bitstamp.getAccountBalance().getBody();

        if (bitstamp == null) {
            throw new IllegalArgumentException("Bitstamp market info is not available");

        } else {
            lastAsk = bitstamp.getActualMarket().getAsk();
            lastBid = bitstamp.getActualMarket().getBid();
        }

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

            logger.info("I can sell BTC when actual buy is bigger then: {}", getRatingInfo());
            logger.info("Bigger then: {}", limitForSellingBtc());
        } else {
            logger.info("Ok, I can buy BTC with price {} USD", balance.getUsd_available());

            logger.info("I can buy when actual sell is lower then: {}", getRatingInfo());
            logger.info("Lower then: {}", limitForBuyingBtc());
        }

    }

    boolean isSellable() {
        int hasMinimalAvailableBTC = balance.getBtc_available().compareTo(new BigDecimal("0.05"));
        int hasMinimalAvailableUSD = balance.getUsd_available().compareTo(new BigDecimal("10.00"));

        if ((hasMinimalAvailableBTC == -1) && (hasMinimalAvailableUSD == -1)) {
            logger.info("No BTC or Money = no funny");

            BigDecimal emaSell = EmaStrategy.computeEmaIndex(true, tickerDAO.getLastTickers(), emaDao.getEma()
                    .getEmaSell());

            BigDecimal emaBuy = EmaStrategy.computeEmaIndex(false, tickerDAO.getLastTickers(), emaDao.getEma()
                    .getEmaBuy());

            logger.info("I'm trying to get and save new EMA!");
            logger.info("Last sell EMA is {}", emaDao.getEma().getEmaSell());
            logger.info("New last sell EMA is {}", emaSell);

            logger.info("Last buy EMA is {}", emaDao.getEma().getEmaBuy());
            logger.info("New last buy EMA is {}", emaBuy);

            ema = new EmaEntity();
            ema.setEmaSell(emaSell);
            ema.setEmaBuy(emaBuy);

            emaDao.addEma(ema);

            throw new IllegalArgumentException("No BTC or Money = no funny");
        }
        return ((balance.getBtc_available().multiply(getRatingInfo())).compareTo(balance.getUsd_available())) == 1;
    }

    BigDecimal limitForSellingBtc() {
        BigDecimal needRateWithGain = getRatingInfo().add(moneyGain);
        BigDecimal fee = (((needRateWithGain).multiply(balance.getBtc_available())).divide(new BigDecimal(100)))
                .multiply(balance.getFee());
        return needRateWithGain.add(fee);
    }

    BigDecimal limitForBuyingBtc() {
        BigDecimal needRateWithGain = getRatingInfo().subtract(moneyGain);
        BigDecimal howManyBitcoinsCanIBuy = obtainBTCpossibleToBuy(needRateWithGain);

        BigDecimal fee = (((needRateWithGain).multiply(howManyBitcoinsCanIBuy)).divide(new BigDecimal(100)))
                .multiply(balance.getFee());
        return needRateWithGain.subtract(fee);
    }

    BigDecimal obtainBTCpossibleToBuy(BigDecimal needRateWithGain) {
        return balance.getUsd_available().divide(needRateWithGain, 2, RoundingMode.FLOOR);
    }

    public void setBalanceDAO(AccountBalanceDAO balanceDAO) {
        this.balanceDAO = balanceDAO;
    }

    public void setMoneyGain(BigDecimal moneyGain) {
        this.moneyGain = moneyGain;
    }

    public void setEmaDao(EmaDAO emaDao) {
        this.emaDao = emaDao;
    }

}
