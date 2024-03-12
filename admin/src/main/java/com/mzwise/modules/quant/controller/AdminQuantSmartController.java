package com.mzwise.modules.quant.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.AdminLogActionEnum;
import com.mzwise.constant.AdminLogModuleEnum;
import com.mzwise.constant.PlatformEnum;
import com.mzwise.constant.SmartStatusEnum;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.quant.dto.QuantSmartDto;
import com.mzwise.modules.quant.dto.QuantSmartListParam;
import com.mzwise.modules.quant.entity.QuantCoin;
import com.mzwise.modules.quant.entity.QuantSmart;
import com.mzwise.modules.quant.service.AdminQuantApiService;
import com.mzwise.modules.quant.service.QuantCoinService;
import com.mzwise.modules.quant.service.QuantSmartService;
import com.mzwise.modules.quant.vo.AdminQuantApiAccessVO;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

/**
 * 智能选币控制器
 * @Author LiangZaiChao
 * @Date 2022/7/12 11:32
 */
@Api(tags = "智能选币控制器")
@RestController
@RequestMapping("/admin/quant-smart")
public class AdminQuantSmartController {

    @Autowired
    private QuantSmartService quantSmartService;
    @Autowired
    private QuantCoinService quantCoinService;
    @Autowired
    private UmsAdminLogService adminLogService;

    @ApiOperation("智能选币列表")
    @PostMapping("/quantSmartList")
    public CommonResult<Page<QuantSmartDto>> quantSmartList(@RequestBody QuantSmartListParam param) {
        Page<QuantSmartDto> resultPage = quantSmartService.quantSmartList(param);
        return CommonResult.success(resultPage);
    }

    @ApiOperation("新增智能选币")
    @PostMapping("/addQuantSmart")
    public CommonResult addQuantSmart(@RequestBody QuantSmartDto quantSmart) {
        quantSmartService.addQuantSmart(quantSmart);
        // 记录日志
        QuantSmartDto smartDto = new QuantSmartDto();
        QuantSmart smart = quantSmartService.getById(quantSmart.getId());
        BeanUtils.copyProperties(smart, smartDto);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.ADD.getValue(), AdminLogModuleEnum.SMART_COIN_SELECTION_ADD.getValue(),
                JSON.toJSONString(smartDto), JSON.toJSONString(quantSmart), "智能选币-增加"));
        return CommonResult.success();
    }

    @ApiOperation("智能选币详情")
    @GetMapping("/quantSmartDetail")
    public CommonResult<QuantSmartDto> quantSmartDetail(@RequestParam Integer id) {
        QuantSmartDto quantSmartDto = quantSmartService.quantSmartDetail(id);
        return CommonResult.success(quantSmartDto);
    }

    @ApiOperation("修改智能选币")
    @PostMapping("/updateQuantSmart")
    public CommonResult updateQuantSmart(@RequestBody QuantSmartDto quantSmart) {
        quantSmartService.updateQuantSmart(quantSmart);
        // 记录日志
        QuantSmartDto smartDto = new QuantSmartDto();
        QuantSmart smart = quantSmartService.getById(quantSmart.getId());
        BeanUtils.copyProperties(smart, smartDto);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.SMART_COIN_SELECTION_UPDATE.getValue(),
                JSON.toJSONString(smartDto), JSON.toJSONString(quantSmart), "智能选币-修改"));
        return CommonResult.success();
    }

    @ApiOperation("删除智能选币")
    @GetMapping("/deleteQuantSmart")
    public CommonResult deleteQuantSmart(@RequestParam Integer id) {
        quantSmartService.deleteQuantSmart(id);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.DELETE.getValue(), AdminLogModuleEnum.SMART_COIN_SELECTION_DELETE.getValue(),
                null, JSON.toJSONString(id), "智能选币-删除"));
        return CommonResult.success();
    }

    @ApiOperation("所有量化币种")
    @GetMapping("/allQuantCoin")
    public CommonResult allQuantCoin() {
        List<QuantCoin> coins = quantCoinService.list();
        return CommonResult.success(coins);
    }

    @ApiOperation("修改智能选币状态")
    @PutMapping("/updateStatus")
    public CommonResult updateStatus(@RequestParam Integer id){
        QuantSmart quantSmart = quantSmartService.getById(id);
        Assert.notNull(quantSmart,"该币种不存在");
        String desc = null;
        if (quantSmart.getStatus() == SmartStatusEnum.DISABLE){
            quantSmartService.updateCoinStatus(SmartStatusEnum.ENABLE,id);
            desc = "智能选币-启用";
        }
        if (quantSmart.getStatus() == SmartStatusEnum.ENABLE){
            quantSmartService.updateCoinStatus(SmartStatusEnum.DISABLE,id);
            desc = "智能选币-禁用";
        }
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.SMART_COIN_SELECTION_ENABLE_DISABLE.getValue(),
                null, JSON.toJSONString(id), desc));
        return CommonResult.success();
    }
}
