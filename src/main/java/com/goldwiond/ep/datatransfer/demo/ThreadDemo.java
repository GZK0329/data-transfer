package com.goldwiond.ep.datatransfer.demo;

import com.goldwiond.ep.datatransfer.cvsutil.CsvUtil;
import com.goldwiond.ep.datatransfer.dao.PgTest;
import com.goldwiond.ep.datatransfer.util.QueryService;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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


    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(100);
        int[][] num = new int[][]{
                {10, 100, 1, 100},
                {100, 30, 1, 100},
                {1000, 10, 1, 5},
                {10, 100, 6, 100},
                {100, 30, 6, 100},
                {1000, 10, 6, 5},
                {10, 100, 12, 100},
                {100, 30, 12, 100},
                {1000, 10, 12, 5}
        };

        int[][] wind = new int[][]{
                {10, 100, 1, 100},
                {100, 30, 1, 100},
                {600, 10, 1, 5},
                {10, 100, 6, 100},
                {100, 30, 6, 100},
                {600, 10, 6, 5},
                {10, 100, 12, 100},
                {100, 30, 12, 100},
                {600, 10, 12, 5}
        };

//        int[][] num = new int[][]{
//                {10, 2, 1, 2},
//
//        };

        List<String> devices = new ArrayList<>();
        devices.add("realtimedata_4");
        devices.add("realtimedata_6");
        devices.add("realtimedata_1000");
        Instant start;
        Instant end;


        for (String device : devices) {
            String title = "表名,设备数,并发数,测点数,时间,执行次数,最大耗时(ms整点),最小耗时(ms整点),平均耗时(ms整点),中位值(ms整点),最大耗时(ms非整点),最小耗时(ms非整点),平均耗时(ms非整点),中位值(ms非整点),结构,查询方式";
            String csvFile = "D:/read2/查询性能mars1.csv";
            if ("realtimedata_0".equals(device)) {
                num = wind;
            }

            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);
            start = Instant.now();
            for (int i = 0; i < num.length; i++) {
                String data1 = calculate(i, num, 1, true, true, device, executor, null);
                String data2 = calculate(i, num, 1, false, true, device, executor, null);
                String data = data1 + data2;
                CsvUtil.writeToCsv("", Collections.singletonList(data), csvFile, true);
            }
            end = Instant.now();
            log.info(device + " " + start + "——" + end);

//            start = Instant.now();
//            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);
//            for (int i = 0; i < num.length; i++) {
//                String data1 = calculate(i, num, 1, true, false, device, executor);
//                String data2 = calculate(i, num, 1, false, false, device, executor);
//                String data = data1 + data2;
//                CsvUtil.writeToCsv("", Collections.singletonList(data), csvFile, true);
//            }
//            end = Instant.now();
//            log.info(device + " " + start + "——" + end);

            start = Instant.now();
            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);
            for (int i = 0; i < num.length; i++) {
                String data1 = calculate(i, num, 2, true, true, device, executor, null);
                String data2 = calculate(i, num, 2, false, true, device, executor, null);
                String data = data1 + data2;
                CsvUtil.writeToCsv("", Collections.singletonList(data), csvFile, true);
            }
            end = Instant.now();
            log.info(device + " " + start + "——" + end);

//            start = Instant.now();
//            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);
//            for (int i = 0; i < num.length; i++) {
//                String data1 = calculate(i, num, 2, true, false, device, executor);
//                String data2 = calculate(i, num, 2, false, false, device, executor);
//                String data = data1 + data2;
//                CsvUtil.writeToCsv("", Collections.singletonList(data), csvFile, true);
//            }
//            end = Instant.now();
//            log.info(device + " " + start + "——" + end);
        }
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
