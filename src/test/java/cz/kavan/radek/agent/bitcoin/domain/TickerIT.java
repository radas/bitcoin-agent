package cz.kavan.radek.agent.bitcoin.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.test.annotation.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bitstamp-web-service-IT.xml" })
@Transactional
public class TickerIT {

    @Autowired
    private TickerDAO tickerDAO;

    @Test
    public void shouldHaveASessionFactory() {
        assertNotNull(tickerDAO);
    }

    @Test
    public void shouldHaveNoObjectsAtStart() {
        List<Ticker> results = tickerDAO.getTickers();
        assertTrue(results.isEmpty());
    }

    @Test
    public void shouldBeAbleToPersistAnObject() {
        assertEquals(0, tickerDAO.getTickers().size());

        Ticker ticker = new Ticker();
        ticker.setAsk(new BigDecimal("10.0"));
        ticker.setBid(new BigDecimal("5.0"));
        tickerDAO.addTicker(ticker);
        assertEquals(1, tickerDAO.getTickers().size());
    }
}