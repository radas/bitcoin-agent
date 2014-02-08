package cz.kavan.radek.agent.bitcoin.domain.dao;

import java.util.List;

import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;

public interface TickerDAO {

    void addTicker(TickerEntity ticker);

    List<TickerEntity> getTickers();

}
