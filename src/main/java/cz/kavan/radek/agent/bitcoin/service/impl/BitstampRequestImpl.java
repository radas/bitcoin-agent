package cz.kavan.radek.agent.bitcoin.service.impl;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

import cz.kavan.radek.agent.bitcoin.auth.MarketAuth;
import cz.kavan.radek.agent.bitcoin.service.MarketRequest;

public class BitstampRequestImpl implements MarketRequest {

    private MarketAuth bitstampAuth;

    @Override
    public HttpEntity<MultiValueMap<String, String>> createRequest() {
        return bitstampAuth.setAuthRequest();
    }

    @Override
    public HttpEntity<MultiValueMap<String, String>> createRequestWithOffer(BigDecimal amount, BigDecimal price) {
        return bitstampAuth.setAuthRequestWithOffer(amount, price);
    }

    public void setBitstampAuth(MarketAuth bitstampAuth) {
        this.bitstampAuth = bitstampAuth;
    }

}
