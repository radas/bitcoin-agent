package cz.kavan.radek.agent.bitcoin.scheduler;

import java.math.BigDecimal;

import cz.kavan.radek.agent.bitcoin.domain.dao.RatingDAO;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

public abstract class Agent {

    protected BitstampClientImpl bitstamp;
    protected RatingDAO ratingDAO;

    public abstract void startAgent();

    protected BigDecimal getBuyRatingInfo() {
        return ratingDAO.getRating().getBuyRating();
    }

    protected BigDecimal getSellRatingInfo() {
        return ratingDAO.getRating().getSellRating();
    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

    public void setRatingDAO(RatingDAO ratingDAO) {
        this.ratingDAO = ratingDAO;
    }
}
