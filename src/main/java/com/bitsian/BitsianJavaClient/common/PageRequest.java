package com.bitsian.BitsianJavaClient.common;

import lombok.Data;

@Data
public class PageRequest {
    /**
     * Page number.
     */
    public Integer page;

    /**
     * Records per page.
     */
    public Integer pageSize;

    /**
     * totalPage
     */
    public Integer totalPage;


}
