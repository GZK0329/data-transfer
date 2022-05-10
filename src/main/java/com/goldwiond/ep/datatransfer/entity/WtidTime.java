package com.goldwiond.ep.datatransfer.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WtidTime {
    private LocalDateTime localDateTime;
    private List<Integer> wtids;
    private Integer size;
}
