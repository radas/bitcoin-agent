package cz.kavan.radek.agent.bitcoin.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.domain.Transaction;
import cz.kavan.radek.agent.bitcoin.errorhandling.resttemplate.BitstampClientException;
import cz.kavan.radek.agent.bitcoin.service.MarketClient;
import cz.kavan.radek.agent.bitcoin.service.MarketRequest;

public class BitstampClientImpl implements MarketClient {

    private static final Logger logger = LoggerFactory.getLogger(BitstampClientImpl.class);

    private String bitstampUrl;
    private String bitstampBalanceUrl;
    private String bitstampBuyUrl;
    private String bitstampSellUrl;
    private RestTemplate restTemplate;
    private MarketRequest bitstampRequest;

    @Override
    public Ticker getActualMarket() {

        Ticker ticker = null;

        try {
            ticker = restTemplate.getForObject(bitstampUrl, Ticker.class);
        } catch (Exception e) {
            throw new BitstampClientException("Can't get object from: " + bitstampUrl, e);
        }
        return ticker;
    }

    @Override
    public ResponseEntity<AccountBalance> getAccountBalance() {

        ResponseEntity<AccountBalance> responseEntity = null;

        try {
            responseEntity = restTemplate.exchange(bitstampBalanceUrl, HttpMethod.POST,
                    bitstampRequest.createRequest(), AccountBalance.class);
        } catch (Exception e) {
            throw new BitstampClientException("Can't get object from: " + bitstampBalanceUrl, e);
        }
        return responseEntity;

    }

    @Override
    public void buyBTC(BigDecimal amount, BigDecimal price) {

        try {
            sendRequest(amount, price, bitstampBuyUrl);
        } catch (Exception e) {
            throw new BitstampClientException("Can't buy BTC from URL: " + bitstampBuyUrl, e);
        }

    }

    @Override
    public void sellBTC(BigDecimal amount, BigDecimal price) {
        try {
            sendRequest(amount, price, bitstampSellUrl);
        } catch (Exception e) {
            throw new BitstampClientException("Can't sell BTC from URL: " + bitstampSellUrl, e);
        }
    }

    private void sendRequest(BigDecimal amount, BigDecimal price, String url) {
        ResponseEntity<Transaction> responseEntity = null;

        responseEntity = restTemplate.exchange(url, HttpMethod.POST,
                bitstampRequest.createRequestWithOffer(amount, price), Transaction.class);
        checkIfErrorOccurs(amount, price, responseEntity);
    }

    private void checkIfErrorOccurs(BigDecimal amount, BigDecimal price, ResponseEntity<Transaction> responseEntity) {

        try {
            if (StringUtils.hasText(responseEntity.getBody().getError().toString())) {
                logger.error("Error response: {}", responseEntity.getBody().getError());
                logger.error("Amount : {} Price : {} Url: {}", amount, price);
                throw new BitstampClientException("Can't buy or sell btc.");
            }
        } catch (Exception e) {
            return;
        }

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

    public void setBitstampBuyUrl(String bitstampBuyUrl) {
        this.bitstampBuyUrl = bitstampBuyUrl;
    }

    public void setBitstampSellUrl(String bitstampSellUrl) {
        this.bitstampSellUrl = bitstampSellUrl;
    }

    public void setBitstampRequest(MarketRequest bitstampRequest) {
        this.bitstampRequest = bitstampRequest;
    }

}
