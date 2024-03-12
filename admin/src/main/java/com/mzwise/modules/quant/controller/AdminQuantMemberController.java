package com.mzwise.modules.quant.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.quant.service.AdminQuantMemberService;
import com.mzwise.modules.quant.vo.AdminQuantMemberVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author piao
 * @Date 2021/7/1 18:43
 * @Description
 */
@Api(tags = "量化用户")
@RestController
@RequestMapping("/admin/quant-member")
public class AdminQuantMemberController {
    @Autowired
    private AdminQuantMemberService adminQuantMemberService;

    @ApiOperation("展示量化用户列表")
    @GetMapping("/list")
    public CommonResult<Page<AdminQuantMemberVO>> listQuantMember(
            @ApiParam("昵称") String nickname,
            @ApiParam("手机号") String phone,
            @ApiParam("邮件") String email,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(adminQuantMemberService.listQuantMember(nickname, phone, email, pageNum, pageSize));
    }
}
