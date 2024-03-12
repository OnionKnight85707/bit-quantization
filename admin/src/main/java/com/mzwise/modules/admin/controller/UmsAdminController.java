package com.mzwise.modules.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonPage;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.dto.IdAndNameVo;
import com.mzwise.common.exception.ApiException;
import com.mzwise.constant.AdminLogActionEnum;
import com.mzwise.constant.AdminLogModuleEnum;
import com.mzwise.modules.admin.dto.PasswordParam;
import com.mzwise.modules.admin.dto.UmsAdminLoginParam;
import com.mzwise.modules.admin.dto.UmsAdminParam;
import com.mzwise.modules.admin.dto.UpdateAdminPasswordParam;
import com.mzwise.modules.admin.entity.UmsAdmin;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.entity.UmsRole;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.admin.service.UmsAdminService;
import com.mzwise.modules.admin.service.UmsRoleService;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.admin.service.impl.GoogleAuthenticatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Administrator
 * @Date 2021/02/02
 */

@Api(tags = "后台用户管理")
@RestController
@RequestMapping("/admin")
public class UmsAdminController {
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private UmsRoleService roleService;
    @Autowired
    private UmsAdminLogService adminLogService;

    @Autowired
    private GoogleAuthenticatorService googleAuthenticatorService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult<UmsAdmin> register(@Validated @RequestBody UmsAdminParam umsAdminParam) {
        UmsAdmin umsAdmin = adminService.register(umsAdminParam);
        if (umsAdmin == null) {
            return CommonResult.failed();
        }
        // 记录日志
        UmsAdminParam param = new UmsAdminParam();
        BeanUtils.copyProperties(umsAdminParam, param);
        param.setPassword("null");
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.ADD.getValue(), AdminLogModuleEnum.ADMIN_ADD.getValue(),
                null, JSON.toJSONString(param), "管理员添加"));
        return CommonResult.success(umsAdmin);
    }

    @ApiOperation("登录以后返回token")
    @PostMapping("/login")
    @AnonymousAccess
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {

        try {
            googleAuthenticatorService.verify(umsAdminLoginParam.getCode());
        } catch (Exception e) {
            return CommonResult.validateFailed("code_wrong");
        }


        String token = adminService.login(umsAdminLoginParam.getUsername(), umsAdminLoginParam.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("username_password_wrong");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("刷新token")
    @GetMapping("/refreshToken")
    public CommonResult refreshToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String refreshToken = adminService.refreshToken(token);
        if (refreshToken == null) {
            return CommonResult.failed("token已经过期！");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);
        return CommonResult.success(tokenMap);
    }

    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/info")
    public CommonResult getAdminInfo(Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
        String username = principal.getName();
        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>();
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));
        data.put("icon", umsAdmin.getIcon());
        List<UmsRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }
        return CommonResult.success(data);
    }

    @ApiOperation("登出功能")
    @PostMapping("/logout")
    public CommonResult logout() {
        return CommonResult.success(null);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsAdmin>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                   @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsAdmin> adminList = adminService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @ApiOperation("管理员列表")
    @GetMapping("/adminList")
    public CommonResult<List<IdAndNameVo>> adminList(@RequestParam String name) {
        List<IdAndNameVo> list = adminService.adminList(name);
        return CommonResult.success(list);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping("/{id}")
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getById(id);
        return CommonResult.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody UmsAdmin admin) {
        boolean success = adminService.update(id, admin);
        if (success) {
            // 记录日志
            UmsAdmin umsAdmin = new UmsAdmin();
            UmsAdmin byId = adminService.getById(admin.getId());
            BeanUtils.copyProperties(byId, umsAdmin);
            umsAdmin.setPassword("null");

            UmsAdmin afterUmsAdmin = new UmsAdmin();
            BeanUtils.copyProperties(admin, afterUmsAdmin);
            afterUmsAdmin.setPassword("null");
            adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.ADMIN_UPDATE.getValue(),
                    JSON.toJSONString(umsAdmin), JSON.toJSONString(afterUmsAdmin), "管理员修改"));
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改指定用户密码")
    @PostMapping("/updatePassword")
    public CommonResult updatePassword(@Validated @RequestBody UpdateAdminPasswordParam updatePasswordParam) {
        int status = adminService.updatePassword(updatePasswordParam);
        if (status > 0) {
            return CommonResult.success(status);
        } else if (status == -1) {
            return CommonResult.failed("提交参数不合法");
        } else if (status == -2) {
            return CommonResult.failed("找不到该用户");
        } else if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("删除指定用户信息")
    @PostMapping("/delete/{id}")
    public CommonResult delete(@PathVariable Long id) {
        boolean success = adminService.delete(id);
        if (success) {
            // 记录日志
            adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.ADMIN_DELETE.getValue(),
                    null, JSON.toJSONString(id), "管理员删除"));
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改帐号状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        boolean success = adminService.update(id, umsAdmin);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/role/update")
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改密码")
    @PostMapping("/updateMyPassword")
    public CommonResult updateMyPassword(@Validated @RequestBody PasswordParam passwordParam) {
        Long adminId = SecurityUtils.getCurrentAdminId();
        if (adminId == null) {
            throw new ApiException("未登录");
        }
        int status = adminService.updatePassword(adminId, passwordParam);
        if (status > 0) {
            return CommonResult.success(status);
        }
        if (status == -3) {
            return CommonResult.failed("旧密码错误");
        } else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping("/role/{adminId}")
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }
}
