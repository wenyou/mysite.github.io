package cn.stkit.wxl.blogadmin.constants;

import cn.stkit.wxl.blogadmin.entity.SysRole;

import java.util.List;
import java.util.Optional;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file Constants
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 5:28 下午
 */

public class Constants
{
    public static final Byte YES = 1;
    public static final Byte NO = 0;

    //起始页下标
    public static final int startPage = 0;
    //默认每页大小
    public static final int defaultPageSize = 10;

    public static final String SYS_STATIC_PATH = "static/";
    //用户头像上传路径
    public static final String USER_AVATAR_PATH = "static/upload/avatar/";
    public static final String USER_AVATAR_URI = "upload/avatar/";
    public static final String UPLOAD_IMAGE_PATH = "static/upload/imgs/";

    //菜单标示
    public static final Integer INDEX_MENU_FLAG = 1;
    public static final Integer ARTICLE_MENU_FLAG = 2;
    public static final Integer TAG_MENU_FLAG = 3;
    public static final Integer LINK_MENU_FLAG = 4;
    public static final Integer USER_MENU_FLAG = 5;
    public static final Integer MESSAGE_MENU_FLAG = 6;
    public static final Integer SYSTEM_MENU_FLAG = 7;
    public static final Integer RESOURCE_MENU_FLAG = 8;

    //权限
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SYSTEMADMIN = "ROLE_SYSTEMADMIN";
    public static final String ROLE_SUPERADMIN = "ROLE_SUPERADMIN";

    //权限列表
    public static final Integer[] ROLE_USER_LIST = {INDEX_MENU_FLAG, ARTICLE_MENU_FLAG, TAG_MENU_FLAG};
    public static final Integer[] ROLE_ADMIN_LIST = {INDEX_MENU_FLAG, ARTICLE_MENU_FLAG, TAG_MENU_FLAG, LINK_MENU_FLAG, USER_MENU_FLAG, MESSAGE_MENU_FLAG,RESOURCE_MENU_FLAG};
    public static final Integer[] ROLE_SYSTEMADMIN_LIST = {INDEX_MENU_FLAG, SYSTEM_MENU_FLAG};
    public static final Integer[] ROLE_SUPERADMIN_LIST = {INDEX_MENU_FLAG, ARTICLE_MENU_FLAG, TAG_MENU_FLAG, LINK_MENU_FLAG, USER_MENU_FLAG, MESSAGE_MENU_FLAG, SYSTEM_MENU_FLAG,RESOURCE_MENU_FLAG};

    public static Integer[] getUserRoleList(List<SysRole> roleList)
    {
        Optional<SysRole> superAdminOptional = roleList.stream().filter(role -> ROLE_SUPERADMIN.equals(role.getRoleName())).findFirst();
        Optional<SysRole> adminOptional = roleList.stream().filter(role->ROLE_ADMIN.equals(role.getRoleName())).findFirst();
        Optional<SysRole> systemAdminOptional = roleList.stream().filter(role->ROLE_SYSTEMADMIN.equals(role.getRoleName())).findFirst();
        Optional<SysRole> userOptional = roleList.stream().filter(role->ROLE_USER.equals(role.getRoleName())).findFirst();
        if(superAdminOptional.isPresent()) {
            return ROLE_SUPERADMIN_LIST;
        } else if(adminOptional.isPresent() && systemAdminOptional.isPresent()) {
            return ROLE_SUPERADMIN_LIST;
        } else if(adminOptional.isPresent()) {
            return ROLE_ADMIN_LIST;
        } else if(userOptional.isPresent() && systemAdminOptional.isPresent()) {
            return ROLE_SUPERADMIN_LIST;
        } else if(systemAdminOptional.isPresent()) {
            return ROLE_SYSTEMADMIN_LIST;
        } else {
            return ROLE_USER_LIST;
        }
    }
}