package cn.stkit.wxl.utils;

import cn.stkit.wxl.object.ResponseVO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file ModelAndViewUtil
 * @desc 模型视图接口返回类，支持ModelAndView， ResponseVO和PageResult
 * @website http://www.stkit.cn/
 * @date 2020/6/15 2:43 下午
 */

public class ModelAndViewUtil
{
    public static ModelAndView view(String view)
    {
        return new ModelAndView(view);
    }

    public static ModelAndView redirect(String view)
    {
        return new ModelAndView("redirect:"+view);
    }

    public static ResponseVO success(String message, Object data)
    {
        return vo(WeChatConst.DEFAULT_SUCCESS_CODE, message, data);
    }

    public static ResponseVO error(String message)
    {
        return vo(WeChatConst.DEFAULT_ERROR_CODE, message, null);
    }

    public static ResponseVO vo(int code, String message, Object data)
    {
        return new ResponseVO<>(code, message, data);
    }

}