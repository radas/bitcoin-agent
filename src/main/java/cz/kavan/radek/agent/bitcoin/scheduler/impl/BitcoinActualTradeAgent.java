package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

public class BitcoinActualTradeAgent implements Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinActualTradeAgent.class);

    private BitstampClientImpl bitstamp;

    private BigDecimal bid; // nejvyšší nákupní nabídka - za tohle prodavam
    private BigDecimal ask; // Nejnižší prodejní nabídka - za tohle nakupuji

    @Override
    public void startAgent() {

        try {
            populateTradeInfo();
        } catch (Exception e) {
            logger.error("Something is wrong with: " + e);
        }

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
            logger.error("Can't get info about trade.");
        } else {
            logger.info("Status of trading. Sell: {} Buy: {}", bid, ask);
        }
    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

}
