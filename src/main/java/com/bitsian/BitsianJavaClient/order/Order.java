package com.bitsian.BitsianJavaClient.order;

import lombok.Data;

@Data
public class Order {

    private String orderId;
    private String currencyPair;
    private String baseCurrencyId;
    private String baseCurrency;
    private String quantity;
    private String quoteCurrencyId;
    private String price;
    private String orderType;
    private String tifType;
    private String status;
    private String exchangeName;
    private String exchangeId;
    private String exchangeOrderId;
    private String orderPlacedVia;
    private String fillPercentage;
    private String executedPrice;
    private String feeSide;
    private String fee;
    private String statusDescription;
    private String amountFilled;
    private String createdTime;
    private String updateTime;
}
