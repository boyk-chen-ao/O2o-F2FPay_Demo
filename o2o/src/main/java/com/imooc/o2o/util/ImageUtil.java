package com.imooc.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {

    // 定数basePath
    private static String BASE_PATH = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    // 设置时间格式 --SimpleDateFormat
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    // 随机数对象
    private static final Random r = new Random();

    // 日志信息
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将CommonsMultipartFile转换成File类
     *
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IllegalStateException e) {
            logger.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * thumbnailator的使用Main
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // resource路径
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        // 打印看看 ----> /Users/boykchen/Desktop/F2FPay_Demo_Java/o2o/target/classes/
        System.out.println(basePath);

        // 1.(.of)获取图片路径；
        // 2.(.size)指定输出图片的图片大小；
        // 3.(.watermark)给图片添加水印
        //               (1)水印在图片中的位置
        //               (2)水印图片的路径
        //               (3)定义透明度
        // 4.(.outputQuality)压缩图片：0.8 即压缩成 80%
        // 5.(.toFile)将处理过的图片输出到指定地方
//        Thumbnails.of(new File("/Users/boykchen/Desktop/8.10-校园商铺实战案例-o2o/Image/testImage.png"))
//                .size(200, 200).watermark(Positions.BOTTOM_RIGHT,
//                ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f).outputQuality(0.8f)
//                .toFile("/Users/boykchen/Desktop/8.10-校园商铺实战案例-o2o/Image/getTestImg.png");

        Thumbnails.of(new File("/Users/boykchen/Desktop/图片1.png"))
                .size(200, 200).outputQuality(0.8f)
                .toFile("/Users/boykchen/Desktop/getTestImg.png");
    }

    //------------ MethodStar ----------------//

    /**
     * 生成图片
     *
     * @param thumbnail
     *             文件流
     * @param targetAddr
     *             文件存放路径
     * @return
     */
    public static String generateThumbnail(File thumbnail,String targetAddr){

        // 随机名字 20190812124508889910
        String realFileName = getRandomFileName();

        // 扩展名 .png
        String extension = getFileExtension(thumbnail);

        // ------> realFileName + extension = "getTestImg.png"

        // 生成文件存放路径の文件夹
        makeDirPath(targetAddr);

        // 文件的相对路径 ----->  targetAddr + realFileName + extension
        String relativeAddr = targetAddr + realFileName + extension;

        // 输出相对路径
        logger.debug("current relativeAddr is:" + relativeAddr);

        // TODO 最后拼成什么样的路径？
        // /Users/boykchen/Desktop/upload/item/shop/53/2019082322032992336.png
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);

        logger.debug("current relativeAddr is:" + PathUtil.getImgBasePath() + relativeAddr);

        try {

            // 图片处理
            Thumbnails.of(thumbnail).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(BASE_PATH +"/watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);

            /**
             * thumbnail：/Users/boykchen/Desktop/like/test.png
             * toFile：/Users/boykchen/Desktop/upload/item/shop/53/2019082322032992336.png
             */

        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return  relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg
     * 那么这三个文件夹自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {

        // 图片存放路径文件夹，目录 全路径
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;

        // 创建全路径File
        File dirPath = new File(realFileParentPath);

        // 判断路径是否存在
        if (!dirPath.exists()){

            // 创建文件夹
            //mkdir()是创建子目录。
            //mkdirs()是创建多级目录。
            // boolean mkdirs() 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
            dirPath.mkdirs();

        }

    }

    /**
     * 获取输入文件流的扩展名
     * @param thumbnail
     * @return
     */
    private static String getFileExtension(File thumbnail) {

        // file.getOriginalFilename()是得到上传时的文件名
        String originalFileName = thumbnail.getName();

        // 	        String str = "getTestImg.png";
        //	        System.out.println(str.lastIndexOf('.'));        // 10
        //	        System.out.println(str.substring(str.lastIndexOf('.')));     // .png
        return originalFileName.substring(originalFileName.lastIndexOf("."));

    }

    /**
     * 1.生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     * @return
     */
    public static String getRandomFileName() {

        // 获取随机5位数 (10000~99999)
        int rannum = r.nextInt(89999) +10000;

        // 获取当前时间的yyyyMMddHHmmss格式
        String nowTimeStr = sDateFormat.format(new Date());

        // 创建StringBuffer对象进行字符串拼接
        StringBuffer nowTimeAndRannum = new StringBuffer();
        nowTimeAndRannum.append(nowTimeStr);
        nowTimeAndRannum.append(rannum);

        // 返回随机文件名
        return nowTimeAndRannum.toString();
    }

}
