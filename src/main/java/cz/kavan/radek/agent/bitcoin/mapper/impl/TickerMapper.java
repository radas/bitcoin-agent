package cz.kavan.radek.agent.bitcoin.mapper.impl;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;
import cz.kavan.radek.agent.bitcoin.utils.TimeUtil;

public class TickerMapper {

    private TickerMapper() {
    }

    public static void mapTickerResponse(Ticker ticker, TickerEntity target) {

        long timestamp = ticker.getTimestamp();

        target.setAsk(ticker.getAsk());
        target.setBid(ticker.getBid());
        target.setTimestamp(TimeUtil.convertTimestampLocalDateTime(timestamp));
    }

}
