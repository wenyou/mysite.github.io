package cn.stkit.wxl.service.menu;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file MenuService
 * @desc 对公众号的菜单进行的操作服务接口
 * @website http://www.stkit.cn/
 * @date 2020/6/15 10:32 上午
 */

public interface MenuService
{

    JSONObject getMenu(String accessToken);

    int createMenu(Map<String, Object> menu, String accessToken);

    int deleteMenu(String accessToken);
}
