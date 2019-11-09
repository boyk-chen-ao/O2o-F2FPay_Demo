package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestDaoTest extends BaseTest {

    @Autowired
    private TestDao testDao;

    @Test
    public void test() {
        System.out.println("这是具体的单元测试");
    }

    @Test
    public void testTestDao(){

        List<Area> areaList = testDao.queryArea();

        Assert.assertEquals(2,areaList.size());

        System.out.println(areaList.get(0).getAreaName());

    }

}
