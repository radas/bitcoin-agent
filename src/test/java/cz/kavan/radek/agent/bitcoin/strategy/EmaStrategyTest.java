package cz.kavan.radek.agent.bitcoin.strategy;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;

public class EmaStrategyTest {

    private List<TickerEntity> lastPrices;
    private final BigDecimal previousEma = new BigDecimal("500.50");

    @Before
    public void setUp() throws Exception {
        lastPrices = new ArrayList<TickerEntity>();

        for (int i = 500; i < 506; i++) {
            TickerEntity entity = new TickerEntity();
            entity.setAsk(new BigDecimal(i));
            entity.setBid(new BigDecimal(i + 1));
            lastPrices.add(entity);
        }

    }

    @Test
    public void testCountEmaIndexex() {
        double lastHourItems = 6.00;
        assertEquals(EmaStrategy.ACTUAL_INDEX, (2 / (lastHourItems + 1)), 5);
        assertEquals(EmaStrategy.PREVIOUS_INDEX, 1 - EmaStrategy.ACTUAL_INDEX, 5);
    }

    @Test
    public void testComputeEmaIndexForSelling() {
        BigDecimal index = EmaStrategy.computeEmaIndex(true, lastPrices, previousEma);
        assertEquals(new BigDecimal("501.07"), index);
    }

    @Test
    public void testComputeEmaIndexForBuying() {
        BigDecimal index = EmaStrategy.computeEmaIndex(false, lastPrices, previousEma);
        assertEquals(new BigDecimal("501.36"), index);
    }
}
