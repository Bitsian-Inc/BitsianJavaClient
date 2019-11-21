package com.bitsian.BitsianJavaClient.exchangedata.Response;

import lombok.Data;

@Data
public class Exchange {

    private Integer exchangeId;
    private String exchangeName;
    private String websiteLink;
    private String countryOfOrigin;
}
