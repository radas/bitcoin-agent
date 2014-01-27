package cz.kavan.radek.agent.bitcoin.domain.dao;

import java.util.List;

import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;

public interface TickerDAO {

    public void addTicker(TickerEntity ticker);

    public List<TickerEntity> getTickers();

}
