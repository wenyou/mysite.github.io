package cn.stkit.wxl.blogadmin.service.impl;

import cn.stkit.wxl.blog.util.FileUtil;
import cn.stkit.wxl.blogadmin.constants.Constants;
import cn.stkit.wxl.blogadmin.entity.Pages;
import cn.stkit.wxl.blogadmin.entity.SysRole;
import cn.stkit.wxl.blogadmin.entity.SysUser;
import cn.stkit.wxl.blogadmin.repository.SysUserRepository;
import cn.stkit.wxl.blogadmin.service.SysRoleService;
import cn.stkit.wxl.blogadmin.service.SysUserService;
import cn.stkit.wxl.utils.Md5Util;
import cn.stkit.wxl.utils.TimeUtil;
import cn.stkit.wxl.utils.ValidateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author youwenzhang (80825745@qq.com)
 * @version 1.0
 * @file SysUserServiceImpl
 * @desc
 * @website http://www.stkit.cn/
 * @date 2020/7/2 6:27 下午
 */
@Service
public class SysUserServiceImpl implements SysUserService
{
    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public Page<SysUser> findAllBySearch(Pages pages, Integer userId, String userName)
    {
        Pageable pageable = PageRequest.of(pages.getPage(), pages.getPageSize(), Sort.Direction.DESC, "userId");
        return sysUserRepository.findAll(this.getWhereClause(userId, userName), pageable);
    }

    @Override
    public Page<SysUser> findAllBySearch(Pages pages, Integer userId, String userName, String phone, String wechatNickname, String openId, String realName, Byte userStatus)
    {
        Pageable pageable = PageRequest.of(pages.getPage(), pages.getPageSize(), Sort.Direction.DESC, "userId");
        return sysUserRepository.findAll(this.getWhereClause(userId, userName, phone, wechatNickname, openId, realName, userStatus), pageable);
    }

    @Override
    public SysUser findSysUserByUserId(Integer userId)
    {
        Optional<SysUser> optionalSysUser = sysUserRepository.findById(userId);
        if(optionalSysUser.isPresent()) {
            return optionalSysUser.get();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String saveOrUpdateUser(SysUser sysUser)
    {
        List<SysRole> roleList = new ArrayList<>();
        List<Integer> roleIds = sysUser.getRoleIdList();
        if(CollectionUtils.isNotEmpty(roleIds)) {
            for(Integer roleId : roleIds) {
                SysRole role = sysRoleService.findSysRoleByRoleId(roleId);
                roleList.add(role);
            }
            sysUser.setRoleList(roleList);
        }
        if(sysUser.getUserId() == null) {
            sysUser.setUserStatus(Constants.NO);
        }
        else if(sysUserRepository.findByUserName(sysUser.getUserName()) != null)
        {
            return "用户名已经存在！";
        }
        sysUserRepository.save(sysUser);
        return "保存成功";
    }

    @Override
    public int updateUserUserStatus(Integer userId, Byte userStatus)
    {
        SysUser sysUser = sysUserRepository.findById(userId).get();
        sysUser.setUserStatus(userStatus);
        sysUserRepository.save(sysUser);
        return 1;
    }

    @Override
    public int deleteUser(Integer userId)
    {
        sysUserRepository.deleteById(userId);
        return 1;
    }

    @Override
    public SysUser findByUserName(String userName)
    {
        return sysUserRepository.findByUserName(userName);
    }

    /**
     * 后台管理员添加或修改用户信息
     * @param sysUser
     * @return
     */
    @Override
    public String adminSaveOrUpdateUser(SysUser sysUser)
    {
        List<SysRole> roleList = new ArrayList<>();
        List<Integer> roleIds = sysUser.getRoleIdList();
        if(CollectionUtils.isNotEmpty(roleIds)) {
            for(Integer roleId : roleIds) {
                SysRole role = sysRoleService.findSysRoleByRoleId(roleId);
                roleList.add(role);
            }
            sysUser.setRoleList(roleList);
        }

        int timestamp = TimeUtil.getNowTimestamp();

        SysUser sysUserUserName = sysUserRepository.findByUserName(sysUser.getUserName());
        SysUser sysUserPhone = sysUserRepository.findByPhone(sysUser.getPhone());

        if(!ValidateUtil.isPhoneNum(sysUser.getPhone())) {
            return "手机号码格式不正确！";
        }

        if(ValidateUtil.isEmptyString(sysUser.getUserName()) || sysUser.getUserName().length() < 3) {
            return "用户名不能少于3个字符！";
        }

        if(ValidateUtil.isEmptyString(sysUser.getOpenId())) {
            sysUser.setOpenId(null);
        }
        if(ValidateUtil.isEmptyString(sysUser.getWechatNickname())) {
            sysUser.setWechatNickname(null);
        }
        if(ValidateUtil.isEmptyString(sysUser.getEmail())) {
            sysUser.setEmail(null);
        }
        if(ValidateUtil.isEmptyString(sysUser.getUserPassword())) {
            sysUser.setUserPassword(null);
        }

        if(sysUser.getUserId() == null) {
            //新增用户处理
            sysUser.setCreatedTime(timestamp);
            sysUser.setCreatedUserId(sysUser.getLoginUserId());

            if(ValidateUtil.isEmptyString(sysUser.getUserPassword())) {
                return "创建新用户密码不能少！";
            }
            //创建新用户，生成用户密码
            sysUser.setUserPassword(Md5Util.Md5Str(sysUser.getUserPassword(), sysUser.getPhone().substring(7)));

            if(sysUserUserName != null) {
                return "用户名已经存在！";
            }
            if(sysUserPhone != null) {
                return "用户手机号码已经存在！";
            }
            //如果没有上传头像图片，则把数据表头像字段值设为null
            if(ValidateUtil.isEmptyString(sysUser.getUserAvatar())) {
                sysUser.setUserAvatar(null);
            }
        }
        else
        {
            //修改用户信息处理
            sysUser.setUpdatedTime(timestamp);
            sysUser.setUpdatedUserId(sysUser.getLoginUserId());
            //原表中用户信息
            SysUser sysUserOld = sysUserRepository.findByUserId(sysUser.getUserId());

            if(sysUserUserName != null && sysUserUserName.getUserId() != sysUser.getUserId()) {
                return "该用户名已经被其他用户使用，请更换！";
            }
            if(sysUserPhone != null && sysUserPhone.getUserId() != sysUser.getUserId()) {
                return "该手机号码已经被其他用户使用，请更换！";
            }
            if(!ValidateUtil.isEmptyString(sysUser.getUserPassword())) {
                //修改用户信息时，如果输入了新密码，则更新用户密码
                sysUser.setUserPassword(Md5Util.Md5Str(sysUser.getUserPassword(), sysUser.getPhone().substring(7)));
            } else {
                if(sysUser.getPhone().equals(sysUserOld.getPhone())) {
                    sysUser.setUserPassword(sysUserOld.getUserPassword());
                } else {
                    return "用户手机号码变更，密码也需要更新，请输入新的密码！";
                }
            }
            //用户头像处理
            if(!ValidateUtil.isEmptyString(sysUser.getUserAvatar()) && !sysUser.getUserAvatar().equals(sysUserOld.getUserAvatar())) {
                //上传了新头像，删除旧头像文件，如果有的话
                if(!ValidateUtil.isEmptyString(sysUserOld.getUserAvatar())) {
                    //删除原表中头像对应的旧文件
                    FileUtil.deleteDir(FileUtil.getClassPath()+Constants.SYS_STATIC_PATH+sysUserOld.getUserAvatar());
                }
            } else if(ValidateUtil.isEmptyString(sysUser.getUserAvatar())) {
                //没有上传新头像，继续使用旧头像
                sysUser.setUserAvatar(sysUserOld.getUserAvatar());
            }
        }
        sysUserRepository.save(sysUser);
        return "success";
    }

    /**
     * 动态生成where语句
     * @param userId
     * @param userName
     * @return
     */
    private Specification<SysUser> getWhereClause(Integer userId, String userName)
    {
        return new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(userId != null) {
                    predicates.add(
                            criteriaBuilder.or(
                                    criteriaBuilder.equal(root.get("userId"), userId)
                            )
                    );
                }
                if(StringUtils.isNotBlank(userName)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("userName"), userName + "%"))
                    );
                }
                Predicate[] predicates_arr = new Predicate[predicates.size()];

                return criteriaQuery.where(predicates.toArray(predicates_arr)).getRestriction();
            }
        };
    }

    private Specification<SysUser> getWhereClause(Integer userId, String userName, String phone, String wechatNickname, String openId, String realName, Byte userStatus)
    {
        return new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(userId != null) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.equal(root.get("userId"), userId))
                    );
                }
                if(StringUtils.isNotBlank(userName)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("userName"), userName + "%"))
                    );
                }
                if(StringUtils.isNotBlank(phone)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("phone"), phone + "%"))
                    );
                }
                if(StringUtils.isNotBlank(wechatNickname)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("wechatNickname"), wechatNickname + "%"))
                    );
                }
                if(StringUtils.isNotBlank(openId)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("openId"), openId + "%"))
                    );
                }
                if(StringUtils.isNotBlank(realName)) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.like(root.get("realName"), realName + "%"))
                    );
                }
                if(userStatus != null) {
                    predicates.add(
                            criteriaBuilder.or(criteriaBuilder.equal(root.get("userStatus"), userStatus))
                    );
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return criteriaQuery.where(predicates.toArray(pre)).getRestriction();
            }
        };
    }
}