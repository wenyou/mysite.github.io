package cn.stkit.wxl.service.menu;

import cn.stkit.wxl.utils.WeChatConst;
import cn.stkit.wxl.utils.WeChatUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MenuServiceImpl
 * @desc 对公众号的菜单进行的操作实现
 * @website http://www.stkit.cn/
 * @date 2020/6/15 10:34 上午
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService
{

    private static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    /**
     * 查询菜单
     * @param accessToken  有效的access_token
     * @return
     */
    @Override
    public JSONObject getMenu(String accessToken)
    {
        //拼装查询菜单的url
        String url = WeChatConst.MENU_GET_URL.replace("ACCESS_TOKEN", accessToken);
        //调用接口查询菜单
        JSONObject jsonObject = WeChatUtil.httpRequest(url, "GET", null);
        return jsonObject;
    }

    /**
     * 创建菜单（替换旧菜单）
     * @param menu 菜单对象
     * @param accessToken 有效的access_token
     * @return 0 表示成功，其他值表示失败
     */
    @Override
    public int createMenu(Map<String, Object> menu, String accessToken)
    {
        int result = 0;
        //拼装创建菜单的url
        String url = WeChatConst.MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
        //将菜单对象转换成json字符串
        String jsonMenu =  JSONObject.fromObject(menu).toString();
        //调用接口创建菜单
        JSONObject jsonObject = WeChatUtil.httpRequest(url, "POST", jsonMenu);

        if(null != jsonObject) {
            if(0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                logger.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
                logger.error("****"+jsonMenu+"****");
            }
        }
        return result;
    }

    /**
     * 删除菜单
     * @param accessToken 有效的access_token
     * @return 0 表示成功，其他值表示失败
     */
    @Override
    public int deleteMenu(String accessToken)
    {
        int result = 0;
        //拼装删除菜单的url
        String url = WeChatConst.MENU_DELETE_URL.replace("ACCESS_TOKEN", accessToken);
        //调用接口删除菜单
        JSONObject jsonObject = WeChatUtil.httpRequest(url, "GET", null);

        if(null != jsonObject) {
            if(0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                logger.error("删除菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }

}