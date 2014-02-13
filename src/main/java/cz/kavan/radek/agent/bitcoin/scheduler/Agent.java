package cz.kavan.radek.agent.bitcoin.scheduler;

import cz.kavan.radek.agent.bitcoin.service.impl.BitstampClientImpl;

public abstract class Agent {

    protected BitstampClientImpl bitstamp;

    public abstract void startAgent();

    public void setBitstamp(BitstampClientImpl bitstamp) {
        this.bitstamp = bitstamp;
    }
}
