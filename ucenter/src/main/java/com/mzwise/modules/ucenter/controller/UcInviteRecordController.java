package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.ucenter.service.UcInviteRecordService;
import com.mzwise.modules.ucenter.vo.MySubMembersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-05-19
 */
@Api(tags = "邀请记录")
@RestController
@RequestMapping("/ucenter/ucInviteRecord")
public class UcInviteRecordController {
    @Autowired
    private UcInviteRecordService inviteRecordService;

    @ApiOperation("分页查询我的下级")
    @GetMapping("/query-mySub")
    public CommonResult<Page<MySubMembersVO>> queryMySubMembers(@RequestParam(defaultValue = "1") Integer page,
                                                                @RequestParam(defaultValue = "10") Integer size) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return CommonResult.success(inviteRecordService.queryMySubMembers(currentUserId, page, size));
    }

}

