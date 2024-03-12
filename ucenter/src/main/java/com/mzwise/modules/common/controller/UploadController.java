package com.mzwise.modules.common.controller;

import com.mzwise.common.api.CommonResult;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.config.AliyunConfig;
import com.mzwise.modules.common.service.AmazonS3UploadService;
import com.mzwise.modules.common.service.LocalStorageService;
import com.mzwise.modules.common.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@Api(tags = "通用上传接口")
@RequestMapping("/common/upload")
public class UploadController {

    private static Log log = LogFactory.getLog(UploadController.class);
    private Logger logger = LoggerFactory.getLogger(UploadController.class);
    private String savePath = "data/upload/{:cate}/{yyyy}{mm}{dd}/{time}{rand:6}";
    private String allowedFormat = ".jpg,.gif,.png";
    private long maxAllowedSize = 1024 * 10000;

    @Autowired
    private AliyunConfig aliyunConfig;
    @Autowired
    private UploadService uploadService;
    @Autowired
    private LocalStorageService localStorageService;

    @ApiOperation("通用上传图片")
    @RequestMapping(value = "/oss/image", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult uploadOssImage(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("file") MultipartFile file) throws IOException {
        log.info(request.getSession().getServletContext().getResource("/"));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new ApiException("format_not_supported");
        }
        return uploadService.uploadOssImage(file);
    }

//    @RequestMapping(value = "/local/image", method = RequestMethod.POST)
//    @ResponseBody
//    @AccessLog(module = AdminModule.COMMON, operation = "上传本地图片")
//    public String uploadLocalImage(HttpServletRequest request, HttpServletResponse response,
//                                   @RequestParam("file") MultipartFile file) throws IOException {
//        log.info(request.getSession().getServletContext().getResource("/").toString());
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html; charset=UTF-8");
//        Assert.isTrue(ServletFileUpload.isMultipartContent(request), sourceService.getMessage("FORM_FORMAT_ERROR"));
//        Assert.isTrue(file != null, sourceService.getMessage("NOT_FIND_FILE"));
//        //验证文件类型
//        String fileName = file.getOriginalFilename();
//        String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
//        if (!allowedFormat.contains(suffix.trim().toLowerCase())) {
//            return MessageResult.error(sourceService.getMessage("FORMAT_NOT_SUPPORT")).toString();
//        }
//        String result = UploadFileUtil.uploadFile(file, fileName);
//        if (result != null) {
//            MessageResult mr = new MessageResult(0, sourceService.getMessage("UPLOAD_SUCCESS"));
//            mr.setData(result);
//            return mr.toString();
//        } else {
//            MessageResult mr = new MessageResult(0, sourceService.getMessage("FAILED_TO_WRITE"));
//            mr.setData(result);
//            return mr.toString();
//        }
//    }

    @ApiOperation("通用本地存储上传文件")
    @RequestMapping(value = "/storage/image", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<String> upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) {
        String s = localStorageService.upload(request, response, file);
        return CommonResult.success(s);
    }

}
