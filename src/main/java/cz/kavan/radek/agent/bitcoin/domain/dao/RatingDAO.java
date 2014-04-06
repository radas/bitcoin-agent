package cz.kavan.radek.agent.bitcoin.domain.dao;

import java.math.BigDecimal;

import cz.kavan.radek.agent.bitcoin.domain.entity.RatingEntity;

public interface RatingDAO {

    void addRating(RatingEntity rating);

    void updateRating(RatingEntity rating);

    BigDecimal getRating();

}
