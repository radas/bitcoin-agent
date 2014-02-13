package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.math.BigDecimal;
import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.domain.dao.RatingDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;
import cz.kavan.radek.agent.bitcoin.mapper.impl.TickerMapper;
import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

/**
 * 
 * @author radek
 * 
 *         bid | nejvyšší nákupní nabídka - za tohle prodavam ask | Nejnižší
 *         prodejní nabídka - za tohle nakupuji
 */
public class BitcoinActualTradeAgent implements Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinActualTradeAgent.class);

    private BitstampClientImpl bitstamp;
    private TickerDAO tickerDAO;
    private RatingDAO ratingDAO;

    private TickerEntity ticker;

    @Override
    public void startAgent() {

        try {
            populateTradeInfo();
        } catch (Exception e) {
            logger.error("Something is wrong with BitcoinActualTradeAgent : " + e);
        }

    }

    private void populateTradeInfo() throws ParseException {
        initBidAskValues();
        writeTradeInfo();
    }

    private void initBidAskValues() {
        Ticker bitstampMarket = bitstamp.getActualMarket();

        if (bitstampMarket != null) {
            ticker = new TickerEntity();
            TickerMapper.mapTickerResponse(bitstampMarket, ticker);
        }
    }

    private void writeTradeInfo() throws ParseException {
        if (ticker.getBid() == null || ticker.getAsk() == null) {
            logger.error("Can't get info about trade.");
            throw new IllegalArgumentException("Can't get info about trade.");
        }
        logger.debug("Status of trading. Sell: {} Buy: {}", ticker.getBid(), ticker.getAsk());

        populateTickerDAO();

    }

    private void populateTickerDAO() {
        ticker.setBuyDiff(ticker.getAsk().subtract(getBuyRatingInfo()));
        ticker.setSellDiff(ticker.getBid().subtract(getSellRatingInfo()));
        tickerDAO.addTicker(ticker);
    }

    private BigDecimal getBuyRatingInfo() {
        return ratingDAO.getRating().getBuyRating();
    }

    private BigDecimal getSellRatingInfo() {
        return ratingDAO.getRating().getSellRating();
    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

    public void setTickerDAO(TickerDAO tickerDAO) {
        this.tickerDAO = tickerDAO;
    }

    public void setRatingDAO(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

}
