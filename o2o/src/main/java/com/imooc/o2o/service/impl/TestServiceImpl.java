package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.TestDao;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestDao testDao;

    @Override
    public List<Area> getAreaList() {

        return testDao.queryArea();

    }
}
