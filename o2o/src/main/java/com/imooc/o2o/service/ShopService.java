package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService {

    /**
     * 通过店铺Id获取店铺信息
     *
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括对图片的处理
     *
     * @param shop
     * @param fileName
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

    ShopExecution addShop(Shop shop, File shopImg);
}
