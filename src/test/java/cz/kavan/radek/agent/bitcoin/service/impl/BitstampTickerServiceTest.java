package cz.kavan.radek.agent.bitcoin.service.impl;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.domain.bitstamp.Ticker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:/bitstamp-ticker-service-test.xml" })
public class BitstampTickerServiceTest {

	@Autowired
	private RestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		// http://pfelitti87.blogspot.no/2012/07/rest-services-with-spring-3-xml-json.html
	}

	@Test
	public void restJsonClientTest() {

		Ticker ticker = restTemplate.getForObject(
				"https://www.bitstamp.net/api/ticker/", Ticker.class);

		assertNotNull(ticker);
	}

}
