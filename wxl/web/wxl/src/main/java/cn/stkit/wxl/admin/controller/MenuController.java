package cn.stkit.wxl.admin.controller;

import cn.stkit.wxl.model.menu.Menu;
import cn.stkit.wxl.service.menu.MenuService;
import cn.stkit.wxl.thread.AccessTokenThread;
import cn.stkit.wxl.utils.WeChatConst;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MenuController
 * @desc 对公众号的菜单的操作
 * @website http://www.stkit.cn/
 * @date 2020/6/15 11:32 上午
 */
@RestController
@RequestMapping("/wxl/admin/menu")
public class MenuController
{

    private static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 查询全部菜单
     * http://wxl.stkit.cn/wxl/admin/menu/get
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getMenu() {
        //调用接口获取access_token
        String accessToken = AccessTokenThread.accessToken.getToken();
        JSONObject jsonObject = null;
        if(accessToken != null) {
            //调用接口查询菜单
            jsonObject = menuService.getMenu(accessToken);
            //判断菜单创建结果
            return String.valueOf(jsonObject);
        }
        logger.info("token 为 " + accessToken);
        return "没有创建菜单";
    }

    /**
     * 创建菜单
     * http://wxl.stkit.cn/wxl/admin/menu/create
     * @return
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String createMenu() throws UnsupportedEncodingException {
        //调用接口获取access_token
        String accessToken = AccessTokenThread.accessToken.getToken();
        int result = 0;
        if(accessToken != null) {
            //调用接口创建菜单
            result = menuService.createMenu(getFirstMenu(), accessToken);
            //判断菜单创建结果
            if(0 == result) {
                logger.info("菜单创建成功！");
            } else {
                logger.info("菜单创建失败，错误码：" + result);
            }
        }
        return result == 0 ? "菜单创建成功" : "菜单创建失败";
    }

    /**
     * 删除菜单
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteMenu() {
        //调用接口获取access_token
        String accessToken = AccessTokenThread.accessToken.getToken();
        int result = 0;
        if(accessToken != null) {
            //调用接口删除菜单
            result = menuService.deleteMenu(accessToken);
            //判断菜单删除结果
            if(0 == result) {
                logger.info("菜单删除成功！");
            } else {
                logger.info("菜单删除失败，错误码：" + result);
            }
        }
        return result == 0 ? "菜单删除成功" : "菜单删除失败";
    }

    private static Map<String, Object> getFirstMenu() throws UnsupportedEncodingException {
        //第一栏菜单
        Menu menu1 = new Menu();
        menu1.setId("1");
        menu1.setName("想咨询");
        menu1.setType("click");
        menu1.setKey("1");

        Menu menu11 = new Menu();
        menu11.setId("11");
        menu11.setName("您怎么了");
        menu11.setType("click");
        menu11.setKey("11");

        Menu menu12 = new Menu();
        menu12.setId("12");
        menu12.setName("我的咨询师");
        menu12.setType("click");
        menu12.setKey("12");

        Menu menu13 = new Menu();
        menu13.setId("13");
        menu13.setName("我的咨询订单");
        menu13.setType("click");
        menu13.setKey("13");

        //第二栏菜单
        Menu menu2 = new Menu();
        menu2.setId("2");
        menu2.setName("启航明灯");
        menu2.setType("click");
        menu2.setKey("2");

        Menu menu21 = new Menu();
        menu21.setId("21");
        menu21.setName("心理自测");
        menu21.setType("click");
        menu21.setKey("21");

        Menu menu22 = new Menu();
        menu22.setId("22");
        menu22.setName("心灵导航");
        menu22.setType("click");
        menu22.setKey("22");

        Menu menu23 = new Menu();
        menu23.setId("23");
        menu23.setName("我的公开课");
        menu23.setType("click");
        menu23.setKey("23");

        //第三栏菜单
        Menu menu3 = new Menu();
        menu3.setId("3");
        menu3.setName("我的");
        menu3.setType("click");
        menu3.setKey("3");

        Menu menu31 = new Menu();
        menu31.setId("31");
        menu31.setName("个人资料");
        menu31.setType("view");
        menu31.setUrl(WeChatConst.oauth2_snsapi.replace("REDIRECT_URI", URLEncoder.encode("http://wxl.stkit.cn/wxl/uc", "UTF-8"))
                .replace("SCOPE", "snsapi_userinfo").replace("STATE", "31"));

        Menu menu32 = new Menu();
        menu32.setId("32");
        menu32.setName("意见反馈");
        menu32.setType("view");
        menu32.setUrl(WeChatConst.oauth2_snsapi.replace("REDIRECT_URI",URLEncoder.encode("http://wxl.stkit.cn/wxl/feedback","UTF-8"))
                .replace("SCOPE","snsapi_base").replace("STATE","32"));

        //最外一层大括号
        Map<String, Object> wechatMenuMap = new HashMap<>();
        //包装button的List
        List<Map<String, Object>> wechatMenuMapList = new ArrayList<>();

        //包装第一栏
        Map<String, Object> menuMap1 = new HashMap<>();
        Map<String, Object> menuMap11 =  new HashMap<>();
        Map<String, Object> menuMap12 =  new HashMap<>();
        Map<String, Object> menuMap13 =  new HashMap<>();
        List<Map<String, Object>> subMenuMapList1 = new ArrayList<>();

        //第一栏第一个
        menuMap11.put("name", menu11.getName());
        menuMap11.put("type", menu11.getType());
        menuMap11.put("key", menu11.getKey());
        subMenuMapList1.add(menuMap11);

        //第一栏第二个
        menuMap12.put("name", menu12.getName());
        menuMap12.put("type", menu12.getType());
        menuMap12.put("key", menu12.getKey());
        subMenuMapList1.add(menuMap12);

        //第一栏第三个
        menuMap13.put("name", menu13.getName());
        menuMap13.put("type", menu13.getType());
        menuMap13.put("key", menu13.getKey());
        subMenuMapList1.add(menuMap13);

        menuMap1.put("name", menu1.getName());
        menuMap1.put("sub_button", subMenuMapList1);

        //包装第二栏
        Map<String, Object> menuMap2 = new HashMap<>();
        Map<String, Object> menuMap21 =  new HashMap<>();
        Map<String, Object> menuMap22 =  new HashMap<>();
        Map<String, Object> menuMap23 =  new HashMap<>();
        List<Map<String, Object>> subMenuMapList2 = new ArrayList<>();

        //第二栏第一个
        menuMap21.put("name", menu21.getName());
        menuMap21.put("type", menu21.getType());
        menuMap21.put("key", menu21.getKey());
        subMenuMapList2.add(menuMap21);

        //第二栏第二个
        menuMap22.put("name", menu22.getName());
        menuMap22.put("type", menu22.getType());
        menuMap22.put("key", menu22.getKey());
        subMenuMapList2.add(menuMap22);

        //第二栏第三个
        menuMap23.put("name", menu23.getName());
        menuMap23.put("type", menu23.getType());
        menuMap23.put("key", menu23.getKey());
        subMenuMapList2.add(menuMap23);

        menuMap2.put("name", menu2.getName());
        menuMap2.put("sub_button", subMenuMapList2);

        //包装第三栏
        Map<String, Object> menuMap3 = new HashMap<>();
        Map<String, Object> menuMap31 =  new HashMap<>();
        Map<String, Object> menuMap32 =  new HashMap<>();
        List<Map<String, Object>> subMenuMapList3 = new ArrayList<>();

        //第三栏第一个
        menuMap31.put("name", menu31.getName());
        menuMap31.put("type", menu31.getType());
        menuMap31.put("url", menu31.getUrl());
        subMenuMapList3.add(menuMap31);

        //第三栏第二个
        menuMap32.put("name", menu32.getName());
        menuMap32.put("type", menu32.getType());
        menuMap32.put("url", menu32.getUrl());
        subMenuMapList3.add(menuMap32);

        menuMap3.put("name", menu3.getName());
        menuMap3.put("sub_button", subMenuMapList3);

        wechatMenuMapList.add(menuMap1);
        wechatMenuMapList.add(menuMap2);
        wechatMenuMapList.add(menuMap3);

        wechatMenuMap.put("button", wechatMenuMapList);

        return wechatMenuMap;
    }

}
