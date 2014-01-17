package cz.kavan.radek.agent.bitcoin.bitstamp.impl;

import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.bitstamp.BitstampClient;
import cz.kavan.radek.agent.bitcoin.domain.bitstamp.Ticker;

public class BitstampClientImpl implements BitstampClient {

    private String bitstampUrl;
    private RestTemplate restTemplate;

    @Override
    public Ticker getActualMarket() {

        Ticker ticker;

        try {
            ticker = restTemplate.getForObject(bitstampUrl, Ticker.class);
        } catch (Exception e) {
            return null;
        }
        return ticker;
    }

    public void setBitstampUrl(String bitstampUrl) {
        this.bitstampUrl = bitstampUrl;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

}
