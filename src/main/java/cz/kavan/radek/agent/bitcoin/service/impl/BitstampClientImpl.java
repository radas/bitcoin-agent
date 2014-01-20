package cz.kavan.radek.agent.bitcoin.service.impl;

import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.service.BitstampClient;

public class BitstampClientImpl implements BitstampClient {

    private String bitstampUrl;
    private RestTemplate restTemplate;

    @Override
    public Ticker getActualMarket() {

        Ticker ticker = null;

        try {
            ticker = restTemplate.getForObject(bitstampUrl, Ticker.class);
        } catch (Exception e) {
            new RuntimeException("Can't connect to: " + bitstampUrl, e);
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
