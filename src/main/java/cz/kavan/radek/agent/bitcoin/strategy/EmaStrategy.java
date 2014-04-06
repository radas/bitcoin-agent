package cz.kavan.radek.agent.bitcoin.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;

public class EmaStrategy {

    // http://www.iexplain.org/ema-how-to-calculate/
    // http://www.forextradingzone.org/fx-EMA
    static final double ACTUAL_INDEX = 0.28571;
    static final double PREVIOUS_INDEX = 0.71429;

    private EmaStrategy() {
    }

    public static BigDecimal computeEmaIndex(boolean sell, List<TickerEntity> lastPrices, BigDecimal previousEma) {

        BigDecimal actualPrices = countAvg(sell, lastPrices);

        BigDecimal actualPriceIndex = actualPrices.multiply(new BigDecimal(ACTUAL_INDEX));
        BigDecimal previousPriceIndex = previousEma.multiply(new BigDecimal(PREVIOUS_INDEX));

        return actualPriceIndex.add(previousPriceIndex).setScale(2, RoundingMode.HALF_UP);

    }

    private static BigDecimal countAvg(boolean sell, List<TickerEntity> lastPrices) {
        int bg = 0;

        for (TickerEntity tickerEntity : lastPrices) {

            if (sell) {
                bg += tickerEntity.getBid().intValue();

            } else {
                bg += tickerEntity.getAsk().intValue();
            }

        }

        return new BigDecimal(bg).divide(new BigDecimal(lastPrices.size()), 2, RoundingMode.FLOOR);

    }
}
