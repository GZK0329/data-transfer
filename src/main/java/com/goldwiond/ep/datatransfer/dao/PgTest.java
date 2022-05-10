package com.goldwiond.ep.datatransfer.dao;

import com.goldwiond.ep.datatransfer.entity.QueryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PgTest {

    int count();

    List<QueryEntity> queryCommon(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                  @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);

    List<QueryEntity> queryCommon0(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                   @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);


    List<QueryEntity> queryCommon4(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                   @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);

    List<QueryEntity> queryCommon6(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                   @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);

    List<QueryEntity> queryCommon1000(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                      @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);

    List<QueryEntity> queryCommonStatistic0(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                      @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);


    List<QueryEntity> queryCommonStatistic3(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                            @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);


    List<QueryEntity> queryCommonStatistic4(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                            @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);


    void test();

    List<QueryEntity> querySampling0(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                   @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);

    List<QueryEntity> querySampling4(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                     @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);

    List<QueryEntity> querySampling6(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                     @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);

    List<QueryEntity> querySampling1000(@Param("randColumn1") String randColumn1, @Param("randColumn2") String randColumn2, @Param("randColumn3") String randColumn3, @Param("randColumn4") String randColumn4, @Param("randColumn5") String randColumn5,
                                     @Param("wtids") List<Integer> wtids, @Param("localDateTime1") LocalDateTime localDateTime1, @Param("localDateTime2") LocalDateTime localDateTime2);





}
