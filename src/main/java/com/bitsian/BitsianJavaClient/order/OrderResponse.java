package com.bitsian.BitsianJavaClient.order;

import com.bitsian.BitsianJavaClient.common.PageRequest;
import lombok.Data;

@Data
public class OrderResponse {

    private Order[] data;
    private PageRequest page;
}