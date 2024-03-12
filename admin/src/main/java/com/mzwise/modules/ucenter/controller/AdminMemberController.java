package com.mzwise.modules.ucenter.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.constant.AdminLogActionEnum;
import com.mzwise.constant.AdminLogModuleEnum;
import com.mzwise.constant.MemberStatusEnum;
import com.mzwise.constant.PartnerLevelEnum;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.ucenter.dto.BatchModifyUserStatusParam;
import com.mzwise.modules.ucenter.dto.ShowPartnerParam;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcPartnerLevel;
import com.mzwise.modules.ucenter.service.AdminMemberService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcPartnerLevelSerivce;
import com.mzwise.modules.ucenter.vo.AdminMemberVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author piao
 * @Date 2021/05/28
 */
@Api(tags = "后台会员管理")
@RestController
@RequestMapping("/admin/member")
public class AdminMemberController {

    @Autowired
    private AdminMemberService adminMemberService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UmsAdminLogService adminLogService;
    @Autowired
    private UcPartnerLevelSerivce partnerLevelSerivce;

    @ApiOperation("会员管理")
    @GetMapping("/member-manage")
    public CommonResult<Page<AdminMemberVO>> listAllMembersSelective(@RequestParam(required = false) @ApiParam(value = "昵称") String nickname,
                                                                     @RequestParam(required = false) @ApiParam(value = "手机号") String phone,
                                                                     @RequestParam(required = false) @ApiParam(value = "邮箱") String email,
                                                                     @RequestParam(required = false) @ApiParam(value = "上级id") Long parentId,
                                                                     @RequestParam(required = false) @ApiParam(value = "是否手机验证") Boolean phoneVerify,
                                                                     @RequestParam(required = false) @ApiParam(value = "是否邮件验证") Boolean emailVerify,
                                                                     @RequestParam(required = false) @ApiParam(value = "是否激活量化") Boolean isEffective,
                                                                     @RequestParam(required = false) @ApiParam(value = "是否激活矿机") Boolean miningIsEff,
                                                                     @RequestParam(required = false, defaultValue = "registration_time") @ApiParam(value = "排序字段名") String orderColumn,
                                                                     @RequestParam(required = false, defaultValue = "desc") @ApiParam(value = "排序方式") String orderDirection,
                                                                     @RequestParam(required = false) @ApiParam(value = "起始时间") java.sql.Date beginDate,
                                                                     @RequestParam(required = false) @ApiParam(value = "终止时间") java.sql.Date endDate,
                                                                     @RequestParam(required = false) @ApiParam(value = "是否禁用") MemberStatusEnum status,
                                                                     @RequestParam(defaultValue = "1") @ApiParam(value = "当前页") Integer pageNum,
                                                                     @RequestParam(defaultValue = "10") @ApiParam(value = "页数限制") Integer pageSize) {
        return CommonResult.success(adminMemberService.listAllMembersSelective(nickname, phone, email, parentId, phoneVerify, emailVerify, isEffective, miningIsEff, orderColumn, orderDirection, beginDate, endDate, status, pageNum, pageSize));
    }

    @ApiOperation("所有合伙人等级")
    @GetMapping("/allPartnerLevel")
    public CommonResult<List<UcPartnerLevel>> getAllPartnerLevel() {
        List<UcPartnerLevel> allLevel = partnerLevelSerivce.findAll();
        return CommonResult.success(allLevel);
    }

    /**
     * 修改合伙人
     *
     * @param ucPartnerParam
     * @return
     */
    @ApiOperation("修改合伙人")
    @PostMapping("/updatePartner")
    public CommonResult<UcMember> updatePartner(@RequestBody @Validated UcPartnerParam ucPartnerParam) {
        // 未修改之前的
        UcMember beforeUpdateMember = memberService.getById(ucPartnerParam.getId());
        beforeUpdateMember.setPartnerLevelId(PartnerLevelEnum.SILVER.getValue());
        // 如果将下级是合伙人，则不能修改成非合伙人
        if (beforeUpdateMember.getIsPartner()){
            if ( ! ucPartnerParam.getIsPartner()) {
                throw new ApiException("不允许将合伙人设置成非合伙人");
            } else {
                adminMemberService.updatePartner(beforeUpdateMember, ucPartnerParam);
            }
        } else {
            adminMemberService.updatePartner(beforeUpdateMember, ucPartnerParam);
        }
        return CommonResult.success();
    }

    /**
     * 合伙人数据回显
     *
     * @param id
     * @return
     */
    @ApiOperation("合伙人数据回显")
    @GetMapping("/showPartnerData")
    public CommonResult<ShowPartnerParam> showPartnerData(@RequestParam @ApiParam Long id) {
        UcMember ucMember = adminMemberService.getById(id);
        ShowPartnerParam showPartnerParam = new ShowPartnerParam();
        showPartnerParam.setIsPartner(ucMember.getIsPartner());
        showPartnerParam.setPartnerCommissionRate(ucMember.getPartnerCommissionRate());
        return CommonResult.success(showPartnerParam);
    }

    @ApiOperation("批量禁用启用用户")
    @PostMapping("/modify/status")
    public CommonResult enableStatus(@RequestBody BatchModifyUserStatusParam param) {
        List<UcMember> list = new ArrayList<>();
        for (Long id : param.getIdList()) {
            UcMember member = new UcMember();
            member.setId(id);
            member.setStatus(param.getStatus());
            member.setDisableInstructions(param.getDisableInstructions());
            list.add(member);
        }
        adminMemberService.updateBatchById(list);
        return CommonResult.success();
    }

}
