package com.bitsian.BitsianJavaClient.balance;

import com.bitsian.BitsianJavaClient.connector.GetResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.bitsian.BitsianJavaClient.constant.CommonConstants.BALANCE_ENDPOINT;
import static com.bitsian.BitsianJavaClient.constant.CommonConstants.REST_END_POINT;

@Service
public class BalanceService {

    @Autowired
    private GetResponse getResponse;

    private Gson gson = new Gson();

    @Autowired
    public BalanceService(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

    /**
     * get balance
     *
     * @param * @return get balance for the user
     */

    public BalanceResponse getBalance(Integer exchangeId, Integer currencyId) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {

        String path = getPath(BALANCE_ENDPOINT, exchangeId, currencyId);

        ResponseEntity<String> balanceResponse = this.getResponse.getResponse(REST_END_POINT,
                path, HttpMethod.GET, null);

        JsonObject data = this.gson.fromJson(balanceResponse.getBody(), JsonObject.class);
        return this.gson.fromJson(data, BalanceResponse.class);
    }

    private String getPath(String balanceEndpoint, Integer exchangeId, Integer currencyId) {

        String path;

        if (exchangeId == null && currencyId == null)
            return balanceEndpoint;

        else if (exchangeId != null && currencyId == null)
            return String.format("%s?exchangeId=%s",balanceEndpoint, exchangeId);

        else if (exchangeId == null)
            return String.format("%s?currencyId=%s",balanceEndpoint, currencyId);

        else
            return String.format("%s?exchangeId=%s&currencyId=%s",balanceEndpoint, exchangeId, currencyId);
    }
}
