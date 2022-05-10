package com.goldwiond.ep.datatransfer.entity;

import lombok.Data;


@Data
public class QueryEntity {
    private Integer wtid;
    private Integer count;
    private Float avg;
    private Integer sum;
    private Float max;
    private Float min;
}
