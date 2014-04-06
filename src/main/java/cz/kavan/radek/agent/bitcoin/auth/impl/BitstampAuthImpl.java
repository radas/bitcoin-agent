package cz.kavan.radek.agent.bitcoin.auth.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cz.kavan.radek.agent.bitcoin.auth.MarketAuth;
import cz.kavan.radek.agent.bitcoin.errorhandling.exception.AuthRequestException;
import cz.kavan.radek.agent.bitcoin.utils.ApiKeyGenerator;

public class BitstampAuthImpl implements MarketAuth {

    private static final Logger logger = LoggerFactory.getLogger(BitstampAuthImpl.class);
    private MultiValueMap<String, String> bitstampParam;

    private ApiKeyGenerator apiKeyGenerator;

    @Override
    public HttpEntity<MultiValueMap<String, String>> setAuthRequest() {
        setAuthParams();
        logBitstampParam();

        return populateRequestEntity();
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> setAuthRequestWithOffer(BigDecimal amount, BigDecimal price) {
        setAuthParams();
        setOfferParams(amount, price);
        logBitstampParam();

        return populateRequestEntity();
    }

    private void setAuthParams() {
        bitstampParam = new LinkedMultiValueMap<>();

        final String nonce = apiKeyGenerator.getNonce();

        try {
            createApiSecurityKey(nonce);
        } catch (Exception e) {
            throw new AuthRequestException("Can't do authentification with bitstamp", e);
        }

        bitstampParam.add("nonce", nonce);
    }

    private void setOfferParams(BigDecimal amount, BigDecimal price) {
        bitstampParam.add("amount", String.valueOf(amount));
        bitstampParam.add("price", String.valueOf(price));
    }

    private void logBitstampParam() {
        if (logger.isDebugEnabled()) {
            logger.debug("BitstampParameters: ");
            for (Entry<String, List<String>> param : bitstampParam.entrySet()) {
                logger.debug("{} : {}", param.getKey(), param.getValue());
            }
        }
    }

    private void createApiSecurityKey(final String nonce) throws Exception {
        bitstampParam.add("key", apiKeyGenerator.getApiKey());
        bitstampParam.add("signature", apiKeyGenerator.getApiSignature(nonce));
    }

    private HttpEntity<MultiValueMap<String, String>> populateRequestEntity() {
        final HttpHeaders headers = createHeader();
        final HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(
                bitstampParam, headers);
        return requestEntity;
    }

    private HttpHeaders createHeader() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        return headers;
    }

    public void setApiKeyGenerator(ApiKeyGenerator apiKeyGenerator) {
        this.apiKeyGenerator = apiKeyGenerator;
    }

}
