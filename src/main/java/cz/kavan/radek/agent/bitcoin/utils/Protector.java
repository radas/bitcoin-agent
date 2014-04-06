package cz.kavan.radek.agent.bitcoin.utils;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class Protector {

    private static final String ALGORITHM = "AES";
    private static final byte ITERATIONS = 2;
    private static final String salt = "Tx24@!P";

    private Protector() {
    }

    public static String encryptApiKey(String apiKey) throws Exception {
        Key secretKey = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        String encKey = null;
        String eValue = null;
        for (int i = 0; i < ITERATIONS; i++) {
            encKey = salt + apiKey;
            byte[] encValue = cipher.doFinal(encKey.getBytes());
            eValue = DatatypeConverter.printBase64Binary(encValue);
        }
        return eValue;

    }

    public static String decryptApiKey(String apiKey) throws Exception {
        Key secretKey = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        String decryptedKey = null;
        for (int i = 0; i < ITERATIONS; i++) {
            byte[] decordedValue = DatatypeConverter.parseBase64Binary(apiKey);
            byte[] decValue = cipher.doFinal(decordedValue);
            decryptedKey = new String(decValue).substring(salt.length());
        }
        return decryptedKey;

    }

    private static Key generateKey() throws NoSuchAlgorithmException {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        byte[] key = sha.digest(getKeyValue());
        return new SecretKeySpec(Arrays.copyOf(key, 16), ALGORITHM);
    }

    private static byte[] getKeyValue() {
        return new byte[] { '@', '2', '$', 'V', 'X', '2', '$' };
    }
}
