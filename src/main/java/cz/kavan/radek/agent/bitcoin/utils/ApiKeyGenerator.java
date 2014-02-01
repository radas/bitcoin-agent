package cz.kavan.radek.agent.bitcoin.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import cz.kavan.radek.agent.bitcoin.domain.dao.ApiKeyDAO;

public class ApiKeyGenerator {

    private ApiKeyDAO apiKeyDAO;
    private static final String CRYPT_ALG = "HmacSHA256";

    public String getApiSignature(String nonce) throws InvalidKeyException, NoSuchAlgorithmException {
        Mac sha256_HMAC = populateSecretKeySpec();

        byte[] hash = sha256_HMAC.doFinal(generateApiMessage(nonce).getBytes());

        return Hex.encodeHexString(hash).toUpperCase();

    }

    private Mac populateSecretKeySpec() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance(CRYPT_ALG);
        SecretKeySpec secretKey = new SecretKeySpec(getApiSecret().getBytes(), CRYPT_ALG);
        sha256_HMAC.init(secretKey);
        return sha256_HMAC;
    }

    private String generateApiMessage(String nonce) {
        return nonce + getClientApiId() + getApiKey();
    }

    protected String getClientApiId() {
        return apiKeyDAO.getApiAndSecretKey().getApiClientId();
    }

    protected String getApiKey() {
        return apiKeyDAO.getApiAndSecretKey().getApiKey();
    }

    protected String getNonce() {
        return String.valueOf(TimeUtil.getTimestamp());
    }

    public String getApiSecret() {
        return apiKeyDAO.getApiAndSecretKey().getApiSecret();
    }

    public void setApiKeyDAO(ApiKeyDAO apiKeyDAO) {
        this.apiKeyDAO = apiKeyDAO;
    }

}
