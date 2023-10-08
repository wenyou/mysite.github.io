package cn.stkit.wxl.blogadmin.controller;

import cn.stkit.wxl.blog.util.FileUtil;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.Pages;
import cn.stkit.wxl.blogadmin.entity.SysRole;
import cn.stkit.wxl.blogadmin.entity.SysUser;
import cn.stkit.wxl.blogadmin.service.SysRoleService;
import cn.stkit.wxl.blogadmin.service.SysUserService;
import cn.stkit.wxl.object.ResponseVO;
import cn.stkit.wxl.utils.ModelAndViewUtil;
import cn.stkit.wxl.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file UserController
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/17 10:00 上午
 */
@Controller
@RequestMapping("/blogAdmin/user")
public class UserController
{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("/list")
    public String list(Integer pageNumber,Integer userId, String userName, String phone, String wechatNickname, String openId,String realName,Byte userStatus, Model model)
    {
        Page<SysUser> sysUserPage = sysUserService.findAllBySearch(Pages.defaultPages(pageNumber),userId, userName, phone, wechatNickname, openId, realName, userStatus);

        model.addAttribute("userList", sysUserPage.getContent());
        model.addAttribute("totalCount", sysUserPage.getTotalElements());
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("phone", phone);
        model.addAttribute("wechatNickname", wechatNickname);
        model.addAttribute("openId", openId);
        model.addAttribute("userStatus", userStatus);

        model.addAttribute("menuFlag", Constants.USER_MENU_FLAG);

        model.addAttribute("title", "用户管理");

        return "blog/admin/user";
    }

    @GetMapping("/saveOrUpdatePage")
    public String saveOrUpdatePage(Model model, Integer userId)
    {
        List<SysRole> sysRoleList = sysRoleService.findAllByEnabled();
        SysUser sysUser = new SysUser();
        if(userId != null) {
            sysUser = sysUserService.findSysUserByUserId(userId);
            List userRoleList = sysUser.getRoleList();
            for(SysRole role : sysRoleList) {
                if(userRoleList.contains(role)) {
                    role.setHave(Constants.YES);
                }
            }
        } else {
            sysUser.setUserId(0);
        }

        model.addAttribute("user", sysUser);
        model.addAttribute("menuFlag", Constants.USER_MENU_FLAG);
        model.addAttribute("roleList", sysRoleList);

        return "blog/admin/userEdit";
    }

    @PostMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseVO saveOrUpdateUser(@RequestBody SysUser sysUser)
    {
        String result = sysUserService.adminSaveOrUpdateUser(sysUser);
        if("success".equals(result)) {
            return ModelAndViewUtil.success("保存用户信息成功！", "/blogAdmin/user/list");
        } else {
            return ModelAndViewUtil.error(result);
        }
    }

    @PostMapping("/updateUserEnabled")
    @ResponseBody
    public void updateUserEnabled(Integer userId, Byte enabled)
    {
        sysUserService.updateUserUserStatus(userId, enabled);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void delete(Integer userId)
    {
        sysUserService.deleteUser(userId);
    }

    //@RequestParam("fileAvatar") MultipartFile fileAvatar
    @PostMapping("/uploadAvatar")
    @ResponseBody
    public ResponseVO uploadAvatar(@RequestParam(value = "fileAvatar", required = false) MultipartFile fileAvatar, @RequestParam("userId") Integer userId, @RequestParam("loginUserId") Integer loginUserId)
    {
        try{
            if(fileAvatar == null || fileAvatar.isEmpty()) {
                return ModelAndViewUtil.error("请选择要上传的图片！");
            }
            //获取文件名
            String fileName = fileAvatar.getOriginalFilename();
            logger.info("上传的文件名为："+ fileName);
            //构建新文件名
            String fileNameNew = "a_"+loginUserId+"_"+userId+"_"+ RandomUtil.getAlphanumeric(4) + "." +fileName.substring(fileName.lastIndexOf(".") + 1);

            //设置文件存储路径
            String path = FileUtil.getUploadAvatarPath() + fileNameNew;
            File dest = new File(path);
            //检测是否存在目录
            if(!dest.getParentFile().exists()) {
                //新建文件夹
                dest.getParentFile().mkdirs();
            }
            //文件写入
            fileAvatar.transferTo(dest);

            return ModelAndViewUtil.success("文件上传成功！",  Constants.USER_AVATAR_URI+fileNameNew);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ModelAndViewUtil.error("上传失败！");
    }
}