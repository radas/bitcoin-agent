package cz.kavan.radek.agent.bitcoin.auth.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cz.kavan.radek.agent.bitcoin.auth.BitstampAuth;
import cz.kavan.radek.agent.bitcoin.utils.ApiKeyGenerator;

public class BitstampAuthImpl implements BitstampAuth {

    private static final Logger logger = LoggerFactory.getLogger(BitstampAuthImpl.class);

    private ApiKeyGenerator apiKeyGenerator;

    public HttpEntity<?> getHttpAuthEntity() {
        HttpEntity<?> httpEntity = null;
        try {
            httpEntity = setUpBitstampHeaders();
        } catch (Exception e) {
            logger.error("Reason: {} : Can't set up HttpEntity to: ", e.getMessage(), e);
            new RuntimeException("Can't set up HttpEntity: ", e);
        }

        return httpEntity;
    }

    private MultiValueMap<String, String> setUpBitstampParams() throws Exception {

        MultiValueMap<String, String> bitstampParam = new LinkedMultiValueMap<>();

        String nonce = apiKeyGenerator.getNonce();
        bitstampParam.add("key", apiKeyGenerator.getApiKey());
        bitstampParam.add("signature", apiKeyGenerator.getApiSignature(nonce));
        bitstampParam.add("nonce", nonce);
        return bitstampParam;
    }

    private HttpEntity<?> setUpBitstampHeaders() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> requestEntity = new HttpEntity<Object>(setUpBitstampParams(), headers);
        return requestEntity;
    }

    public void setApiKeyGenerator(ApiKeyGenerator apiKeyGenerator) {
        this.apiKeyGenerator = apiKeyGenerator;
    }

}
