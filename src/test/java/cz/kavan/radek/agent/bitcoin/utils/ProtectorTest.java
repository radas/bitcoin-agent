package cz.kavan.radek.agent.bitcoin.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ProtectorTest {

    private String apiKey;

    @Before
    public void setUp() throws Exception {
        apiKey = "AafaTHKAD662662A";
    }

    @Test
    public void encryptApiKey() throws Exception {
        String encKey = Protector.encryptApiKey(apiKey);
        assertEquals("yNaTz3OdCm2Rh", encKey.substring(0, 13));
    }

    @Test
    public void decryptApiKey() throws Exception {
        String encKey = Protector.encryptApiKey(apiKey);
        String decKey = Protector.decryptApiKey(encKey);

        assertEquals(apiKey, decKey);
    }

    @Test
    @Ignore
    /**
     * Its used for generating encrypt api key and secret.
     * @throws Exception
     */
    public void generateRealApiKeyAndSecret() throws Exception {
        String decApiKey = Protector.encryptApiKey("PXSDIOeLxvM");
        System.out.println(decApiKey);

        String decSecret = Protector.encryptApiKey("6nfUuCxnfbPgiiu7");
        System.out.println(decSecret);
    }

}
