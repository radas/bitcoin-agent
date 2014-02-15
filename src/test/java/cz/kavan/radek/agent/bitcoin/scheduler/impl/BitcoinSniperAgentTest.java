package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;

public class BitcoinSniperAgentTest {

    @Mock
    private AccountBalanceEntity balance;

    @InjectMocks
    private BitcoinSniperAgent sniper;

    @Before
    public void setUp() throws Exception {
        sniper = new BitcoinSniperAgent();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isSellable() {

        when(balance.getBtc_available()).thenReturn(new BigDecimal("10.00"));
        when(balance.getUsd_available()).thenReturn(new BigDecimal("0.00"));
        assertTrue(sniper.isSellable());
    }

    @Test
    public void limitForSellingBtc() {

        when(balance.getBtc_available()).thenReturn(new BigDecimal("0.00"));
        when(balance.getUsd_available()).thenReturn(new BigDecimal("10.00"));
        assertFalse(sniper.isSellable());
    }

    @Test
    public void isNotSellable() {

        when(balance.getBtc_available()).thenReturn(new BigDecimal("0.00"));
        when(balance.getUsd_available()).thenReturn(new BigDecimal("10.00"));
        assertFalse(sniper.isSellable());
    }

}
