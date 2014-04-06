package cz.kavan.radek.agent.bitcoin.scheduler;

import java.math.BigDecimal;

import cz.kavan.radek.agent.bitcoin.domain.dao.RatingDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

public abstract class Agent implements IAgent {

    protected BitstampClientImpl bitstamp;
    protected RatingDAO ratingDAO;
    protected TickerDAO tickerDAO;

    public abstract void startAgent();

    protected BigDecimal getRatingInfo() {
        return ratingDAO.getRating();
    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

    public void setRatingDAO(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }

    public void setTickerDAO(TickerDAO tickerDAO) {
        this.tickerDAO = tickerDAO;
    }

}
