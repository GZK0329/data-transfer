package com.goldwiond.ep.datatransfer.util;

import com.goldwiond.ep.datatransfer.DataTransferApplication;
import com.goldwiond.ep.datatransfer.dao.PgTest;
import com.goldwiond.ep.datatransfer.demo.CyclicBarrierDemo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataTransferApplication.class)
class QueryServiceTest {
    @Autowired
    QueryService jdbcQuery;

    @Autowired
    PgTest pgTest;

    @Autowired
    CyclicBarrierDemo cyclicBarrierDemo;


    @Test
    void queryData() {
        while (true) {
//            jdbcQuery.queryData();
        }
    }

    @Test
    void executeQuery() {
        jdbcQuery.executeQuery(5, "", 10, 1L, 1, true, true, pgTest);
    }

    @Test
    void executeQuery2() {

        CyclicBarrierDemo demo = new CyclicBarrierDemo();
        demo.start();



//        cyclicBarrierDemo.cyclicBarrier();
    }


    @Test
    void generateQuerySql() {


        jdbcQuery.generateQuerySql("realtimedata_4", 10,  true, true);

    }
}