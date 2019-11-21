package com.bitsian.BitsianJavaClient.utils;

import com.bitsian.BitsianJavaClient.constant.CommonConstants;
import com.bitsian.BitsianJavaClient.websocket.MySessionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class CommonUtils {

    private Logger log = LoggerFactory.getLogger(MySessionHandler.class);

    public static String generateSignature(String nonce, String method, String path, String body) {

        try {

            Mac hmac = Mac.getInstance(CommonConstants.HASH_ALGORITHM);

            SecretKeySpec keySpec = new SecretKeySpec(CommonConstants.SECRET_KEY.getBytes(StandardCharsets.UTF_8), hmac.getAlgorithm());

            hmac.init(keySpec);

            String preHash = nonce + method + path + body;

            return Base64.getEncoder().encodeToString(hmac.doFinal(preHash.getBytes(StandardCharsets.UTF_8)));

        } catch (Exception e) {

            e.printStackTrace();

            return "";
        }
    }
}
