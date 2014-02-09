package cz.kavan.radek.agent.bitcoin.domain.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;

@Repository
public class AccountBalanceDAOImpl implements AccountBalanceDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void addAccountBalance(AccountBalanceEntity balance) {
        sessionFactory.getCurrentSession().save(balance);

    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public List<AccountBalanceEntity> getBalance() {
        return sessionFactory.getCurrentSession().createCriteria(AccountBalanceEntity.class).list();
    }

}
