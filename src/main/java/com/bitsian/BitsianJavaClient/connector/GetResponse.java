package com.bitsian.BitsianJavaClient.connector;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.bitsian.BitsianJavaClient.constant.CommonConstants.*;

@Component
public class GetResponse {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public ResponseEntity<String> getResponse(String url, String path, HttpMethod method, String body) throws InvalidKeyException, NoSuchAlgorithmException, CloneNotSupportedException {
        long currentTime = System.currentTimeMillis();

        String signature = this.createSignature(currentTime, path, method, body);

        HttpHeaders headers = new HttpHeaders();

        headers.add("BITSIAN-API-KEY", API_KEY);

        headers.add("BITSIAN-TIMESTAMP", String.valueOf(currentTime));

        headers.add("BITSIAN-PASSPHRASE", PASSPHRASE);

        headers.add("BITSIAN-API-SIGN", signature);

        headers.add("Content-Type", "application/json");

        HttpURLConnection connection;

        HttpEntity httpEntity;

        RestTemplate restTemplate = new RestTemplate();

        headers.add("Content-Type", "application/json");

        if (body == null) {
            httpEntity = new HttpEntity(headers);
        } else {
            httpEntity = new HttpEntity(body, headers);
        }

        try {

            ResponseEntity<String> responseEntity = restTemplate.exchange(url + path, method, httpEntity, String.class);

            return responseEntity;

        } catch (HttpClientErrorException e) {
            logger.log(Level.SEVERE, "HttpClientErrorException - " + e.getMessage());
            return null;
        }
    }

    public String createSignature(long currentTime, String path, HttpMethod method, String body) throws NoSuchAlgorithmException, CloneNotSupportedException, InvalidKeyException {

        String toSign = String.valueOf(currentTime) + method + path + ((body == null ? "" : body));

        Mac sha = Mac.getInstance("HmacSHA256");

        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), sha.getAlgorithm());

        Mac sha256 = (Mac) sha.clone();
        sha256.init(keySpec);

        String signature = Base64.getEncoder().encodeToString(sha256.doFinal(toSign.getBytes(StandardCharsets.UTF_8)));

        return signature;
    }

}
