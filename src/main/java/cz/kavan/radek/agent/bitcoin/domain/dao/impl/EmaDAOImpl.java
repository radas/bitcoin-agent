package cz.kavan.radek.agent.bitcoin.domain.dao.impl;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.EmaDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.EmaEntity;

@Repository
public class EmaDAOImpl implements EmaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void addEma(EmaEntity ema) {
        sessionFactory.getCurrentSession().save(ema);

    }

    @Override
    @Transactional
    public EmaEntity getEma() {
        return (EmaEntity) sessionFactory.getCurrentSession().createCriteria(EmaEntity.class)
                .addOrder(Order.desc("id")).setMaxResults(1).list().get(0);
    }

}
