package cz.kavan.radek.agent.bitcoin.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;
import cz.kavan.radek.agent.bitcoin.utils.TimeUtil;
import cz.kavan.radek.test.annotation.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bitstamp-web-service-IT.xml" })
@Transactional
public class TickerEntityIT {

    @Autowired
    private TickerDAO tickerDAO;

    private TickerEntity ticker;

    @Before
    public void setUp() throws Exception {
        ticker = new TickerEntity();
    }

    @Test
    public void shouldHaveASessionFactory() {
        assertNotNull(tickerDAO);
    }

    @Test
    public void shouldHaveNoObjectsAtStart() {
        List<TickerEntity> results = tickerDAO.getTickers();
        assertTrue(results.isEmpty());
    }

    @Test
    public void shouldBeAbleToPersistAnObject() throws ParseException {

        assertEquals(0, tickerDAO.getTickers().size());

        ticker.setAsk(new BigDecimal("10.0"));
        ticker.setBid(new BigDecimal("5.0"));
        ticker.setTimestamp(new DateTime());
        DateTime tradeTime = TimeUtil.convertTimestampLocalDateTime(1390671934L);
        ticker.setTimestamp(tradeTime);
        tickerDAO.addTicker(ticker);

        assertEquals(1, tickerDAO.getTickers().size());
        assertEquals(19, tradeTime.getHourOfDay());
        assertEquals(45, tradeTime.getMinuteOfHour());
        assertEquals(34, tradeTime.getSecondOfMinute());
    }
}
