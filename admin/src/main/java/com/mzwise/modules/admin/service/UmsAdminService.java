package com.mzwise.modules.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.common.dto.IdAndNameVo;
import com.mzwise.modules.admin.dto.PasswordParam;
import com.mzwise.modules.admin.dto.UmsAdminParam;
import com.mzwise.modules.admin.dto.UpdateAdminPasswordParam;
import com.mzwise.modules.admin.entity.UmsAdmin;
import com.mzwise.modules.admin.entity.UmsResource;
import com.mzwise.modules.admin.entity.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 后台管理员管理Service
 * Created by admin on 2018/4/26.
 */
public interface UmsAdminService extends IService<UmsAdmin> {

    /**
     * 根据用户名获取后台管理员
     *
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 注册功能
     *
     * @param umsAdminParam
     * @return
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);

    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的token
     * @return
     */
    String refreshToken(String oldToken);

    /**
     * 根据用户名或昵称分页查询用户
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 管理员列表
     * @param name 管理员名称
     * @return
     */
    List<IdAndNameVo> adminList(String name);

    /**
     * 修改指定用户信息
     *
     * @param id
     * @param admin
     * @return
     */
    boolean update(Long id, UmsAdmin admin);

    /**
     * 删除指定用户
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 修改用户角色关系
     *
     * @param adminId
     * @param roleIds
     * @return
     */
    int updateRole(Long adminId, List<Long> roleIds);

    /**
     * 获取用户对于角色
     *
     * @param adminId
     * @return
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * 获取指定用户的可访问资源
     *
     * @param adminId
     * @return
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 修改指定用户密码
     *
     * @param updatePasswordParam
     * @return
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 修改自己的密码
     *
     * @param adminId
     * @param param
     * @return
     */
    int updatePassword(Long adminId, PasswordParam param);

    /**
     * 获取用户信息
     *
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);
}
