package cz.kavan.radek.agent.bitcoin.service.impl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.test.annotation.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bitstamp-web-service-IT.xml" })
public class BitstampClientImplIT {

    @Autowired
    private BitstampClientImpl bitstamp;

    private Ticker ticker;

    @Before
    public void setUp() throws Exception {
        ticker = bitstamp.getActualMarket();
    }

    @Test
    public void getActualMarketIT() {
        assertNotNull(ticker);
        assertThat(ticker.getAsk(), greaterThan(new BigDecimal("100")));
        assertThat(ticker.getBid(), greaterThan(new BigDecimal("100")));
        assertThat(ticker.getTimestamp(), greaterThan(1390654556L));

    }

}
