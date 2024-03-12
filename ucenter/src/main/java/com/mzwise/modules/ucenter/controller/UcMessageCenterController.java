package com.mzwise.modules.ucenter.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.modules.ucenter.entity.UcMessageCenter;
import com.mzwise.modules.ucenter.service.UcMessageCenterService;
import com.mzwise.common.util.SecurityUtils;
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
 * @since 2021-03-22
 */
@Api(tags = "消息中心")
@RestController
@RequestMapping("/ucenter/ucMessageCenter")
public class UcMessageCenterController {
    @Autowired
    private UcMessageCenterService messageCenterService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;
    @ApiOperation("分页展示我的所有消息列表")
    @GetMapping("/listMyMessages")
    public CommonResult<Page<UcMessageCenter>> listMyMessages(@RequestParam(defaultValue = "1") Integer page,
                                                              @RequestParam(defaultValue = "10") Integer size) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return CommonResult.success(messageCenterService.listMyMessages(currentUserId, page, size));
    }

    @ApiOperation("获取某个消息详情")
    @GetMapping("/getDetail")
    public CommonResult<UcMessageCenter> getDetail(Long messageId) {
        return CommonResult.success(messageCenterService.getById(messageId));
    }

    @ApiOperation("删除某个消息")
    @PostMapping("/delete")
    public CommonResult delete(Long messageId) {
        messageCenterService.removeById(messageId);
        return CommonResult.success();
    }

    @ApiOperation("已读消息")
    @PutMapping("/read/{id}")
    public CommonResult readMessage(@PathVariable("id") Long messageId) {
        messageCenterService.readMessage(messageId);
        return CommonResult.success();
    }

    @ApiOperation("全部已读")
    @PutMapping("/readAll")
    public CommonResult readAll() {
        Long memberId = SecurityUtils.getCurrentUserId();
        if (memberId == null) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMessageCenterController_001));
        }
        messageCenterService.readAll(memberId);
        return CommonResult.success();
    }

    @ApiOperation("未读消息数量")
    @GetMapping("/unRead")
    @AnonymousAccess
    public CommonResult<Integer> countUnRead() {
        Long memberId = null;
        try {
            memberId = SecurityUtils.getCurrentUserId();
        } catch (Exception e) {
            return CommonResult.success(0);
        }
        if (memberId == null) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcMessageCenterController_001));
        }
        return CommonResult.success(messageCenterService.countUnRead(memberId));
    }
}

