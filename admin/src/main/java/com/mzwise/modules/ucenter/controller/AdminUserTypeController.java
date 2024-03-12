package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UniUserType;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UniUserTypeService;
import com.mzwise.modules.ucenter.vo.AdminMemberUserTypeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 666
 * @Date 2022/08/03
 */
@Api(tags = "用户类别")
@RestController
@RequestMapping("/user/type")
public class AdminUserTypeController {

    @Autowired
    private UniUserTypeService uniUserTypeService;

    @ApiOperation("查询全部用户类别")
    @GetMapping("/query/type")
    public CommonResult<List<UniUserType>> queryUserType() {
        return CommonResult.success(uniUserTypeService.queryUserType());
    }

    @ApiOperation("增加用户类别")
    @PostMapping("/add/tpye")
    public CommonResult addUserType(@RequestBody UniUserType uniUserType) {
        if (uniUserType.getLowerLimit().compareTo(uniUserType.getUpperLimit()) == 1) {
            throw new ApiException("上限值不能低于下限值");
        }
        uniUserTypeService.addUserType(uniUserType);
        return CommonResult.success();
    }

    @ApiOperation("修改用户类别")
    @PostMapping("/modify/type")
    public CommonResult modifyUserType(@RequestBody UniUserType uniUserType) {
        if (uniUserType.getLowerLimit().compareTo(uniUserType.getUpperLimit()) == 1) {
            throw new ApiException("上限值不能低于下限值");
        }
        uniUserTypeService.modifyUserType(uniUserType);
        return CommonResult.success();
    }

    @ApiOperation("修改 Member表 中用户类别")
    @PostMapping("/modify/user-type")
    public CommonResult modifyMemberType(@RequestParam Long memberId, @RequestParam Integer userTypeId) {
        uniUserTypeService.modifyMemberType(memberId, userTypeId);
        return CommonResult.success();
    }

    @ApiOperation("删除用户类别")
    @GetMapping("/del/type")
    public CommonResult delUserType(@RequestParam Integer userTypeId) {
        if (uniUserTypeService.checkUserTypeExist(userTypeId) == true) {
            throw new ApiException("当前用户类别存在关联，无法删除");
        }
        uniUserTypeService.removeById(userTypeId);
        return CommonResult.success();
    }

    @ApiOperation("分页查询会员对应用户等级")
    @GetMapping("/query/usertype")
    public CommonResult<Page<AdminMemberUserTypeVO>> queryAllUser(@RequestParam(value = "userTypeId",required = false) Integer userTypeId,
                                                                  @RequestParam(value = "email",required = false) String email,
                                                                  @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                                  @RequestParam(defaultValue = "10") @ApiParam("每页限制") Integer pageSize) {
        return CommonResult.success(uniUserTypeService.queryAllUser(userTypeId,email, pageNum, pageSize));
    }

}
