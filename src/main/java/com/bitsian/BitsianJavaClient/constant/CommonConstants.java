package com.bitsian.BitsianJavaClient.constant;

public class CommonConstants {

    public static final String BITSIAN_FEED_ENDPOINT = "/bitsian-feed";

    public static final String HASH_ALGORITHM = "HmacSHA256";

    public static final String BITSIAN_TIMESTAMP = "BITSIAN-TIMESTAMP";

    public static final String BITSIAN_API_SIGN = "BITSIAN-API-SIGN";

    public static final String BITSIAN_PASSPHRASE = "BITSIAN-PASSPHRASE";

    public static final String BITSIAN_API_KEY = "BITSIAN-API-KEY";

    public static final String PATH = "/bitsian-feed";

    public static final String METHOD = "GET";

    public static final String BODY = "";

    public static final String AUTH_FEED = "auth";

    public static final String WEBSOCKET_DESTINATION_PRIFEX = "/v1/";

    public static final String WEBSOCKET_BASE_URL = "wss://api.bitsian.io";

    public static final String RESPONSE_DATA = "data";

    public static final String BALANCE_ENDPOINT = "/balance";

    public static final String ORDER_DETAILS = "/orders";

    public static final String ORDER_CANCEL = "/cancel";

    public static final String EXCHANGE_ENDPOINT = "/exchange";

    public static final String CURRENCY_ENDPOINT = "/currency";

    public static final String PRODUCT_ENDPOINT = "/product";

    public static final String REST_END_POINT = "https://api.bitsian.io/api";

    public static final String SECRET_KEY = System.getenv("BITSIAN_SECRET_KEY");

    public static final String API_KEY = System.getenv("BITSIAN_API_KEY");

    public static final String PASSPHRASE = System.getenv("BITSIAN_PASSPHRASE");

    public static final String ERROR = "Error";

    public static final String ORDER_BOOK = "productbest";

    public static final String TRADE_TAPE = "tradetape";

    public static final String SNAP_SHOT = "ask";


}
