package cz.kavan.radek.agent.bitcoin.utils;

import cz.kavan.radek.agent.bitcoin.domain.dao.ApiKeyDAO;

public class ApiKeyGenerator {

    private final String key;
    private final String secret;

    private String signature;
    private String nonce;

    public ApiKeyGenerator(ApiKeyDAO apiKeyDAO) {
        super();
        this.key = apiKeyDAO.getApiAndSecretKey().getApiKey();
        this.secret = apiKeyDAO.getApiAndSecretKey().getApiSecret();
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public String getSignature() {
        return signature;
    }

    public String getNonce() {
        return nonce;
    }

}
