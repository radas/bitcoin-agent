package cz.kavan.radek.agent.bitcoin.domain.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;

@Repository("tickerDAO")
public class TickerDAOImpl implements TickerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addTicker(Ticker ticker) {
        sessionFactory.getCurrentSession().save(ticker);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ticker> getTickers() {
        return sessionFactory.getCurrentSession().createQuery("from TickerEntity").list();
    }

}
