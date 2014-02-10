package cz.kavan.radek.agent.bitcoin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.auth.BitstampAuth;
import cz.kavan.radek.agent.bitcoin.auth.impl.BitstampAuthImpl;
import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.service.BitstampClient;

public class BitstampClientImpl implements BitstampClient {

    private static final Logger logger = LoggerFactory.getLogger(BitstampClientImpl.class);

    private String bitstampUrl;
    private String bitstampBalanceUrl;
    private RestTemplate restTemplate;
    private BitstampAuth bitstampAuth;

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
            responseEntity = restTemplate.exchange(bitstampBalanceUrl, HttpMethod.POST,
                    bitstampAuth.getHttpAuthEntity(), AccountBalance.class);
        } catch (Exception e) {
            logger.error("Reason: {} : Can't set bitstamp params: " + bitstampBalanceUrl, e.getMessage(), e);
            throw new RuntimeException("Can't set bitstamp params", e);
        }
        return responseEntity;

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

    public void setBitstampAuth(BitstampAuthImpl bitstampAuth) {
        this.bitstampAuth = bitstampAuth;
    }

}
