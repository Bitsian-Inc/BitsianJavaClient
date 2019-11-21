package com.bitsian.BitsianJavaClient.balance;

import com.bitsian.BitsianJavaClient.common.PageRequest;
import lombok.Data;

@Data
public class BalanceResponse {

    private Balance[] data;
    private PageRequest page;
}
