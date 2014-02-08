package cz.kavan.radek.agent.bitcoin.service;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;

public interface BitstampClient {

    Ticker getActualMarket();

    void getAccountBalance();

}
