package cz.kavan.radek.agent.bitcoin.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.auth.BitstampAuth;
import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.service.BitstampClient;
import cz.kavan.radek.agent.bitcoin.utils.ApiKeyGenerator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:/bitstamp-web-service-test.xml" })
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class BitstampClientTest {

    @Autowired
    private BitstampClient bitstamp;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BitstampAuth bitstampAuth;

    @ReplaceWithMock
    @Autowired
    private ApiKeyGenerator apiKeyGenerator;

    @Before
    public void setUp() throws Exception {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("HeaderKey", "HeaderData");

        AccountBalance account = new AccountBalance();
        account.setBtc_available(new BigDecimal("10.00"));
        account.setUsd_available(new BigDecimal("20.00"));

        ResponseEntity<AccountBalance> response = new ResponseEntity<AccountBalance>(account, responseHeaders,
                HttpStatus.CREATED);

        when(
                restTemplate.exchange("http://test", HttpMethod.POST, bitstampAuth.getHttpAuthEntity(),
                        AccountBalance.class)).thenReturn(response);

    }

    @Test
    public void accountBalanceIsNotNull() {
        assertNotNull(bitstamp);
    }

    @Test
    public void accountBalanceValues() {
        assertEquals(new BigDecimal("10.00"), bitstamp.getAccountBalance().getBody().getBtc_available());
        assertEquals(new BigDecimal("20.00"), bitstamp.getAccountBalance().getBody().getUsd_available());
    }

    @Test
    public void accountBalanceNullValues() {
        assertNull(bitstamp.getAccountBalance().getBody().getBtc_reserved());
    }
}
