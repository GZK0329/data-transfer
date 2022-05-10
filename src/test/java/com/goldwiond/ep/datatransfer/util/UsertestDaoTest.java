package com.goldwiond.ep.datatransfer.util;

import com.goldwiond.ep.datatransfer.DataTransferApplication;
import com.goldwiond.ep.datatransfer.dao.Usertest;
import com.goldwiond.ep.datatransfer.dao.UsertestDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataTransferApplication.class)
class UsertestDaoTest {

    @Autowired
    private UsertestDao usertestDao;

    @Test
    void deleteByPrimaryKey() {

    }

    @Test
    void insert() {
        Usertest usertest = new Usertest();
        usertest.setAddress("cn");
        usertest.setSalary(12.5f);
        usertest.setName("jj");
        assertEquals(1, usertestDao.insert(usertest));
    }

    @Test
    void insertSelective() {
    }

    @Test
    void selectByPrimaryKey() {
    }

    @Test
    void updateByPrimaryKeySelective() {
    }

    @Test
    void updateByPrimaryKey() {
    }
}