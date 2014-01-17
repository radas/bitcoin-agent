package cz.kavan.radek.agent.bitcoin.bitstamp;

import cz.kavan.radek.agent.bitcoin.domain.bitstamp.Ticker;

public interface BitstampClient {

	Ticker getActualMarket();

}
