package com.mzwise.modules.home.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.api.CommonResult;
import com.mzwise.constant.AdminLogActionEnum;
import com.mzwise.constant.AdminLogModuleEnum;
import com.mzwise.constant.ArticleTypeEnum;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.home.dto.AdminArticleParam;
import com.mzwise.modules.home.entity.HomeArticle;
import com.mzwise.modules.home.service.HomeArticleService;
import com.mzwise.modules.ucenter.dto.UcPartnerParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author piao
 * @Date 2021/06/02
 */
@Api("后台财经新闻")
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {
    @Autowired
    private HomeArticleService articleService;
    @Autowired
    private UmsAdminLogService adminLogService;

    @ApiOperation("创建文章")
    @PostMapping("/create")
    public CommonResult create(@RequestBody AdminArticleParam param) {
        articleService.create(param);
        // 记录日志
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.ADD.getValue(), AdminLogModuleEnum.FINANCE_MANAGE.getValue(),
                null, JSON.toJSONString(param), "财经管理-增加"));
        return CommonResult.success();
    }

    @ApiOperation("修改文章列表")
    @PutMapping("/update")
    public CommonResult update(@RequestBody HomeArticle article) {
        articleService.updateById(article);
        // 记录日志
        HomeArticle homeArticle = new HomeArticle();
        HomeArticle byId = articleService.getById(article.getId());
        BeanUtils.copyProperties(byId, homeArticle);
        adminLogService.addAdminLog(new UmsAdminLog(AdminLogActionEnum.UPDATE.getValue(), AdminLogModuleEnum.FINANCE_MANAGE.getValue(),
                JSON.toJSONString(homeArticle), JSON.toJSONString(article), "财经管理-修改"));
        return CommonResult.success();
    }

    @ApiOperation("展示文章列表")
    @GetMapping("/list")
    public CommonResult<Page<HomeArticle>> list(@ApiParam("语言") String language,
                                                @RequestParam @ApiParam(value = "种类", required = true) ArticleTypeEnum type,
                                                @ApiParam("标题") String title,
                                                @ApiParam("是否置顶") Boolean top,
                                                @RequestParam(defaultValue = "1") @ApiParam("当前页") Integer pageNum,
                                                @RequestParam(defaultValue = "10") @ApiParam("每页限制展示数目") Integer pageSize) {
        return CommonResult.success(articleService.listArticleSelective(language, type, title, top, pageNum, pageSize));

    }

}
