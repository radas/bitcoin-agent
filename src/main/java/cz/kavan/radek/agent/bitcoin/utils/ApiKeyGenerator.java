package cz.kavan.radek.agent.bitcoin.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import cz.kavan.radek.agent.bitcoin.domain.dao.ApiKeyDAO;

public class ApiKeyGenerator {

    private ApiKeyDAO apiKeyDAO;
    private static final String CRYPT_ALG = "HmacSHA256";

    public String getApiSignature(String nonce) throws Exception {
        Mac sha256HMAC = populateSecretKeySpec();

        byte[] hash = sha256HMAC.doFinal(generateApiMessage(nonce).getBytes());

        return Hex.encodeHexString(hash).toUpperCase();

    }

    private Mac populateSecretKeySpec() throws Exception {
        Mac sha256HMAC = Mac.getInstance(CRYPT_ALG);
        SecretKeySpec secretKey = new SecretKeySpec(getApiSecret().getBytes(), CRYPT_ALG);
        sha256HMAC.init(secretKey);
        return sha256HMAC;
    }

    private String generateApiMessage(String nonce) throws Exception {
        return nonce + getClientApiId() + getApiKey();
    }

    protected String getClientApiId() {
        return apiKeyDAO.getApiAndSecretKey().getApiClientId();
    }

    public String getApiKey() throws Exception {
        String apiKey = apiKeyDAO.getApiAndSecretKey().getApiKey();
        return Protector.decryptApiKey(apiKey);
    }

    public String getNonce() {
        return String.valueOf(TimeUtil.getTimestamp());
    }

    public String getApiSecret() throws Exception {
        String apiSecret = apiKeyDAO.getApiAndSecretKey().getApiSecret();
        return Protector.decryptApiKey(apiSecret);
    }

    public void setApiKeyDAO(ApiKeyDAO apiKeyDAO) {
        this.apiKeyDAO = apiKeyDAO;
    }

}
