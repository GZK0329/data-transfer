package com.goldwiond.ep.datatransfer.util;

import com.goldwiond.ep.datatransfer.dao.PgTest;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;


@Slf4j
public class ThreadDemo implements Runnable {
    private Integer times;
    private Integer deviceNum;
    private Integer hours;
    private Integer type;
    private String tableName;
    private CountDownLatch cdl;
    private boolean isInteger;
    private boolean isMars;
    private PgTest pgTest;

    ThreadDemo(Integer times, Integer deviceNum, Integer hours, Integer type, String tableName, CountDownLatch cdl, boolean isInteger, boolean isMars, PgTest pgTest) {
        this.times = times;
        this.deviceNum = deviceNum;
        this.hours = hours;
        this.type = type;
        this.tableName = tableName;
        this.cdl = cdl;
        this.isInteger = isInteger;
        this.isMars = isMars;
        this.pgTest = pgTest;
    }


    public void run() {
        QueryService jdbcQuery = new QueryService();
        jdbcQuery.executeQuery(times, tableName, deviceNum, hours, type, isInteger, isMars, pgTest);
        cdl.countDown();
    }

    public static String calculate(int i, int[][] num, int type, boolean isInteger, boolean isMars, String tableName, ExecutorService executor, PgTest pgTest) {
        int threads = num[i][1];
        CountDownLatch cdl = new CountDownLatch(threads);
        ThreadDemo threadDemo = new ThreadDemo(num[i][3], num[i][0], num[i][2], type, tableName, cdl, isInteger, isMars, pgTest);

        for (int j = 0; j < threads; j++) {
            executor.submit(threadDemo);
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Double avg = QueryService.times.stream().mapToLong(r -> r).average().getAsDouble();
        Long max = Collections.max(QueryService.times);
        Long min = Collections.min(QueryService.times);
        Collections.sort(QueryService.times);
        Long median;
        if (QueryService.times.size() % 2 == 0) {
            median = (QueryService.times.get(QueryService.times.size() / 2 - 1) + QueryService.times.get(QueryService.times.size() / 2)) / 2;
        } else {
            median = QueryService.times.get(QueryService.times.size() / 2);
        }
        QueryService.times.clear();

        String queryType = type == 1 ? "通用" : "采样";
        log.info("查询方式：" + queryType + "max:" + max + " min:" + min + " avg:" + avg + " median:" + median + " 是否整点：" + isInteger);

        String data;
        if (isInteger) {
            data = tableName + "," + num[i][0] + "," + num[i][1] + ",5," + num[i][2] + "," + num[i][3] + "," + max + "," + min + "," + avg + "," + median;
        } else {
            if (isMars) {
                data = "," + max + "," + min + "," + avg + "," + median + ",mars";
            } else {
                data = "," + max + "," + min + "," + avg + "," + median + ",heap";
            }

            if (type == 1) {
                data = data + ",通用查询";
            } else {
                data = data + ",采样查询";
            }
        }
        return data;
    }
}
