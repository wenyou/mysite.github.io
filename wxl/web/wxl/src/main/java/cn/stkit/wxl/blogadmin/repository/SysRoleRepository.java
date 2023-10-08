package cn.stkit.wxl.blogadmin.repository;

import cn.stkit.wxl.blogadmin.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysRoleRepository
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:30 下午
 */

public interface SysRoleRepository extends JpaRepository<SysRole, Integer>, JpaSpecificationExecutor<SysRole>
{
    List<SysRole> findAllByEnabled(Byte enabled);

    SysRole findSysRoleByRoleId(Integer roleId);
}
