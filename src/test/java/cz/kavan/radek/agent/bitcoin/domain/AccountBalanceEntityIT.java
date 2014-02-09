package cz.kavan.radek.agent.bitcoin.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.AccountBalanceDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;
import cz.kavan.radek.test.annotation.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bitstamp-web-service-IT.xml" })
@Transactional
public class AccountBalanceEntityIT {

    @Autowired
    private AccountBalanceDAO balanceDAO;

    private AccountBalanceEntity balance;

    @Before
    public void setUp() throws Exception {
        balance = new AccountBalanceEntity();
    }

    @Test
    public void shouldHaveASessionFactory() {
        assertNotNull(balanceDAO);
    }

    @Test
    public void shouldHaveNoObjectsAtStart() {
        List<AccountBalanceEntity> results = balanceDAO.getBalance();
        assertTrue(results.isEmpty());
    }

    @Test
    public void shouldBeAbleToPersistAnObject() {

        assertEquals(0, balanceDAO.getBalance().size());

        balance.setBtc_available(new BigDecimal("1.0"));
        balance.setUsd_available(new BigDecimal("50.0"));
        balanceDAO.addAccountBalance(balance);

        assertEquals(1, balanceDAO.getBalance().size());
        assertEquals(new BigDecimal("1.0"), balanceDAO.getBalance().get(0).getBtc_available());
        assertEquals(new BigDecimal("50.0"), balanceDAO.getBalance().get(0).getUsd_available());
    }
}
