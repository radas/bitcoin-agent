package cz.kavan.radek.agent.bitcoin.domain.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;

@Repository
public class TickerDAOImpl implements TickerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void addTicker(TickerEntity ticker) {
        sessionFactory.getCurrentSession().save(ticker);

    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<TickerEntity> getTickers() {
        return sessionFactory.getCurrentSession().createQuery("from TickerEntity").list();
    }

}
