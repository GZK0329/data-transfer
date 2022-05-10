package com.goldwiond.ep.datatransfer.cvsutil;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvUtilTest {

    String csvfile = "D:\\read\\对账文件20211111.csv";

    @Test
    void writeToCsv() {
        String headDataStr = "交易流水,商户编号,订单金额,支付金额,优惠金额,收款账户,支付方式,支付状态,支付时间,交易类型";
        List<String> dataList = new ArrayList<>();

        CsvUtil.writeToCsv(headDataStr, dataList, csvfile, false);
        CsvUtil.writeToCsv("", dataList, csvfile, false);
        dataList.add("190469644375,100011,4000,4000,0,105000041112608,03,00,2021-09-12 07:44:12,8");
        dataList.add("190469644954,100011,4000,4000,0,105000041112608,03,00,2021-09-12 07:50:10,8");
        dataList.add("190469645620,100011,4000,4000,0,105000041112608,03,00,2021-09-12 07:56:59,8");
        dataList.add("190469661593,100011,2000,2000,0,105000041112608,01,00,2021-09-12 09:51:24,8");
        dataList.add("190469661738,100011,2000,2000,0,105000041112608,01,00,2021-09-12 09:52:15,8");
        dataList.add("190469661853,100011,2000,2000,0,105000041112608,01,00,2021-09-12 09:52:51,8");
        dataList.add("190469661931,100011,2000,2000,0,105000041112608,01,00,2021-09-12 09:53:27,8");
        dataList.add("190469699471,100011,2000,2000,0,105000041112608,03,00,2021-09-12 13:59:02,8");
        dataList.add("190469699540,100011,2000,2000,0,105000041112608,03,00,2021-09-12 13:59:29,8");
        dataList.add("190469712840,100011,4000,4000,0,105000041112608,03,00,2021-09-12 15:20:32,8");
        CsvUtil.writeToCsv(headDataStr, dataList, csvfile, false);
    }

    @Test
    void readFromCsv() {
        List<String> dataList = new ArrayList<>();
        dataList.add("190469730006,100011,4000,4000,0,105000041112608,01,00,2021-09-12 16:52:57,8");
        dataList.add("190469730173,100011,4000,4000,0,105000041112608,01,00,2021-09-12 16:53:54,8");
        dataList.add("190469730367,100011,4000,4000,0,105000041112608,01,00,2021-09-12 16:54:50,8");
        dataList.add("190469736543,100011,4000,4000,0,105000041112608,01,00,2021-09-12 17:25:53,8");
        dataList.add("190469746558,100011,4000,4000,0,105000041112608,01,00,2021-09-12 18:14:13,8");
        CsvUtil.writeToCsv("", dataList, csvfile, true);
    }
}