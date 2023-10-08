package cn.stkit.wxl.utils;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ThymeleafUtil
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/17 3:09 下午
 */

public class ThymeleafUtil
{
    /**
     * 如果前面的值为空，则使用后面的值代替
     * @param before
     * @param back
     * @return
     */
    public static String nullToBackValue(String before, Object back)
    {
        if(ValidateUtil.isEmptyString(before)) {
            return back.toString();
        } else {
            return before;
        }
    }

    /**
     * 返回用户状态表述文字
     * @param userStatus
     * @return
     */
    public static String getUserStatusText(Byte userStatus)
    {
        String str = "";
        switch (userStatus) {
            case 0:
                str = "未激活";
                break;
            case 1:
                str =  "正常";
                break;
            case 2:
                str =  "冻结";
                break;
            case 3:
                str = "禁止登录";
                break;
            case 4:
                str =  "黑名单";
                break;
            case 5:
                str =  "删除";
                break;
            default:
                str =  "未知";
        }
        return str;
    }
}