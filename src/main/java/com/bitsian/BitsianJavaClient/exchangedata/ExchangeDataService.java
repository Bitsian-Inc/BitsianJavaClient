package com.bitsian.BitsianJavaClient.exchangedata;

import com.bitsian.BitsianJavaClient.connector.GetResponse;
import com.bitsian.BitsianJavaClient.exchangedata.Response.Currency;
import com.bitsian.BitsianJavaClient.exchangedata.Response.Exchange;
import com.bitsian.BitsianJavaClient.exchangedata.Response.Product;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static com.bitsian.BitsianJavaClient.constant.CommonConstants.*;

@Service
public class ExchangeDataService {

    private GetResponse getResponse;

    private Gson gson = new Gson();

    @Autowired
    public ExchangeDataService(GetResponse getResponse) {
        this.getResponse = getResponse;
    }

    /**
     * get exchanges
     *
     * @param
     * @return list of exchanges
     */
    public Exchange[] getExchanges() throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {

        ResponseEntity<String> exchangeResponse = this.getResponse.getResponse(REST_END_POINT, EXCHANGE_ENDPOINT, HttpMethod.GET, null);

        JsonArray data = this.gson.fromJson(exchangeResponse.getBody(), JsonArray.class);

        return this.gson.fromJson(data, Exchange[].class);
    }


    /**
     * get currencies
     *
     * @param
     * @return list of currencies
     */
    public Currency[] getCurrencies() throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {

        ResponseEntity<String> currencyResponse = this.getResponse.getResponse(REST_END_POINT, CURRENCY_ENDPOINT, HttpMethod.GET, null);

        if(currencyResponse != null) {

            JsonArray data = this.gson.fromJson(currencyResponse.getBody(), JsonArray.class);

            return this.gson.fromJson(data, Currency[].class);

        }

        return null;
    }


    /**
     * get products
     *
     * @return list of products
     */
    public Product[] getProducts(Integer exchangeId) throws NoSuchAlgorithmException, InvalidKeyException, CloneNotSupportedException {

        ResponseEntity<String> productResponse = this.getResponse.getResponse(REST_END_POINT, PRODUCT_ENDPOINT + "?exchangeId="
                + exchangeId, HttpMethod.GET, null);

        JsonArray data = this.gson.fromJson(productResponse.getBody(), JsonArray.class);

        return this.gson.fromJson(data, Product[].class);

    }
}
