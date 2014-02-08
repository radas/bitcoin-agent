package cz.kavan.radek.agent.bitcoin.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import cz.kavan.radek.agent.bitcoin.domain.Ticker;
import cz.kavan.radek.agent.bitcoin.service.BitstampClient;
import cz.kavan.radek.agent.bitcoin.utils.ApiKeyGenerator;

public class CopyOfBitstampClientImpl implements BitstampClient {

    private static final Logger logger = LoggerFactory.getLogger(CopyOfBitstampClientImpl.class);

    private String bitstampUrl;
    private String bitstampBalanceUrl;
    private ApiKeyGenerator apiKeyGenerator;
    private RestTemplate restTemplate;

    @Override
    public Ticker getActualMarket() {

        Ticker ticker = null;

        try {
            ticker = restTemplate.getForObject(bitstampUrl, Ticker.class);
        } catch (Exception e) {
            logger.error("Reason: {} : Can't connect to: " + bitstampUrl, e.getMessage(), e);
            new RuntimeException("Can't connect to: " + bitstampUrl, e);
        }
        return ticker;
    }

    @Override
    public void getAccountBalance() {

        try {
            String nonce = apiKeyGenerator.getNonce();
            String query = "key=" + apiKeyGenerator.getApiKey();
            query += "&";
            query += "nonce=" + nonce;
            query += "&";
            query += "signature=" + apiKeyGenerator.getApiSignature(nonce);

            URL obj = new URL(bitstampBalanceUrl);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            String USER_AGENT = "Mozilla/5.0";
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(query);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + bitstampBalanceUrl);
            System.out.println("Post parameters : " + query);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());

        } catch (Exception e) {
            throw new RuntimeException("Can't set bitstamp params", e);
        }

        // try {
        //
        // accountBalance = restTemplate.exchange(bitstampBalanceUrl,
        // HttpMethod.POST, requestEntity,
        // AccountBalance.class);
        // } catch (Exception e) {
        // logger.error("Reason: {} : Can't connect to: " + bitstampBalanceUrl,
        // e.getMessage(), e);
        // new RuntimeException("Can't connect to: " + bitstampUrl, e);
        // }
    }

    private MultiValueMap<String, String> setUpBitstampParams() throws Exception {
        MultiValueMap<String, String> bitstampParam = new LinkedMultiValueMap<>();
        String nonce = apiKeyGenerator.getNonce();
        bitstampParam.add("key", apiKeyGenerator.getApiKey());
        bitstampParam.add("signature", apiKeyGenerator.getApiSignature(nonce));
        bitstampParam.add("nonce", nonce);
        return bitstampParam;
    }

    public void setBitstampUrl(String bitstampUrl) {
        this.bitstampUrl = bitstampUrl;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setBitstampBalanceUrl(String bitstampBalanceUrl) {
        this.bitstampBalanceUrl = bitstampBalanceUrl;
    }

    public void setApiKeyGenerator(ApiKeyGenerator apiKeyGenerator) {
        this.apiKeyGenerator = apiKeyGenerator;
    }
}
