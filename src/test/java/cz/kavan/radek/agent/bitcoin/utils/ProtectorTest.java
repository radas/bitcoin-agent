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
        String decKey = Protector.decryptApiKey(Protector.encryptApiKey(apiKey));
        assertEquals("AafaTHKAD662662A", decKey);
    }

    @Test
    @Ignore
    public void generateRealApiKeyAndSecret() throws Exception {
        String decApiKey = Protector.encryptApiKey(Protector.encryptApiKey("DjOtCD65lL0SLHQE"));
        System.out.println(decApiKey);

        String decSecret = Protector.encryptApiKey(Protector.encryptApiKey("7cIKLRY3oBwwO0YKbTbM"));
        System.out.println(decSecret);
    }

}
