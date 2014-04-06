package cz.kavan.radek.agent.bitcoin.scheduler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.EmaDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.RatingDAO;
import cz.kavan.radek.agent.bitcoin.domain.dao.TickerDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;
import cz.kavan.radek.agent.bitcoin.domain.entity.EmaEntity;
import cz.kavan.radek.agent.bitcoin.domain.entity.RatingEntity;
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
    private Ticker ticker;

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
        when(balance.getBtcAvailable()).thenReturn(new BigDecimal("0.5"));
        when(balance.getUsdAvailable()).thenReturn(new BigDecimal("312.18"));
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

        when(balance.getBtcAvailable()).thenReturn(new BigDecimal("0.5"));
        when(balance.getUsdAvailable()).thenReturn(new BigDecimal("350.00"));
        assertTrue(sniper.isSellable());
    }

    @Test
    public void isNotSellable() {

        when(balance.getBtcAvailable()).thenReturn(new BigDecimal("0.5"));
        when(balance.getUsdAvailable()).thenReturn(new BigDecimal("400.00"));
        assertFalse(sniper.isSellable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void isSellableThrowsException() {

        when(tickerDAO.getLastTickers()).thenReturn(tickerEntity);
        when(emaDao.getEma()).thenReturn(emaEntity);
        when(balance.getBtcAvailable()).thenReturn(new BigDecimal("0.04"));
        when(balance.getUsdAvailable()).thenReturn(new BigDecimal("5.0"));
        sniper.isSellable();
    }

    @Test
    public void limitForSellingBtc() {
        assertEquals(new BigDecimal("812.0250"), sniper.limitForSellingBtc());

    }

    @Test
    public void obtainBTCpossibleToBuy() {
        assertEquals(new BigDecimal("0.38"), sniper.obtainBTCpossibleToBuy(new BigDecimal("810.00")));
    }

    @Test
    public void limitForBuyingBtc() {
        assertEquals(new BigDecimal("788.45950"), sniper.limitForBuyingBtc());
    }

    @Test
    public void populateSellingPossibleSellingButBadTrandOfMarket() {
        // my last rate
        when(ratingDAO.getRating()).thenReturn(new BigDecimal("800.00"));

        // sell price
        when(ticker.getBid()).thenReturn(new BigDecimal("818.0"));

        // trend
        emaEntity.setEmaSell(new BigDecimal("817.00"));

        when(emaDao.getEma()).thenReturn(emaEntity);
        sniper.populateSelling();

        verify(ticker, times(1)).getBid();
        verify(ratingDAO, never()).updateRating(any(RatingEntity.class));
    }

    @Test
    @Ignore
    public void populateSelling() {
        // my last rate
        when(ratingDAO.getRating()).thenReturn(new BigDecimal("800.00"));

        // sell price
        when(ticker.getBid()).thenReturn(new BigDecimal("815.0"));

        // trend
        emaEntity.setEmaSell(new BigDecimal("817.00"));

        when(emaDao.getEma()).thenReturn(emaEntity);
        sniper.populateSelling();

        verify(ticker, times(1)).getBid();
        verify(ratingDAO, times(1)).updateRating(any(RatingEntity.class));
    }

    @Test
    public void populateBuyingPossibleBuyingButBadTrandOfMarket() {
        // my last rate
        when(ratingDAO.getRating()).thenReturn(new BigDecimal("800.00"));

        // sell price
        when(ticker.getAsk()).thenReturn(new BigDecimal("780"));

        // trend
        emaEntity.setEmaBuy(new BigDecimal("785.00"));

        when(emaDao.getEma()).thenReturn(emaEntity);
        sniper.populateBuying();

        verify(ticker, times(1)).getAsk();
        verify(ratingDAO, never()).updateRating(any(RatingEntity.class));
    }

    @Test
    @Ignore
    public void populateBuying() {
        // my last rate
        when(ratingDAO.getRating()).thenReturn(new BigDecimal("800.00"));

        // sell price
        when(ticker.getAsk()).thenReturn(new BigDecimal("780"));

        // trend
        emaEntity.setEmaBuy(new BigDecimal("770.00"));

        when(emaDao.getEma()).thenReturn(emaEntity);
        sniper.populateBuying();

        verify(ticker, times(1)).getAsk();
        verify(ratingDAO, times(1)).updateRating(any(RatingEntity.class));
    }

}
