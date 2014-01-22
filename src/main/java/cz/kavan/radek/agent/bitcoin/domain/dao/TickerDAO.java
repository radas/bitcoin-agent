package cz.kavan.radek.agent.bitcoin.domain.dao;

import java.util.List;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;

public interface TickerDAO {

    public void addTicker(Ticker ticker);

    public List<Ticker> getTickers();

}
