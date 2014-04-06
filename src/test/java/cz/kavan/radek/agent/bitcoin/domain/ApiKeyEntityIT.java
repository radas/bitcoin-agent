package cz.kavan.radek.agent.bitcoin.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cz.kavan.radek.agent.bitcoin.domain.dao.ApiKeyDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.ApiKeyEntity;
import cz.kavan.radek.test.annotation.IntegrationTest;

@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/bitstamp-services-IT.xml" })
@Transactional
public class ApiKeyEntityIT {

    @Autowired
    private ApiKeyDAO apiKeyDAO;

    private ApiKeyEntity apiKeyEntity;

    @Before
    public void setUp() throws Exception {
        apiKeyEntity = new ApiKeyEntity();
    }

    @Test
    public void shouldHaveASessionFactory() {
        assertNotNull(apiKeyDAO);
    }

    @Test
    public void shouldBeAbleToPersistAnObject() throws ParseException {

        apiKeyEntity.setApiKey("API KEY");
        apiKeyEntity.setApiSecret("API SECRET");
        apiKeyEntity.setApiClientId("11551");
        apiKeyDAO.addApiKey(apiKeyEntity);

        ApiKeyEntity results = apiKeyDAO.getApiAndSecretKey();
        assertEquals("API KEY", results.getApiKey());
        assertEquals("API SECRET", results.getApiSecret());
        assertEquals("11551", results.getApiClientId());

    }
}
