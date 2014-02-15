package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;
import cz.kavan.radek.agent.bitcoin.mapper.impl.TickerMapper;
import cz.kavan.radek.agent.bitcoin.scheduler.Agent;

/**
 * 
 * @author radek
 * 
 *         bid | nejvyšší nákupní nabídka - za tohle prodavam ask | Nejnižší
 *         prodejní nabídka - za tohle nakupuji
 */
public class BitcoinActualTradeAgent extends Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinActualTradeAgent.class);

    private TickerDAO tickerDAO;

    private TickerEntity ticker;

    @Override
    public void startAgent() {

        try {
            populateTradeInfo();
        } catch (Exception e) {
            logger.error("Something is wrong with BitcoinActualTradeAgent : " + e);
        }

    }

    private void populateTradeInfo() {
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

    private void writeTradeInfo() {
        if (ticker.getBid() == null || ticker.getAsk() == null) {
            logger.error("Can't get info about trade.");
            throw new IllegalArgumentException("Can't get info about trade.");
        }
        logger.debug("Status of trading. Sell: {} Buy: {}", ticker.getBid(), ticker.getAsk());

        populateTickerDAO();

    }

    private void populateTickerDAO() {
        ticker.setBuyDiff(ticker.getAsk().subtract(getRatingInfo()));
        ticker.setSellDiff(ticker.getBid().subtract(getRatingInfo()));
        tickerDAO.addTicker(ticker);
    }

    public void setTickerDAO(TickerDAO tickerDAO) {
        this.tickerDAO = tickerDAO;
    }

}
