package cn.stkit.wxl.blogadmin.interceptors;

import cn.stkit.wxl.blog.entity.Message;
import cn.stkit.wxl.blog.service.ArticleService;
import cn.stkit.wxl.blog.service.BlogConfigService;
import cn.stkit.wxl.blog.service.MessageService;
import cn.stkit.wxl.blog.service.TagService;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.SysUser;
import cn.stkit.wxl.blogadmin.service.AuthenticationService;
import cn.stkit.wxl.blogadmin.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file AdminRequestInterceptor
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:06 下午
 */

@Component
public class AdminRequestInterceptor extends HandlerInterceptorAdapter
{

    /*@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getServletPath();
        System.out.println("====blogAdmin pre Handle== request.getServletPath() = " + path);
        if(path.matches("/blogAdmin/")) {
            return true;
        }else {
            return false;
        }
    }*/

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private BlogConfigService blogConfigService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private SysUserService sysUserService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        System.out.println("=====blogAdmin  postHandle====");
        if(modelAndView != null)
        {
            ModelMap modelMap = modelAndView.getModelMap();
            Date date = new Date();
            System.out.println("=====blogAdmin  postHandle 加载数据====");
            //加载数据
            Authentication authentication = authenticationService.getAuthentication();
            if(authentication != null)
            {
                String userName = authentication.getName();
                System.out.println("==authentication.getName():" + authentication.getName());
                modelMap.addAttribute("loginUserName", userName);
                SysUser sysUser = sysUserService.findByUserName(userName);
                if(sysUser != null) {
                    modelMap.addAttribute("loginUserId", sysUser.getUserId());
                    //赋值用户所拥有菜单集合，动态渲染菜单
                    modelMap.addAttribute("userRoleList", Constants.getUserRoleList(sysUser.getRoleList()));
                }
            }
            System.out.println("=====blogAdmin  postHandle 加载数据(authentication) : " + authentication);

            Integer messageCount = messageService.countByRead(Constants.NO);
            modelMap.addAttribute("messageCount", messageCount);
            Integer tagCount = tagService.countByTagInputDate(date);
            modelMap.addAttribute("tagCount", tagCount);
            Integer articleCount = articleService.countByArticleInputDate(date);
            modelMap.addAttribute("articleCount", articleCount);
            modelMap.addAttribute("sumCount", articleCount + tagCount + messageCount);

            List<Message> messageList = messageService.findAllByRead(Constants.NO);
            modelMap.addAttribute("mainbarMessageList", messageList);
            modelMap.addAttribute("mainbarMessageListCount", messageList.size());
            modelMap.addAttribute("blogConfig", blogConfigService.findBlogConfig());
        }
    }

}