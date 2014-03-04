package cz.kavan.radek.agent.bitcoin.domain.dao;

import cz.kavan.radek.agent.bitcoin.domain.entity.EmaEntity;

public interface EmaDAO {

    void addEma(EmaEntity ema);

    EmaEntity getEma();

}