package cz.kavan.radek.agent.bitcoin.service;

import org.springframework.http.ResponseEntity;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.Ticker;

public interface BitstampClient {

    Ticker getActualMarket();

    ResponseEntity<AccountBalance> getAccountBalance();

}
