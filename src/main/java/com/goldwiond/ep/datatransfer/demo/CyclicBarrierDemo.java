package com.goldwiond.ep.datatransfer.demo;

import com.goldwiond.ep.datatransfer.util.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static com.goldwiond.ep.datatransfer.demo.CSVFile.createCSVFile;


@Component
@Slf4j
public class CyclicBarrierDemo extends Thread {

    public static class Soldier implements Runnable {
        public String soldier;
        private final CyclicBarrier cyclic;

        public Soldier(CyclicBarrier cyclic, String soldierName) {
            this.cyclic = cyclic;
            this.soldier = soldierName;
        }

        public void run() {
            try {
                //等待所有士兵到齐
                cyclic.await();
                doWork();
                //等待所有士兵完成工作
                cyclic.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        void doWork() {
            QueryService jdbcQuery = new QueryService();
            jdbcQuery.executeQuery(5, "realtimedata_4", 1000, 1L, 1, true, true, null);
            log.info(soldier + ":任务完成");
        }
    }

    public static class BarrierRun implements Runnable {
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int N) {
            this.flag = flag;
            this.N = N;
        }

        public void run() {
            if (flag) {
                log.info("司令:[士兵" + N + "个，任务完成！]");
                List<Object> exportData = new ArrayList<>();
//                exportData.add("最大值");
//                exportData.add("最小值");
//                exportData.add("平均值");
//                exportData.add("中位值");

                List<List<Object>> datalist = new ArrayList<>();
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
                List<Object> data = new ArrayList<>();
                data.add(max);
                data.add(min);
                data.add(avg);
                data.add(median);
                List<Object> times = new ArrayList<>();
                times.addAll(QueryService.times);
                datalist.add(times);
                log.info("max:" + max + " min:" + min + " avg:" + avg + " median:" + median);
                createCSVFile(exportData, datalist, "/data/data", "realtimedata_0_600_10_5_6_w_m");
            } else {
                log.info("司令:[士兵" + N + "个，集合完毕！]");
                flag = true;
            }
        }
    }


    public static void main(String args[]) {
        final int N = 10;
        Thread[] allSoldier = new Thread[N];
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
        //设置屏障点，主要是为了执行这个方法
        log.info("集合队伍！");
        for (int i = 0; i < N; ++i) {
            allSoldier[i] = new Thread(new Soldier(cyclic, "线程 " + i));
            allSoldier[i].start();
        }
    }
}
