package cz.kavan.radek.agent.bitcoin.service;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

public interface MarketRequest {

    HttpEntity<MultiValueMap<String, String>> createRequest();

    HttpEntity<MultiValueMap<String, String>> createRequestWithOffer(BigDecimal amount, BigDecimal price);

}
