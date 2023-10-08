package cn.stkit.wxl.blogadmin.service.impl;

import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.SysRole;
import cn.stkit.wxl.blogadmin.repository.SysRoleRepository;
import cn.stkit.wxl.blogadmin.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysRoleServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:34 下午
 */
@Service
public class SysRoleServiceImpl implements SysRoleService
{
    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    public SysRole findSysRoleByRoleId(Integer roleId)
    {
        Optional<SysRole> sysRoleOptional = sysRoleRepository.findById(roleId);
        if(sysRoleOptional.isPresent()) {
            return sysRoleOptional.get();
        }
        return null;
    }

    @Override
    public List<SysRole> findAllByEnabled()
    {
        return sysRoleRepository.findAllByEnabled(Constants.YES);
    }
}
