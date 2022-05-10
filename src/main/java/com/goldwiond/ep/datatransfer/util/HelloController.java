package com.goldwiond.ep.datatransfer.util;

import com.goldwiond.ep.datatransfer.cvsutil.CsvUtil;
import com.goldwiond.ep.datatransfer.dao.PgTest;
import com.goldwiond.ep.datatransfer.demo.CyclicBarrierDemo;
import com.goldwiond.ep.datatransfer.entity.QueryEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.goldwiond.ep.datatransfer.demo.ThreadDemo.calculate;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private PgTest pgTest;


    @RequestMapping(path = {"/hello"})
    public String HelloSpring() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2022, 04, 8, 15, 45, 59);
        LocalDateTime localDateTime2 = localDateTime1.plusHours(1);

        List<Integer> wtids = new ArrayList<>(Arrays.asList(106001014, 106001024, 106001015, 106001016, 106001006, 106001026, 106001004, 106001053, 106001007, 106001012));

        List<QueryEntity> queryEntities = pgTest.queryCommon("WCNV.AC.Ra.F32.ChopperI", "WCNV.AC.Ra.F32.IDCI", "WCNV.Other.Rn.U16.ConverterType", "WCNV.Posi.Ra.F32.Azimuth", "WCNV.PPV.Ra.F32.RecitfierI",
                wtids, localDateTime1, localDateTime2);
        log.info(queryEntities.toString());
        return "ok";
    }

    @RequestMapping(path = {"/cyclic"})
    public String CyclicBarrierDemo() {
        final int N = 2;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new CyclicBarrierDemo.BarrierRun(flag, N));
        //设置屏障点，主要是为了执行这个方法
        log.info("集合队伍！");
        for (int i = 0; i < N; ++i) {
            allSoldier[i] = new Thread(new CyclicBarrierDemo.Soldier(cyclic, "线程 " + i));
            allSoldier[i].start();
        }
        return "ok";
    }

    @RequestMapping(path = {"/mars/{deviceNum}"})
    public String mars(@PathVariable Integer deviceNum) {
//        String device = "realtimedata_" + deviceNum;

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

        ExecutorService executor = Executors.newFixedThreadPool(100);

        List<String> devices = new ArrayList<>();
        devices.add("realtimedata_0");
        devices.add("realtimedata_4");
        devices.add("realtimedata_6");
        devices.add("realtimedata_1000");

        Instant start;
        Instant end;

        String title = "表名,设备数,并发数,测点数,时间,执行次数,最大耗时(ms整点),最小耗时(ms整点),平均耗时(ms整点),中位值(ms整点),最大耗时(ms非整点),最小耗时(ms非整点),平均耗时(ms非整点),中位值(ms非整点),结构,查询方式";
        String csvFile = "/data/data/历史瞬态查询性能mars.csv";

        for (String device : devices) {
            if ("realtimedata_0".equals(device)) {
                num = wind;
            }
            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);


            log.info(device + "_开始执行通用查询");
            start = Instant.now();
            for (int i = 0; i < num.length; i++) {
                String data1 = calculate(i, num, 1, true, true, device, executor, pgTest);
                String data2 = calculate(i, num, 1, false, true, device, executor, pgTest);
                String data = data1 + data2;
                CsvUtil.writeToCsv("", Collections.singletonList(data), csvFile, true);
            }
            end = Instant.now();
            log.info(device + " " + start + "——" + end);

            log.info(device + "_开始执行采样查询");
            start = Instant.now();
            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);
            for (int i = 0; i < num.length; i++) {
                String data1 = calculate(i, num, 2, true, true, device, executor, pgTest);
                String data2 = calculate(i, num, 2, false, true, device, executor, pgTest);
                String data = data1 + data2;
                CsvUtil.writeToCsv("", Collections.singletonList(data), csvFile, true);
            }
            end = Instant.now();
            log.info(device + " " + start + "——" + end);
        }

        return "执行完毕";
    }

    @RequestMapping(path = {"/mars1"})
    public String marsTest() {
        String device = "realtimedata_0";

        int[][] num = new int[][]{
                {600, 10, 12, 5}
        };

        ExecutorService executor = Executors.newFixedThreadPool(100);

        Instant start;
        Instant end;

        String title = "表名,设备数,并发数,测点数,时间,执行次数,最大耗时(ms整点),最小耗时(ms整点),平均耗时(ms整点),中位值(ms整点),最大耗时(ms非整点),最小耗时(ms非整点),平均耗时(ms非整点),中位值(ms非整点),结构,查询方式";
        String csvFile = "/data/data/查询性能mars_2.csv";
        CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);

        log.info("开始执行采样查询");
        start = Instant.now();
        for (int i = 0; i < num.length; i++) {
            String data1 = calculate(i, num, 2, true, true, device, executor, pgTest);
            String data2 = calculate(i, num, 2, false, true, device, executor, pgTest);
            String data = data1 + data2;
            CsvUtil.writeToCsv("", Collections.singletonList(data), csvFile, true);
        }
        end = Instant.now();
        log.info(device + " " + start + "——" + end);

        return "执行完毕";
    }

    @RequestMapping(path = {"/heap/{deviceNum}"})
    public String heap(@PathVariable Integer deviceNum) {
//        String device = "realtimedata_" + deviceNum;
        int[][] num = new int[][]{
                {10, 100, 1, 50},
                {100, 30, 1, 50},
                {300, 10, 1, 5},
                {10, 100, 3, 50},
                {100, 30, 3, 50},
                {300, 10, 3, 5},
                {10, 100, 6, 50},
                {100, 30, 6, 50},
                {300, 10, 6, 5}
        };


        ExecutorService executor = Executors.newFixedThreadPool(100);

        List<String> devices = new ArrayList<>();
        devices.add("realtimedata_0");
        devices.add("realtimedata_4");
        devices.add("realtimedata_6");
        devices.add("realtimedata_1000");

        Instant start;
        Instant end;

        String title = "表名,设备数,并发数,测点数,时间,执行次数,最大耗时(ms整点),最小耗时(ms整点),平均耗时(ms整点),中位值(ms整点),最大耗时(ms非整点),最小耗时(ms非整点),平均耗时(ms非整点),中位值(ms非整点),结构,查询方式";
        String csvFile = "/data/data/历史瞬态查询性能_heap.csv";

        for (String device : devices) {
            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);

            log.info(device + "_开始执行heap通用查询");
            start = Instant.now();
            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);
            for (int i = 0; i < num.length; i++) {
                String data1 = calculate(i, num, 1, true, false, device, executor, pgTest);
                CsvUtil.writeToCsv("", Collections.singletonList(data1), csvFile, true);
            }
            end = Instant.now();
            log.info(device + " " + start + "——" + end);


            log.info(device + "_开始执行heap采样查询");
            start = Instant.now();
            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);
            for (int i = 0; i < num.length; i++) {
                String data1 = calculate(i, num, 2, true, false, device, executor, pgTest);
                CsvUtil.writeToCsv("", Collections.singletonList(data1), csvFile, true);
            }
            end = Instant.now();
            log.info(device + " " + start + "——" + end);
        }

        return "执行完毕";
    }

    @RequestMapping(path = {"/statist"})
    public String statisticMars() {
//        String device = "statisticdata_" + deviceNum;

        int[][] num4 = new int[][]{
                {10, 100, 12, 100},
                {100, 30, 12, 100},
                {1000, 10, 12, 5},
                {10, 100, 36, 100},
                {100, 30, 36, 100},
                {1000, 10, 36, 5},
                {10, 100, 168, 100},
                {100, 30, 168, 100},
                {1000, 10, 168, 5}
        };

        int[][] num0 = new int[][]{
                {10, 100, 12, 100},
                {100, 30, 12, 100},
                {600, 10, 12, 5},
                {10, 100, 36, 100},
                {100, 30, 36, 100},
                {600, 10, 36, 5},
                {10, 100, 168, 100},
                {100, 30, 168, 100},
                {600, 10, 168, 5}
        };

        int[][] num3 = new int[][]{
                {10, 100, 12, 100},
                {100, 30, 12, 100},
                {490, 10, 12, 5},
                {10, 100, 36, 100},
                {100, 30, 36, 100},
                {490, 10, 36, 5},
                {10, 100, 168, 100},
                {100, 30, 168, 100},
                {490, 10, 168, 5}
        };


        ExecutorService executor = Executors.newFixedThreadPool(100);

        List<String> devices = new ArrayList<>();

        devices.add("statisticdata_0");
        devices.add("statisticdata_3");
        devices.add("statisticdata_4");

        Instant start;
        Instant end;
        String title = "表名,设备数,并发数,测点数,时间,执行次数,最大耗时(ms整点),最小耗时(ms整点),平均耗时(ms整点),中位值(ms整点),最大耗时(ms非整点),最小耗时(ms非整点),平均耗时(ms非整点),中位值(ms非整点),结构,查询方式";
        String csvFile = "/data/data/十分钟查询性能mars.csv";
//        String csvFile = "d:/read2/十分钟查询性能mars.csv";

        for (String device : devices) {

            CsvUtil.writeToCsv(title, new ArrayList<>(), csvFile, true);

            int[][] num = new int[9][4];
            if ("statisticdata_0".equals(device)) {
                num = num0;
            } else if ("statisticdata_3".equals(device)) {
                num = num3;
            } else if ("statisticdata_4".equals(device)) {
                num = num4;
            }

            log.info(device + "_开始执行十分钟通用查询");
            start = Instant.now();
            for (int i = 0; i < num.length; i++) {
                String data1 = calculate(i, num, 1, true, true, device, executor, pgTest);
                String data2 = calculate(i, num, 1, false, true, device, executor, pgTest);
                String data = data1 + data2;
                CsvUtil.writeToCsv("", Collections.singletonList(data), csvFile, true);
            }
            end = Instant.now();
            log.info(device + " " + start + "——" + end);
        }

        return "执行完毕";
    }
}
