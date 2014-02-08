package cz.kavan.radek.agent.bitcoin.domain.dao;

import cz.kavan.radek.agent.bitcoin.domain.entity.ApiKeyEntity;

public interface ApiKeyDAO {

    ApiKeyEntity getApiAndSecretKey();

    void addApiKey(ApiKeyEntity apiKey);

}
