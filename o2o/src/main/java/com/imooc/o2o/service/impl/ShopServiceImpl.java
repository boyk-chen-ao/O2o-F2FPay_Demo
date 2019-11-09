package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ShopDao;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.exceptions.ShopOperationException;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            // 1.判断是否需要处理图片
            try{
                if(shopImgInputStream != null && fileName != null && !"".equals(fileName)){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if(tempShop.getShopImg() != null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
//                    addShopImg(shop, shopImgInputStream, fileName);
                }
                // 2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }

        }
    }

    @Override
//    @Transactional
    public ShopExecution addShop(Shop shop, File shopImg) {

        if (shop == null) {

            // new一个ShopExecution构造器；
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {

            // 1。新增店铺信息；
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            int effectedNum = shopDao.insertShop(shop);

            // 判断是否新增成功
            if (effectedNum <= 0) {
                /**
                 * 为什么用RuntimeException，
                 * 因为保证事务可以回滚；
                 * Exception，则不能使事务回滚；
                 */
                throw new ShopOperationException("创建店铺失败");
            } else {
                if (shopImg != null) {
                    // 2。存储图片
                    try {
                        addShopImg(shop, shopImg);
                    } catch (Exception e) {

                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    // 3。更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {

                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }

        } catch (Exception e) {

            throw new ShopOperationException("addShop error:" + e.getMessage());
        }

        // 成功状态返回
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    /**
     * 图片上传
     * @param shop
     * @param shopImg
     */
    private void addShopImg(Shop shop, File shopImg) {
        // 获取shop图片目录的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImgAddr);
    }
}
