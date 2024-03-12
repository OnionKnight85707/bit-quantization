package com.mzwise.modules.common.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.DateUtil;
import com.mzwise.common.util.FileUtil;
import com.mzwise.modules.common.service.AmazonS3UploadService;
import com.mzwise.modules.common.service.LocalStorageService;
import com.mzwise.modules.common.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.applet.AppletIOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author Administrator
 * @Date 2021/02/02
 */
@Api(tags = "通用上传图片")
@RestController
@RequestMapping("/admin/upload")
public class AdminUploadController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private LocalStorageService localStorageService;
    @Autowired
    private AmazonS3UploadService amazonS3UploadService;

    @ApiOperation("通用上传OSS图片")
    @RequestMapping(value = "/oss/image", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult uploadOssImage(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("file") MultipartFile file) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ApiException("format_not_supported");
        }
        return uploadService.uploadOssImage(file);
    }

    @ApiOperation("通用本地存储上传文件")
    @RequestMapping(value = "/storage/image", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        String s = localStorageService.upload(request, response, file);
        return CommonResult.success(s);
    }

    @ApiOperation("上传亚马逊图片")
    @RequestMapping(value = "/amazonUploadImage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> amazonUploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // 文件大小(字节数)
        long fileBytes = file.getSize();
        if (fileBytes > 2 * 1024 * 1024) {
            throw new ApiException("图片大小最大为2MB");
        }
        // 重命名文件名(当前时间 + 6位随机数)
        String fileName = DateUtil.getNowStr() + "-" + RandomStringUtils.randomAlphanumeric(6) + "." + FileUtil.getExtensionName(file.getOriginalFilename());
        String url = amazonS3UploadService.uploadImage(file.getInputStream(), fileName, file.getContentType(), file.getSize());
        return CommonResult.success(url);
    }

}
