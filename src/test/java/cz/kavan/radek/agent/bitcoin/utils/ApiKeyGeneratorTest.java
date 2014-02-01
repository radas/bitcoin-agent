package cz.kavan.radek.agent.bitcoin.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cz.kavan.radek.agent.bitcoin.domain.dao.ApiKeyDAO;
import cz.kavan.radek.agent.bitcoin.domain.entity.ApiKeyEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = { "classpath:/bitstamp-web-service-test.xml" })
public class ApiKeyGeneratorTest {

    @Autowired
    private ApiKeyGenerator apiKeyGenerator;

    @Autowired
    private ApiKeyDAO apiKeyDAO;

    @Before
    public void setUp() throws Exception {
        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setApiKey("API KEY");
        apiKeyEntity.setApiSecret("API SECRET");
        apiKeyEntity.setApiClientId("156447");

        when(apiKeyDAO.getApiAndSecretKey()).thenReturn(apiKeyEntity);

    }

    @Test
    public void getApiKey() {
        assertEquals("API KEY", apiKeyGenerator.getApiKey());
    }

    @Test
    public void getApiClientId() {
        assertEquals("156447", apiKeyGenerator.getClientApiId());
    }

    @Test
    public void getNonce() {
        assertEquals(String.valueOf(TimeUtil.getTimestamp()).substring(0, 10),
                apiKeyGenerator.getNonce().substring(0, 10));
    }

    @Test
    public void getApiSignature() throws Exception {
        final String expectedSignature = "E913DE76EAA94B7DED740B8D0FA2AD6DCE5C244CD24ECA249556E371A2B157E7";
        assertEquals(expectedSignature, apiKeyGenerator.getApiSignature("1391245019"));
    }
}
