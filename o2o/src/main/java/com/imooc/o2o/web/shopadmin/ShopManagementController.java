package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopStateEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    //2019。10。29-------------------------------------------------//

    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<>();
        // 1.接收并转化相应的参数，包括店铺信息以及图片信息

        //   2019.10.28-insert---------------------
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //   2019.10.28-------------------------END

        //(1)获取shop对象；
        //前台传过来一个shopStr参数，json类型，后面进行转换；
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");

        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {

            shop = mapper.readValue(shopStr, Shop.class);

        } catch (Exception e) {

            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //（2）获取文件流
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        // 如果文件流存在
        if (commonsMultipartResolver.isMultipart(request)){

            //public interface MultipartHttpServletRequest extends HttpServletRequest, MultipartRequest{}
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        // 2.修改店铺
        if (shop != null && shop.getShopId() != null){
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
//            File shopImgFile = new File(PathUtil.getImgBasePath()+shopImg.getOriginalFilename());
//            try {
//                shopImgFile.createNewFile();
//            }catch (IOException e){
//                modelMap.put("success",false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }
//            try {
//                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
//            } catch (IOException e) {
//                modelMap.put("success",false);
//                modelMap.put("errMsg",e.getMessage());
//                return modelMap;
//            }
            ShopExecution se;
            if(shopImg == null){
                se = shopService.modifyShop(shop,null,null);
            }
            else {
                se = shopService.modifyShop(shop,null,null);
            }
            if (se.getState() == ShopStateEnum.SUCCESS.getState()){
                modelMap.put("success",true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg",se.getStateInfo());
                return modelMap;
            }
        }
        else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺ID");
            return modelMap;
        }
        return modelMap;
    }

    //2019。10。29-------------------------------------------------//

    @RequestMapping(value="/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitInfo(){
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    // ⬆️2019.10.24------------------------------------------------------------------------------------------New START

    /**
     * Web 服务器收到客户端的 http 请求，会针对每一次请求，分别创建一个用于 代表请求的 request 对象和 代表响应的 response 对象。
     * request 和 response 对象代表请求和响应：获取客户端数据，需要通过request 对象； 向客户端输出数据，需要通过 response 对象。
     *
     * HttpServletRequest:
     *                   对象是封装了用户的请求信息，包括请求参数去，请求头等信息.
     *                   获取客户端请求参数:String name=request.getParameter("uname");
     *
     * HttpServletResponse:
     *                   的主要功能用于服务器对客户端的请求进行响应，将Web 服务器处理后的结果返回给客户端。
     */

    //@responsebody表示该方法的返回结果直接写入HTTP response body中
    //一般在异 步获取数据时使用，在使用@RequestMapping后，返回值通常解析为跳转路径，加上@responsebody后返回结果不会被解析为跳转路 径，
    //而是直接写入HTTP response body中。比如异步获取json数据，加上@responsebody后，会直接返回json数据。

    /**
     * 新增店铺信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request){

        Map<String,Object> modelMap = new HashMap<>();
        // 1.接收并转化相应的参数，包括店铺信息以及图片信息

        //   2019.10.28-insert---------------------
        if (!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","输入了错误的验证码");
            return modelMap;
        }
        //   2019.10.28-------------------------END

        //(1)获取shop对象；
        //前台传过来一个shopStr参数，json类型，后面进行转换；
        String shopStr = HttpServletRequestUtil.getString(request,"shopStr");

        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {

            // Json字符串--->转对象
            shop = mapper.readValue(shopStr, Shop.class);

        } catch (IOException e) {

            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        //（2）获取文件流
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());

        // 如果文件流存在
        if (commonsMultipartResolver.isMultipart(request)){

            //public interface MultipartHttpServletRequest extends HttpServletRequest, MultipartRequest{}
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //上の場合以外
        else {
            modelMap.put("success",false);
            modelMap.put("errMsg","上传图片不能为空");
            return modelMap;
        }

        // 2.注册店铺
        //request.getSession().getServletContext()
        //request.getSession().getAttribute("user")
        if (shop != null && shopImg != null){
            // PersonInfo owner = new PersonInfo();   ----------2019.10.29-----update;
            PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
            owner.setUserId(1L);
            shop.setOwner(owner);
            File shopImgFile = new File(PathUtil.getImgBasePath()+shopImg.getOriginalFilename());
            try {
                shopImgFile.createNewFile();
            }catch (IOException e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
            try {
                inputStreamToFile(shopImg.getInputStream(),shopImgFile);
            } catch (IOException e) {
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
            ShopExecution se = shopService.addShop(shop,shopImgFile);
            if (se.getState() == ShopStateEnum.CHECK.getState()){
                modelMap.put("success",true);
                //该用户可以操作的店铺列表
                @SuppressWarnings("unchecked")
                List<Shop> shopList = (List<Shop>)request.getSession().getAttribute("shoplist");
                if(shopList == null || shopList.size() == 0) {
                    shopList = new ArrayList<Shop>();
                }
                shopList.add(se.getShop());
                request.getSession().setAttribute("shopList", shopList);
            }else {
                modelMap.put("success",false);
                modelMap.put("errMsg",se.getStateInfo());
                return modelMap;
            }
        }
        else {
            modelMap.put("success",false);
            modelMap.put("errMsg","请输入店铺信息");
            return modelMap;
        }
        return modelMap;
    }

    /**
     * inputStream转File类型；
     * @param ins
     * @param file
     */
    private static void inputStreamToFile(InputStream ins, File file){
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer)) != -1){

                os.write(buffer,0,bytesRead);
            }
        }catch (Exception e){
            throw new RuntimeException("调用inputStreamToFile异常：" + e.getMessage());
        }finally {
            try {
                if (os != null){
                    os.close();
                }
                if (ins != null){
                    ins.close();
                }
            }catch (IOException e){
                throw new RuntimeException("inputStreamToFile关闭io产生异常：" + e.getMessage());
            }
        }
    }
}
