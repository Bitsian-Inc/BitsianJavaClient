package com.bitsian.BitsianJavaClient.exchangedata.Response;

import lombok.Data;

@Data
public class Product {

    private Integer exchangeProductId;
    private String productName;
    private String productSymbol;
    private String baseCurrencySymbol;
    private String quoteCurrencySymbol;
    private Integer exchangeId;
    private String exchangeName;
}
