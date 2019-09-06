package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TestServiceTest extends BaseTest {

    @Autowired
    private TestService testService;

    @Test
    public void testTestService(){

        List<Area> areaList = testService.getAreaList();

        Assert.assertEquals("西苑",areaList.get(0).getAreaName());

    }



}
