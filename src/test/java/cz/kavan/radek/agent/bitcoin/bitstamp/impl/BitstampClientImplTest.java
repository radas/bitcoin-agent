package cz.kavan.radek.agent.bitcoin.bitstamp.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.kavan.radek.agent.bitcoin.domain.bitstamp.Ticker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:/bitstamp-ticker-service-test.xml" })
public class BitstampClientImplTest {

    @Autowired
    private BitstampClientImpl bitstamp;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getActualMarketTest() {

        Ticker ticker = bitstamp.getActualMarket();

        assertNotNull(ticker);
        assertNotNull(ticker.getAsk());
        assertNotNull(ticker.getBid());
    }

}
