package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

public class BitcoinSniper implements Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinSniper.class);

    private BitstampClientImpl bitstamp;

    @Override
    public void startAgent() {
        try {
            populateTradeSniper();
        } catch (Exception e) {
            logger.error("Something is wrong with: " + e);
        }

    }

    private void populateTradeSniper() {
        // TODO Auto-generated method stub

    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

}
