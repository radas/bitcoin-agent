package cz.kavan.radek.agent.bitcoin.domain.dao;

import cz.kavan.radek.agent.bitcoin.domain.entity.RatingEntity;

public interface RatingDAO {

    void addRating(RatingEntity rating);

    RatingEntity getRating();

}
