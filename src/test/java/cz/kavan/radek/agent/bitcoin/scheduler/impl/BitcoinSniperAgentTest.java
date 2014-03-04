package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.EmaDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.RatingDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;
import cz.kavan.radek.agent.bitcoin.domain.entity.EmaEntity;
import cz.kavan.radek.agent.bitcoin.domain.entity.TickerEntity;

public class BitcoinSniperAgentTest {

    @Mock
    private AccountBalanceEntity balance;

    @Mock
    private AccountBalanceDAO balanceDAO;

    @Mock
    private RatingDAO ratingDAO;

    @Mock
    private EmaDAO emaDao;

    @Mock
    private TickerDAO tickerDAO;

    @Mock
    AccountBalance accountBalance;

    @InjectMocks
    private BitcoinSniperAgent sniper;

    private List<TickerEntity> tickerEntity;

    private EmaEntity emaEntity;

    @Before
    public void setUp() throws Exception {
        sniper = new BitcoinSniperAgent();
        sniper.setMoneyGain(new BigDecimal("10.00"));
        MockitoAnnotations.initMocks(this);

        when(ratingDAO.getRating()).thenReturn(new BigDecimal("800.00"));
        when(balance.getBtc_available()).thenReturn(new BigDecimal("0.5"));
        when(balance.getUsd_available()).thenReturn(new BigDecimal("500.00"));
        when(balance.getFee()).thenReturn(new BigDecimal("0.5"));

        emaEntity = new EmaEntity();
        emaEntity.setEmaBuy(new BigDecimal("800.00"));
        emaEntity.setEmaSell(new BigDecimal("800.00"));

        tickerEntity = new ArrayList<>();
        TickerEntity entity = new TickerEntity();
        entity.setAsk(new BigDecimal("800.00"));
        entity.setBid(new BigDecimal("800.00"));
        tickerEntity.add(entity);
    }

    @Test
    public void isSellable() {

        when(balance.getBtc_available()).thenReturn(new BigDecimal("0.5"));
        when(balance.getUsd_available()).thenReturn(new BigDecimal("350.00"));
        assertTrue(sniper.isSellable());
    }

    @Test
    public void isNotSellable() {

        when(balance.getBtc_available()).thenReturn(new BigDecimal("0.5"));
        when(balance.getUsd_available()).thenReturn(new BigDecimal("400.00"));
        assertFalse(sniper.isSellable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void isSellableThrowsException() {

        when(tickerDAO.getLastTickers()).thenReturn(tickerEntity);
        when(emaDao.getEma()).thenReturn(emaEntity);
        when(balance.getBtc_available()).thenReturn(new BigDecimal("0.04"));
        when(balance.getUsd_available()).thenReturn(new BigDecimal("5.0"));
        sniper.isSellable();
    }

    @Test
    public void limitForSellingBtc() {
        assertEquals(new BigDecimal("812.0250"), sniper.limitForSellingBtc());

    }

    @Test
    public void obtainBTCpossibleToBuy() {
        assertEquals(new BigDecimal("0.61"), sniper.obtainBTCpossibleToBuy(new BigDecimal("810.00")));
    }

    @Test
    public void limitForBuyingBtc() {
        assertEquals(new BigDecimal("787.51150"), sniper.limitForBuyingBtc());
    }

}
