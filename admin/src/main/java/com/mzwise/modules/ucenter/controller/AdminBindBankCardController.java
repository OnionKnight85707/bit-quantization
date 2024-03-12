package com.mzwise.modules.ucenter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.service.UcBindBankCardService;
import com.mzwise.modules.ucenter.vo.BindBankCardVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Administrator
 * @Date 2021/02/22
 */
@Api(tags = "后台绑定银行卡管理")
@RestController
@RequestMapping("/admin/bindBankCard")
public class AdminBindBankCardController {
    @Autowired
    private UcBindBankCardService bindBankCardService;

    @ApiOperation("所有银行卡列表")
    @GetMapping("/list")
    public CommonResult<Page<BindBankCardVO>> listAll(@RequestParam(required = false) String nickname,
                                                      @RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return CommonResult.success(bindBankCardService.listAllCard(nickname, pageNum, pageSize));
    }

    @ApiOperation("删除某个银行卡")
    @DeleteMapping("/delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id) {
        bindBankCardService.removeById(id);
        return CommonResult.success();
    }

}
