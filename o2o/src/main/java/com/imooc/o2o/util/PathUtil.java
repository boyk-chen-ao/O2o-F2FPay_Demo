package com.imooc.o2o.util;

import java.util.Properties;

public class PathUtil {

    // 获取本地 file.separator;
    private static String SEPERATOR = System.getProperty("file.separator");

    /**
     * 与程序无关，自己写的testMain
     * @param args
     */
    public static void main(String[] args){

        // 获取操作系统的系统名字：Mac OS X
        String os = System.getProperty("os.name");

        // 输出操作系统名字
        System.out.println(os);

        //获取所有的属性
        Properties properties = System.getProperties();
        //遍历所有的属性
        for (String key : properties.stringPropertyNames()) {
            //输出对应的键和值
            System.out.println(key + "=" + properties.getProperty(key));
        }

    }

    /**
     * 获取ImgBasePath
     * @return
     */
    public static String getImgBasePath(){

        // 获取操作系统的系统名字：Mac OS X
        String os = System.getProperty("os.name");

        String basePath = "";

        if (os.toLowerCase().startsWith("win")){

            basePath = "D:/projectdev/image/";
        }

        else {

            // TODO 获取这个路径在哪里？
            basePath = "/Users/boykchen/Desktop/";
        }

        basePath = basePath.replace("/",SEPERATOR);

        return basePath;
    }

    /**
     * 获取ShopImagePath
     * @param shopId
     * @return
     */
    public static String getShopImagePath(long shopId){

        // TODO 这个路径？
        String imagePath = "upload/item/shop/"+shopId+"/";

        return imagePath.replace("/",SEPERATOR);
    }
}
