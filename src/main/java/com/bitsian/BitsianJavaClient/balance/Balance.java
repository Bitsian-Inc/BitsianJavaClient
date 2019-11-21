package com.bitsian.BitsianJavaClient.balance;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Balance {

    private Integer exchangeId;
    @SerializedName(value = "bitsianCurrencyId")
    private Integer currencyId;
    private double availableBalance;
    private double realizedBalance;
    private String currencySymbol;
    private String exchangeName;
}
