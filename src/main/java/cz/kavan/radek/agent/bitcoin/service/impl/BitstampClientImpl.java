package cz.kavan.radek.agent.bitcoin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.service.BitstampClient;
import cz.kavan.radek.agent.bitcoin.utils.ApiKeyGenerator;

public class BitstampClientImpl implements BitstampClient {

    private static final Logger logger = LoggerFactory.getLogger(BitstampClientImpl.class);

    private String bitstampUrl;
    private String bitstampBalanceUrl;
    private ApiKeyGenerator apiKeyGenerator;
    private RestTemplate restTemplate;

    @Override
    public Ticker getActualMarket() {

        Ticker ticker = null;

        try {
            ticker = restTemplate.getForObject(bitstampUrl, Ticker.class);
        } catch (Exception e) {
            logger.error("Reason: {} : Can't connect to: " + bitstampUrl, e.getMessage(), e);
            new RuntimeException("Can't connect to: " + bitstampUrl, e);
        }
        return ticker;
    }

    @Override
    public ResponseEntity<AccountBalance> getAccountBalance() {

        ResponseEntity<AccountBalance> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(bitstampBalanceUrl, HttpMethod.POST, setUpBitstampHeaders(),
                    AccountBalance.class);
        } catch (Exception e) {
            logger.error("Reason: {} : Can't set bitstamp params: " + bitstampBalanceUrl, e.getMessage(), e);
            throw new RuntimeException("Can't set bitstamp params", e);
        }
        return responseEntity;

    }

    private HttpEntity<?> setUpBitstampHeaders() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<?> requestEntity = new HttpEntity<Object>(setUpBitstampParams(), headers);
        return requestEntity;
    }

    private MultiValueMap<String, String> setUpBitstampParams() throws Exception {
        MultiValueMap<String, String> bitstampParam = new LinkedMultiValueMap<>();

        String nonce = apiKeyGenerator.getNonce();
        bitstampParam.add("key", apiKeyGenerator.getApiKey());
        bitstampParam.add("signature", apiKeyGenerator.getApiSignature(nonce));
        bitstampParam.add("nonce", nonce);
        return bitstampParam;
    }

    public void setBitstampUrl(String bitstampUrl) {
        this.bitstampUrl = bitstampUrl;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setBitstampBalanceUrl(String bitstampBalanceUrl) {
        this.bitstampBalanceUrl = bitstampBalanceUrl;
    }

    public void setApiKeyGenerator(ApiKeyGenerator apiKeyGenerator) {
        this.apiKeyGenerator = apiKeyGenerator;
    }
}
