package com.goldwiond.ep.datatransfer.dao;

import java.io.Serializable;
import lombok.Data;

/**
 * usertest
 * @author 
 */
@Data
public class Usertest implements Serializable {
    private Integer id;

    private String name;

    private String address;

    private Float salary;

    private static final long serialVersionUID = 1L;
}