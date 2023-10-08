package cn.stkit.wxl.blogadmin.init;

import cn.stkit.wxl.blog.entity.BlogConfig;
import cn.stkit.wxl.blog.service.BlogConfigService;
import cn.stkit.wxl.blogadmin.entity.SysRole;
import cn.stkit.wxl.blogadmin.entity.SysUser;
import cn.stkit.wxl.blogadmin.repository.SysRoleRepository;
import cn.stkit.wxl.blogadmin.service.SysUserService;
import cn.stkit.wxl.utils.Md5Util;
import cn.stkit.wxl.utils.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file InitBlogAdminData
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/3 3:15 下午
 */
@Component
public class InitBlogAdminData
{
    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private BlogConfigService blogConfigService;

    @PostConstruct
    private void intRoleAndUserAndBlogConfigData()
    {
        System.out.println("===初始化Blog后台系统数据 执行（InitBlogAdminData#intRoleAndUserAndBlogConfigData方法）===");
        int timestamp =  TimeUtil.getNowTimestamp();

        SysRole sysRole = sysRoleRepository.findSysRoleByRoleId(1);
        if(sysRole == null) {
            SysRole role1 = new SysRole(1, "ROLE_USER", (byte) 1, timestamp, 0);
            SysRole role2 = new SysRole(2, "ROLE_ADMIN", (byte) 1, timestamp, 0);
            SysRole role3 = new SysRole(3, "ROLE_SYSTEMADMIN", (byte) 1, timestamp, 0);
            SysRole role4 = new SysRole(4, "ROLE_SUPERADMIN", (byte) 1, timestamp, 0);
            List<SysRole> roleList = new ArrayList<>();
            roleList.add(role1);
            roleList.add(role2);
            roleList.add(role3);
            roleList.add(role4);
            sysRoleRepository.saveAll(roleList);

            SysUser user = sysUserService.findSysUserByUserId(1);
            if(user == null) {
                String password = Md5Util.Md5Str("123456", "17333298805".substring(7));
                sysUserService.saveOrUpdateUser(
                        new SysUser(1,"admin", password, "80825745@qq.com", (byte)1, roleList,"17333298805", 0)
                );
            }
        }

        BlogConfig blogConfig = blogConfigService.findBlogConfig();
        if(blogConfig == null) {
            blogConfigService.saveBlogConfig(
                    new BlogConfig(1, "微博客-合肥蔓朵", "芊忆君子", 1, "皖ICP备17010158号-2","stkit.cn", "80825745@qq.com",timestamp,0,0,0)
            );
        }
    }
}