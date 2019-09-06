package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public class ShopDaoTest extends BaseTest {

    @Autowired
    private ShopDao shopDao;

    // 测试新增店铺信息

    @Test
    /**
     * 回滚设置，不插入数据库中！
     */
    @Transactional
    public void testInsertShop(){

        // 新建Shop对象，进行插入，从而测试；
        Shop shop = new Shop();

        // shopId为自增主键，不用设值
        // 其他值设定
        shop.setShopName("测试的店铺");
        shop.setShopDesc("咖啡牛奶店");
        shop.setShopAddr("浙江省杭州市龙湖天街a18");
        shop.setPhone("15866667756");
        shop.setShopImg("/");
        shop.setPriority(1);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        shop.setEnableStatus(1);
        shop.setAdvice("请注意卫生！");

        // shop对象中的关联对象设值
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);

        // shop对象中的对象设入shop对象中
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        int result = shopDao.insertShop(shop);

        Assert.assertEquals(1,result);
    }

    @Test
    public void testUpdateShop(){

        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setShopName("testName");
        shop.setShopDesc("测试描述");
        shop.setShopAddr("测试地址");
        shop.setLastEditTime(new Date());
        int effectedNum = shopDao.updateShop(shop);
        Assert.assertEquals(1, effectedNum);

    }
}
