package cz.kavan.radek.agent.bitcoin.service;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.Ticker;

public interface MarketClient {

    Ticker getActualMarket();

    ResponseEntity<AccountBalance> getAccountBalance();

    void sellBTC(BigDecimal amount, BigDecimal price);

    void buyBTC(BigDecimal amount, BigDecimal price);

}
