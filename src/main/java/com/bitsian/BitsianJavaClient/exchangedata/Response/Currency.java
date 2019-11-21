package com.bitsian.BitsianJavaClient.exchangedata.Response;

import lombok.Data;

@Data
public class Currency {

    private String currencySymbol;
    private String currencyName;
    private Integer currencyId;
}
