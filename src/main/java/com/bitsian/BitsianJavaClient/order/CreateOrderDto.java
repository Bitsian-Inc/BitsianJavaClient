package com.bitsian.BitsianJavaClient.order;


import lombok.Data;

@Data
public class CreateOrderDto {

    /**
     * Order side.
     */
    private String orderSide;

    /**
     * Currency pair.
     */
    private String currencyPair;

    /**
     * Order Quantity.
     */
    private Double quantity;

    /**
     * Order Price.
     */
    private Double price;

    /**
     * Order Type.
     */
    private String orderType;

    /**
     * Tif type.
     */
    private String tifType;

    /**
     * exchange Id.
     */
    private Integer exchangeId;

}
