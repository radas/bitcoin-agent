package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.EmaDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;
import cz.kavan.radek.agent.bitcoin.domain.entity.EmaEntity;
import cz.kavan.radek.agent.bitcoin.domain.entity.RatingEntity;
import cz.kavan.radek.agent.bitcoin.mapper.impl.AccountBalanceMapper;
import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.strategy.EmaStrategy;

public class BitcoinSniperAgent extends Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinSniperAgent.class);
    private static final Logger statsLogger = LoggerFactory.getLogger("stats");

    private AccountBalanceDAO balanceDAO;
    private BigDecimal moneyGain;

    private AccountBalance accountBalance;
    private AccountBalanceEntity balance;

    private EmaEntity ema;
    private RatingEntity ratingEntity;
    private EmaDAO emaDao;

    private Ticker ticker;

    @Override
    public void startAgent() {
        try {
            populateTradeSniper();
        } catch (Exception e) {
            logger.error("Something is wrong with BitcoinSniperAgent: ", e);
        }

    }

    private void populateTradeSniper() {
        initBitstampValues();
        writeAccountInfo();
        shotBySniper();
    }

    private void initBitstampValues() {
        accountBalance = bitstamp.getAccountBalance().getBody();

        if (bitstamp == null) {
            throw new IllegalArgumentException("Bitstamp market info is not available");

        } else {
            ticker = bitstamp.getActualMarket();
        }

        if (accountBalance != null) {
            balance = new AccountBalanceEntity();
            AccountBalanceMapper.mapAccountBalanceResponse(accountBalance, balance);
        }
    }

    private void writeAccountInfo() {
        if (balance.getBtcAvailable() == null || balance.getUsdAvailable() == null) {
            logger.error("Can't get account status: {}", accountBalance.getError());
            throw new IllegalArgumentException("Can't get info about account status: " + accountBalance.getError());
        }
        logger.debug("Status of account status. BTC: {} USD: {}", balance.getBtcAvailable(), balance.getUsdAvailable());

        balanceDAO.addAccountBalance(balance);

    }

    private void shotBySniper() {
        logger.debug("I'm trying to shot on the market!");
        writeEmaStats();

        if (isSellable()) {
            populateSelling();
        } else {
            populateBuying();
        }

    }

    private void writeEmaStats() {
        BigDecimal emaSell = EmaStrategy
                .computeEmaIndex(true, tickerDAO.getLastTickers(), emaDao.getEma().getEmaSell());

        BigDecimal emaBuy = EmaStrategy.computeEmaIndex(false, tickerDAO.getLastTickers(), emaDao.getEma().getEmaBuy());

        ema = new EmaEntity();
        ema.setEmaSell(emaSell);
        ema.setEmaBuy(emaBuy);

        emaDao.addEma(ema);
    }

    void populateSelling() {
        logger.debug("Ok, I can sell {} BTC", balance.getBtcAvailable());
        logger.debug("I can sell BTC when actual buy is bigger then: {}", getRatingInfo());

        final BigDecimal lastBid = ticker.getBid();

        logger.debug("Bigger then: {} and actual is {}", limitForSellingBtc(), lastBid);
        logger.debug("And second condition is that must be lower than {}", emaDao.getEma().getEmaSell());

        if (lastBid.compareTo(limitForSellingBtc()) == 1) {
            logger.info("great, gain is bigger");
            if (lastBid.compareTo(emaDao.getEma().getEmaSell()) == -1) {
                sellBTC(lastBid);
            }

        }
    }

    private void sellBTC(BigDecimal lastBid) {
        logger.debug("Trade is comming down, sell it!");
        logger.debug("Sell price: {} ", lastBid);

        statsLogger.info("Trying to sell it with price: {}", lastBid);

        bitstamp.sellBTC(balance.getBtcAvailable(), lastBid);

        logger.debug("Updating DB");
        ratingEntity = new RatingEntity();
        ratingEntity.setRating(lastBid);
        ratingDAO.updateRating(ratingEntity);
    }

    void populateBuying() {
        logger.debug("Ok, I can buy BTC with price {} USD", balance.getUsdAvailable());
        logger.debug("I can buy when actual sell is lower then: {}", getRatingInfo());

        final BigDecimal lastAsk = ticker.getAsk();

        logger.debug("Lower then: {} and actual is {}", limitForBuyingBtc(), lastAsk);
        logger.debug("And second condition is that must be bigger than {}", emaDao.getEma().getEmaBuy());

        if (lastAsk.compareTo(limitForBuyingBtc()) == -1) {
            logger.info("great, gain is bigger vetsi");
            if (lastAsk.compareTo(emaDao.getEma().getEmaBuy()) == 1) {
                buyBTC(lastAsk);

            }

        }
    }

    private void buyBTC(BigDecimal lastAsk) {
        logger.debug("Trade is comming up, buy it!");
        logger.debug("Price: {} ", lastAsk);

        statsLogger.info("Trying to buy it with price: {}", lastAsk);

        BigDecimal fee = (balance.getUsdAvailable().divide(new BigDecimal(100))).multiply(balance.getFee());

        logger.debug("fee: {} ", fee);

        BigDecimal amount = (balance.getUsdAvailable().subtract(fee)).divide(lastAsk, 2, RoundingMode.DOWN);

        logger.debug("Amount: {} ", amount);

        logger.debug("Account status {}", balance.getUsdAvailable());

        bitstamp.buyBTC(amount, lastAsk);

        logger.debug("Updating DB");
        ratingEntity = new RatingEntity();
        ratingEntity.setRating(lastAsk);
        ratingDAO.updateRating(ratingEntity);
    }

    boolean isSellable() {
        int hasMinimalAvailableBTC = balance.getBtcAvailable().compareTo(new BigDecimal("0.05"));
        int hasMinimalAvailableUSD = balance.getUsdAvailable().compareTo(new BigDecimal("10.00"));

        if ((hasMinimalAvailableBTC == -1) && (hasMinimalAvailableUSD == -1)) {
            throw new IllegalArgumentException("No BTC or Money = no funny");
        }
        return ((balance.getBtcAvailable().multiply(getRatingInfo())).compareTo(balance.getUsdAvailable())) == 1;
    }

    BigDecimal limitForSellingBtc() {
        BigDecimal needRateWithGain = getRatingInfo().add(moneyGain);
        BigDecimal fee = (((needRateWithGain).multiply(balance.getBtcAvailable())).divide(new BigDecimal(100)))
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
        return balance.getUsdAvailable().divide(needRateWithGain, 2, RoundingMode.FLOOR);
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
