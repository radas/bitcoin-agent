package cz.kavan.radek.agent.bitcoin.utils;

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

        when(apiKeyDAO.getApiAndSecretKey().getApiKey()).thenReturn("KEY");
        when(apiKeyDAO.getApiAndSecretKey().getApiSecret()).thenReturn("KEY");

        // when(apiKeyDAO.getApiAndSecretKey()).thenReturn(apiKeyEntity);

    }

    @Test
    public void getKey() {
        System.out.println(apiKeyGenerator.getKey());
    }

}
