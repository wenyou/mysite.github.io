package cn.stkit.wxl.blogadmin.service;

import cn.stkit.wxl.blogadmin.entity.SysRole;

import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysRoleService
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:32 下午
 */

public interface SysRoleService
{
    SysRole findSysRoleByRoleId(Integer roleId);

    List<SysRole> findAllByEnabled();
}
