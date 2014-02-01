package cz.kavan.radek.agent.bitcoin.service.impl;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.service.BitstampClient;
import cz.kavan.radek.agent.bitcoin.utils.ApiKeyGenerator;

public class BitstampClientImpl implements BitstampClient {

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
            new RuntimeException("Can't connect to: " + bitstampUrl, e);
        }
        return ticker;
    }

    @Override
    public AccountBalance getAccountBalance() {
        AccountBalance accountBalance = null;
        MultiValueMap<String, String> bitstampParam = null;

        try {
            bitstampParam = setUpBitstampParams();
        } catch (Exception e) {
            throw new RuntimeException("Can't set bitstamp params", e);
        }

        try {
            accountBalance = restTemplate.postForObject(bitstampBalanceUrl, bitstampParam, AccountBalance.class);
        } catch (Exception e) {
            new RuntimeException("Can't connect to: " + bitstampUrl, e);
        }
        return accountBalance;
    }

    private MultiValueMap<String, String> setUpBitstampParams() throws Exception {
        MultiValueMap<String, String> bitstampParam;
        String nonce = apiKeyGenerator.getNonce();
        bitstampParam = new LinkedMultiValueMap<String, String>();
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
