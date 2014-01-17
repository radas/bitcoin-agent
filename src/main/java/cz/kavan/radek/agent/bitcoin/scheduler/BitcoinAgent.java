package cz.kavan.radek.agent.bitcoin.scheduler;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.bitstamp.impl.BitstampClientImpl;

public class BitcoinAgent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinAgent.class);

    private BitstampClientImpl bitstamp;

    private BigDecimal bid; // nejvyšší nákupní nabídka - za tohle prodavam
    private BigDecimal ask; // Nejnižší prodejní nabídka - za tohle nakupuji

    public void startScheduler() {
        populateTradeInfo();

    }

    private void populateTradeInfo() {
        initBidAskValues();
        writeTradeInfo();
    }

    private void initBidAskValues() {
        if (bitstamp.getActualMarket() != null) {
            bid = bitstamp.getActualMarket().getBid();
            ask = bitstamp.getActualMarket().getAsk();
        }
    }

    private void writeTradeInfo() {
        if (bid == null | ask == null) {
            logger.error("Nemam info o trhu");
        } else {
            logger.info("Stav trhu. Prodej: {} Nakup: {}", ask, bid);
        }
    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

}
