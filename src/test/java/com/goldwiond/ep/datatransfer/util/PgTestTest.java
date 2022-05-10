package com.goldwiond.ep.datatransfer.util;

import com.goldwiond.ep.datatransfer.DataTransferApplication;
import com.goldwiond.ep.datatransfer.dao.PgTest;
import com.goldwiond.ep.datatransfer.demo.CyclicBarrierDemo;
import com.goldwiond.ep.datatransfer.entity.QueryEntity;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataTransferApplication.class)
class PgTestTest {

    @Autowired
    private PgTest pgTest;

    private String realTime4_10 = "630008040,630008042,630008067,630008016,630008015,630008070,630008088,630008080,630008084,630008083";

    @Test
    void count() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list = list.subList(0,2);
        System.out.println(list);



        int result = pgTest.count();
        System.out.println(result);
    }

    @Test
    void queryRealTime() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2022, 04, 11, 10, 38, 17);
        LocalDateTime localDateTime2 = LocalDateTime.of(2022, 04, 11, 10, 38, 18);
        while (true) {
            Instant start = Instant.now();
            List<String> realTimeData = new ArrayList<>();
//            List<RealTimeData> realTimeData = pgTest.queryRealTime(localDateTime1, localDateTime2);
            Instant end = Instant.now();
            long timeElapseds = Duration.between(start, end).toMillis();
            System.out.println("查询条数： " + realTimeData.size() + " 耗时：" + timeElapseds + "ms, 时间" + localDateTime1);
            localDateTime1 = localDateTime1.plusSeconds(1L);
            localDateTime2 = localDateTime2.plusSeconds(1L);
        }

    }

    @Test
    void queryCommon() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2022, 04, 8, 15, 45, 59);
        LocalDateTime localDateTime2 = localDateTime1.plusHours(1);

        List<Integer> wtids = new ArrayList<>(Arrays.asList(106001014, 106001024, 106001015, 106001016, 106001006, 106001026, 106001004, 106001053, 106001007, 106001012));

        List<QueryEntity> queryEntities = pgTest.queryCommon("WCNV.AC.Ra.F32.ChopperI", "WCNV.AC.Ra.F32.IDCI", "WCNV.Other.Rn.U16.ConverterType", "WCNV.Posi.Ra.F32.Azimuth", "WCNV.PPV.Ra.F32.RecitfierI",
                wtids, localDateTime1, localDateTime2);
        System.out.println(queryEntities);

    }

    @Test
    void test() {

        pgTest.test();

    }

    @Test
    void cyclicBarrier() {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new CyclicBarrierDemo.BarrierRun(flag, N));
        //设置屏障点，主要是为了执行这个方法
        System.out.println("集合队伍！");
        for (int i = 0; i < N; ++i) {
            allSoldier[i] = new Thread(new CyclicBarrierDemo.Soldier(cyclic, "线程 " + i));
            allSoldier[i].start();
        }
    }
}