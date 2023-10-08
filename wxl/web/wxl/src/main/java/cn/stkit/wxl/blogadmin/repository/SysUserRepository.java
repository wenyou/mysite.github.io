package cn.stkit.wxl.blogadmin.repository;

import cn.stkit.wxl.blogadmin.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysUserRepository
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 5:09 下午
 */

public interface SysUserRepository extends JpaRepository<SysUser, Integer>, JpaSpecificationExecutor<SysUser>
{
    SysUser findByUserName(String userName);

    SysUser findByPhone(String phone);

    SysUser findByUserId(Integer userId);
}
