package cz.kavan.radek.agent.bitcoin.domain.dao;

import cz.kavan.radek.agent.bitcoin.domain.entity.ApiKeyEntity;

public interface ApiKeyDAO {

    public ApiKeyEntity getApiAndSecretKey();

    public void addApiKey(ApiKeyEntity apiKey);

}
