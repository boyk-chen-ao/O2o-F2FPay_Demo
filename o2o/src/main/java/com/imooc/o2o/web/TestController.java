package com.imooc.o2o.web;

import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/testController")
public class TestController {

    Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @Autowired
    private ShopService shopService;

    /**
     *  , produces = "text/html;charset=UTF-8"
     *  解决乱码
     * @return
     */
    @RequestMapping(value = "/testShopStateEnum" , method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    private String testShopStateEnum(){


        return String.valueOf(shopService.addShop(null,null).getState()) + "，"
                + String.valueOf(shopService.addShop(null,null).getStateInfo());
    }

    @RequestMapping(value = "/testMap" , method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listArea(){

        Map<String,Object> testMap = new HashMap<>();

        List<Area> areaList = new ArrayList<>();

        try {

            areaList = testService.getAreaList();
            testMap.put("rows",areaList);
            testMap.put("total",areaList.size());

        }
        catch (Exception e){

            // 错误报告打印
            e.printStackTrace();
            testMap.put("success",false);
            testMap.put("errMsg",e.toString());

        }
        return testMap;
    }

    @RequestMapping(value = "/testLogger" , method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> testLogger(){

        logger.info("====start====");

        // 获取当前时间的毫秒数
        long startTime = System.currentTimeMillis();

        // 编辑处理
        Map<String,Object> testMap = new HashMap<>();

        List<Area> areaList = new ArrayList<>();

        try {

            areaList = testService.getAreaList();
            testMap.put("rows",areaList);
            testMap.put("total",areaList.size());

        }
        catch (Exception e){

            logger.error("test error!");
            // 错误报告打印
            e.printStackTrace();
            testMap.put("success",false);
            testMap.put("errMsg",e.toString());

        }
        logger.error("test error!");

        // 获取结束时间
        long endTime = System.currentTimeMillis();

        logger.debug("costTime:[{}ms]",endTime - startTime);
        logger.info("====end====");

        return testMap;
    }

}
