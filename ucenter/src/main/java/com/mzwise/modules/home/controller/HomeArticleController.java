package com.mzwise.modules.home.controller;


import com.mzwise.annotation.AnonymousAccess;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.constant.ArticleTypeEnum;
import com.mzwise.modules.home.entity.HomeArticle;
import com.mzwise.modules.home.service.HomeArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author admin
 * @since 2021-05-20
 */
@Api(tags = "文章管理")
@RestController
@RequestMapping("/home/article")
public class HomeArticleController {

    @Autowired
    private HomeArticleService articleService;

    @ApiOperation("文章列表")
    @GetMapping
    @AnonymousAccess
    public CommonResult<List<HomeArticle>> all(ArticleTypeEnum type, String keyword, Boolean top) {
        String language = LocaleSourceUtil.getLanguage();
        return CommonResult.success(articleService.list(language, type, keyword, top));
    }

    @ApiOperation("根据标题获取文章")
    @GetMapping("/getByTitle")
    @AnonymousAccess
    public CommonResult<HomeArticle> getByTypeAndTitle(@RequestParam() ArticleTypeEnum type, String keyword) {
        String language = LocaleSourceUtil.getLanguage();
        return CommonResult.success(articleService.getByTypeAndTitle(language, type, keyword));
    }

    @ApiOperation("文章详情")
    @GetMapping("/{id}")
    @AnonymousAccess
    public CommonResult<HomeArticle> detail(@PathVariable("id") String id) {
        return CommonResult.success(articleService.getById(id));
    }
}

