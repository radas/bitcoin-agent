package cz.kavan.radek.agent.bitcoin.domain.dao.impl;

import java.math.BigDecimal;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.RatingDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.RatingEntity;

@Repository
public class RatingDAOImpl implements RatingDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void addRating(RatingEntity rating) {
        sessionFactory.getCurrentSession().save(rating);

    }

    @Override
    @Transactional
    public void updateRating(RatingEntity rating) {
        rating.setId(1);
        sessionFactory.getCurrentSession().update(rating);

    }

    @Override
    @Transactional
    public BigDecimal getRating() {
        RatingEntity rating = (RatingEntity) sessionFactory.getCurrentSession().createCriteria(RatingEntity.class)
                .list().get(0);
        return rating.getRating();
    }
}
