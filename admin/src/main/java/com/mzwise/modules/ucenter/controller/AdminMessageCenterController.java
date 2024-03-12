package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.entity.UcMessageCenter;
import com.mzwise.modules.ucenter.service.UcMessageCenterService;
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
 * @Date 2021/06/02
 */
@Api(tags = "消息管理")
@RestController
@RequestMapping("/admin/message")
public class AdminMessageCenterController {
    @Autowired
    private UcMessageCenterService messageCenterService;

    @ApiOperation("后台查看所有消息")
    @GetMapping("/list")
    public CommonResult<Page<UcMessageCenter>> listAllMessage(@RequestParam(defaultValue = "1")@ApiParam(value = "当前页")Integer pageNum,
                                                              @RequestParam(defaultValue = "10")@ApiParam(value = "每页数量显示限制")Integer pageSize) {
        return CommonResult.success(messageCenterService.listMessages(pageNum, pageSize));

    }
}
