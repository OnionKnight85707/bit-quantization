package com.mzwise.modules.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonPage;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.AdminLogActionEnum;
import com.mzwise.constant.AdminLogModuleEnum;
import com.mzwise.modules.admin.dto.UmsAdminParam;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.entity.UmsMenu;
import com.mzwise.modules.admin.entity.UmsResource;
import com.mzwise.modules.admin.entity.UmsRole;
import com.mzwise.modules.admin.mapper.UmsRoleMenuRelationMapper;
import com.mzwise.modules.admin.mapper.UmsRoleResourceRelationMapper;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.admin.service.UmsMenuService;
import com.mzwise.modules.admin.service.UmsResourceService;
import com.mzwise.modules.admin.service.UmsRoleService;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台用户角色管理
 *
 * @author admin
 * @date 2018/9/30
 */
@Api(tags = "后台用户角色管理")
@RestController
@RequestMapping("/role")
public class UmsRoleController {
    @Autowired
    private UmsRoleService roleService;
    @Autowired
    private UmsAdminLogService adminLogService;
    @Autowired
    private UmsMenuService umsMenuService;
    @Autowired
    private UmsResourceService umsResourceService;
    @Autowired
    private UmsRoleMenuRelationMapper umsRoleMenuRelationMapper;
    @Autowired
    private UmsRoleResourceRelationMapper umsRoleResourceRelationMapper;

    @ApiOperation("添加角色")
    @PostMapping("/create")
    public CommonResult create(@RequestBody UmsRole role) {
        boolean success = roleService.create(role);
        if (success) {
            // 记录日志
            adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.ADD.getValue(), AdminLogModuleEnum.ROLE_MANAGE.getValue(),
                    null, JSON.toJSONString(role), "角色管理-增加"));
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("修改角色")
    @PostMapping("/update/{id}")
    public CommonResult update(@PathVariable Long id, @RequestBody UmsRole role) {
        role.setId(id);
        boolean success = roleService.updateById(role);
        if (success) {
            // 记录日志
            UmsRole umsRole = new UmsRole();
            UmsRole byId = roleService.getById(role.getId());
            BeanUtils.copyProperties(byId, umsRole);
            adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.ROLE_MANAGE.getValue(),
                    JSON.toJSONString(umsRole), JSON.toJSONString(role), "角色管理-修改"));
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("批量删除角色")
    @PostMapping("/delete")
    public CommonResult delete(@RequestParam("ids") List<Long> ids) {
        boolean success = roleService.delete(ids);
        if (success) {
            // 记录日志
            adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.ROLE_MANAGE.getValue(),
                    null, JSON.toJSONString(ids), "角色管理-删除"));
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }


    @ApiOperation("获取所有角色")
    @GetMapping("/listAll")
    public CommonResult<List<UmsRole>> listAll() {
        List<UmsRole> roleList = roleService.list();
        return CommonResult.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping("/list")
    public CommonResult<CommonPage<UmsRole>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<UmsRole> roleList = roleService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @ApiOperation("修改角色状态")
    @PostMapping("/updateStatus/{id}")
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsRole umsRole = new UmsRole();
        umsRole.setId(id);
        umsRole.setStatus(status);
        boolean success = roleService.updateById(umsRole);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping("/listMenu/{roleId}")
    public CommonResult<List<UmsMenu>> listMenu(@PathVariable Long roleId) {
        List<UmsMenu> roleList = roleService.listMenu(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("获取角色相关资源")
    @GetMapping("/listResource/{roleId}")
    public CommonResult<List<UmsResource>> listResource(@PathVariable Long roleId) {
        List<UmsResource> roleList = roleService.listResource(roleId);
        return CommonResult.success(roleList);
    }

    @ApiOperation("给角色分配菜单")
    @PostMapping("/allocMenu")
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        Map<Long,StringBuilder> beforeMap = new HashMap<Long,StringBuilder>();
        StringBuilder beforeTitle = new StringBuilder("");
        List<Long> menus = umsRoleMenuRelationMapper.getAllMenuIdByRoleId(roleId);
        int count = roleService.allocMenu(roleId, menuIds);
        // 记录日志
        // 修改前
        for (Long id : menus) {
            UmsMenu menu = umsMenuService.getById(id);
            beforeTitle.append(menu.getTitle() + " ");
        }
        beforeMap.put(roleId, beforeTitle);
        // 修改后
        Map<Long,StringBuilder> AfterMap = new HashMap<Long,StringBuilder>();
        StringBuilder AtferTitle = new StringBuilder("");
        for (Long id : menuIds) {
            UmsMenu menu = umsMenuService.getById(id);
            AtferTitle.append(menu.getTitle() + " ");
        }
        AfterMap.put(roleId, AtferTitle);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.MENU_MANAGE.getValue(),
                JSON.toJSONString(beforeMap), JSON.toJSONString(AfterMap), "菜单管理"));
        return CommonResult.success(count);
    }

    @ApiOperation("给角色分配资源")
    @PostMapping("/allocResource")
    public CommonResult allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        Map<Long,StringBuilder> beforeMap = new HashMap<Long,StringBuilder>();
        StringBuilder beforeDescription = new StringBuilder("");
        List<Long> menus = umsRoleResourceRelationMapper.getAllResourceIdByIdRoleId(roleId);
        int count = roleService.allocResource(roleId, resourceIds);
        // 记录日志
        // 修改前
        for (Long id : menus) {
            UmsResource umsResource = umsResourceService.getById(id);
            beforeDescription.append(umsResource.getDescription() + " ");
        }
        beforeMap.put(roleId, beforeDescription);
        // 修改后
        Map<Long,StringBuilder> AfterMap = new HashMap<Long,StringBuilder>();
        StringBuilder AtferDescription = new StringBuilder("");
        for (Long id : resourceIds) {
            UmsResource umsResource = umsResourceService.getById(id);
            AtferDescription.append(umsResource.getDescription() + " ");
        }
        AfterMap.put(roleId, AtferDescription);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.RESOURCE_MANAGE.getValue(),
                JSON.toJSONString(beforeMap), JSON.toJSONString(AfterMap), "资源管理"));
        return CommonResult.success(count);
    }

}
