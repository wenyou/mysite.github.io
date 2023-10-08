package cn.stkit.wxl.blog.interceptors;

import cn.stkit.wxl.blog.entity.BlogAccess;
import cn.stkit.wxl.blog.service.BlogAccessService;
import cn.stkit.wxl.blog.service.BlogConfigService;
import cn.stkit.wxl.blog.service.LinkService;
import cn.stkit.wxl.blog.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file RequestInterceptor
 * @desc 拦截器
 *          在拦截器中首先判断是否返回页面，如果返回页面，就渲染一些基本数据，如菜单集合、头部信息、底部信息等。
 * @website http://www.stkit.cn/
 * @date 2020/6/29 5:18 下午
 */

@Component
public class RequestInterceptor extends HandlerInterceptorAdapter
{
    private Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Autowired
    private BlogAccessService blogAccessService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogConfigService blogConfigService;

    @Autowired
    private LinkService linkService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        System.out.println("=====postHandle====");
        if(modelAndView != null)
        {
            ModelMap modelMap = modelAndView.getModelMap();
            logger.info("===正在更新网站访问次数===");
            BlogAccess blogAccess = blogAccessService.getByAccessDateIs(new Date());
            if(blogAccess != null) {
                blogAccess.setAccessCount(blogAccess.getAccessCount() + 1);
                blogAccessService.save(blogAccess);
            }

            logger.info("========加载公共（底部）数据=======");
            //标签列表
            modelMap.addAttribute("tagList", tagService.findAll());
            //友情链接
            modelMap.addAttribute("linkList", linkService.findAllByEnabled());
            //博客站点配置信息
            modelMap.addAttribute("blogConfig", blogConfigService.findBlogConfig());
        }
    }

}