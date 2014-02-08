package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.kavan.radek.agent.bitcoin.scheduler.Agent;
import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

public class BitcoinSniperAgent implements Agent {

    private static final Logger logger = LoggerFactory.getLogger(BitcoinSniperAgent.class);

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
        bitstamp.getAccountBalance();

        // if (accoutBalance == null) {
        // throw new RuntimeException("Accout Balance is null!");
        //
        // }
        // logger.debug("My accout balance. BTC: {} USD: {}",
        // accoutBalance.getBtc_available(),
        // accoutBalance.getUsd_available());

    }

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }

}
