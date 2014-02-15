package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.RatingDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;

public class BitcoinSniperAgentTest {

    @Mock
    private AccountBalanceEntity balance;

    @Mock
    private AccountBalanceDAO balanceDAO;

    @Mock
    private RatingDAO ratingDAO;

    @Mock
    AccountBalance accountBalance;

    @InjectMocks
    private BitcoinSniperAgent sniper;

    @Before
    public void setUp() throws Exception {
        sniper = new BitcoinSniperAgent();
        sniper.setMoneyGain(new BigDecimal("10.00"));
        MockitoAnnotations.initMocks(this);

        when(ratingDAO.getRating()).thenReturn(new BigDecimal("800.00"));
        when(balance.getBtc_available()).thenReturn(new BigDecimal("0.5"));
        when(balance.getUsd_available()).thenReturn(new BigDecimal("500.00"));
        when(balance.getFee()).thenReturn(new BigDecimal("0.5"));
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
