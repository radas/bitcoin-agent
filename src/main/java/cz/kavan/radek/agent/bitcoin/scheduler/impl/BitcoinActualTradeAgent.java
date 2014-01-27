package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.math.BigDecimal;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;
import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;
import cz.kavan.radek.agent.bitcoin.utils.TimeUtil;

public class BitcoinActualTradeAgent implements Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinActualTradeAgent.class);

    private BitstampClientImpl bitstamp;
    private TickerDAO tickerDAO;

    private BigDecimal bid; // nejvyšší nákupní nabídka - za tohle prodavam
    private BigDecimal ask; // Nejnižší prodejní nabídka - za tohle nakupuji
    private long timestamp;

    @Override
    public void startAgent() {

        try {
            populateTradeInfo();
        } catch (Exception e) {
            logger.error("Something is wrong with: " + e);
        }

    }

    private void populateTradeInfo() throws ParseException {
        initBidAskValues();
        writeTradeInfo();
    }

    private void initBidAskValues() {
        if (bitstamp.getActualMarket() != null) {
            bid = bitstamp.getActualMarket().getBid();
            ask = bitstamp.getActualMarket().getAsk();
            timestamp = bitstamp.getActualMarket().getTimestamp();
        }
    }

    private void writeTradeInfo() throws ParseException {
        if (bid == null || ask == null) {
            logger.debug("Can't get info about trade.");
            throw new IllegalArgumentException("Can't get info about trade.");
        }
        logger.debug("Status of trading. Sell: {} Buy: {}", bid, ask);

        TickerEntity ticker = new TickerEntity();
        ticker.setAsk(ask);
        ticker.setBid(bid);

        ticker.setTimestamp(TimeUtil.convertTimestampLocalDateTime(timestamp));
        tickerDAO.addTicker(ticker);

    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

    public void setTickerDAO(TickerDAO tickerDAO) {
        this.tickerDAO = tickerDAO;
    }

}
