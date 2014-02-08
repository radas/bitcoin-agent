package cz.kavan.radek.agent.bitcoin.domain.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.ApiKeyDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.ApiKeyEntity;

@Repository
public class ApiKeyDAOImpl implements ApiKeyDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public ApiKeyEntity getApiAndSecretKey() {
        return (ApiKeyEntity) sessionFactory.getCurrentSession().createCriteria(ApiKeyEntity.class).list().get(0);
    }

    @Override
    @Transactional
    public void addApiKey(ApiKeyEntity apiKey) {
        sessionFactory.getCurrentSession().save(apiKey);

    }

}
