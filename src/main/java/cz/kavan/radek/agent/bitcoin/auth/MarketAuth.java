package cz.kavan.radek.agent.bitcoin.auth;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

public interface MarketAuth {

    HttpEntity<MultiValueMap<String, String>> setAuthRequest();

    HttpEntity<MultiValueMap<String, String>> setAuthRequestWithOffer(BigDecimal amount, BigDecimal price);

}
