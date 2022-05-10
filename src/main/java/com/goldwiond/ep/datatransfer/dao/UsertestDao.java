package com.goldwiond.ep.datatransfer.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UsertestDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Usertest record);

    int insertSelective(Usertest record);

    Usertest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Usertest record);

    int updateByPrimaryKey(Usertest record);
}