package com.imooc.o2o.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;

public class JacksonTest extends BaseTest {

    @Test
    public void jacksonTestSuccess() throws JsonProcessingException {

        Shop shop = new Shop();
        //new some Object;
        shop.setArea(new Area());
        shop.setOwner(new PersonInfo());
        shop.setShopCategory(new ShopCategory());

        ObjectMapper mapper = new ObjectMapper(); // create once, reuse

        String jsonString = mapper.writeValueAsString(shop);

        System.out.println(jsonString);
        // {
        //    "shopId":null,
        //    "shopName":null,
        //    "shopDesc":null,
        //    "shopAddr":null,
        //    "phone":null,
        //    "shopImg":null,
        //    "priority":null,
        //    "createTime":null,
        //    "lastEditTime":null,
        //    "enableStatus":null,
        //    "advice":null,
        //    "area":{
        //        "areaId":null,
        //        "areaName":null,
        //        "priority":null,
        //        "createTime":null,
        //        "lastEditTime":null
        //    },
        //    "owner":{
        //        "userId":null,
        //        "name":null,
        //        "profileImg":null,
        //        "email":null,
        //        "gender":null,
        //        "enableStatus":null,
        //        "userType":null,
        //        "createTime":null,
        //        "lastEditTime":null
        //    },
        //    "shopCategory":{
        //        "shopCategoryId":null,
        //        "shopCategoryName":null,
        //        "shopCategoryDesc":null,
        //        "shopCategoryImg":null,
        //        "priority":null,
        //        "createTime":null,
        //        "lastEditTime":null,
        //        "parent":null
        //    }
        //}
    }

    @Test
    public void jacksonTestNotSuccess() throws JsonProcessingException{

        Shop shop = new Shop();

        ObjectMapper mapper = new ObjectMapper(); // create once, reuse

        String jsonString = mapper.writeValueAsString(shop);

        System.out.println(jsonString);
        //{
        //    "shopId":null,
        //    "shopName":null,
        //    "shopDesc":null,
        //    "shopAddr":null,
        //    "phone":null,
        //    "shopImg":null,
        //    "priority":null,
        //    "createTime":null,
        //    "lastEditTime":null,
        //    "enableStatus":null,
        //    "advice":null,
        //    "area":null,
        //    "owner":null,
        //    "shopCategory":null
        //}
    }
}
