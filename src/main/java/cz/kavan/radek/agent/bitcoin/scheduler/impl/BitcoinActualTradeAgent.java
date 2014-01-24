package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

public class BitcoinActualTradeAgent implements Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinActualTradeAgent.class);

    private BitstampClientImpl bitstamp;
    private TickerDAO tickerDAO;

    private BigDecimal bid; // nejvyšší nákupní nabídka - za tohle prodavam
    private BigDecimal ask; // Nejnižší prodejní nabídka - za tohle nakupuji

    @Override
    public void startAgent() {

        try {
            populateTradeInfo();
        } catch (Exception e) {
            logger.error("Something is wrong with: " + e);
        }

    }

    private void populateTradeInfo() {
        initBidAskValues();
        writeTradeInfo();
    }

    private void initBidAskValues() {
        if (bitstamp.getActualMarket() != null) {
            bid = bitstamp.getActualMarket().getBid();
            ask = bitstamp.getActualMarket().getAsk();
        }
    }

    private void writeTradeInfo() {
        if (bid == null | ask == null) {
            logger.error("Can't get info about trade.");
            throw new IllegalArgumentException("Can't get info about trade.");
        }
        logger.info("Status of trading. Sell: {} Buy: {}", bid, ask);

        Ticker ticker = new Ticker();
        ticker.setAsk(new BigDecimal("10.0"));
        ticker.setBid(new BigDecimal("5.0"));
        tickerDAO.addTicker(ticker);

    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

    public void setTickerDAO(TickerDAO tickerDAO) {
        this.tickerDAO = tickerDAO;
    }

}
