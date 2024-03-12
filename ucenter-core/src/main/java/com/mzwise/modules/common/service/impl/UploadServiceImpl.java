package com.mzwise.modules.common.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.config.AliyunConfig;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.GeneratorUtil;
import com.mzwise.modules.common.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UploadServiceImpl implements UploadService {
    private String savePath = "data/upload/{:cate}/{yyyy}{mm}{dd}/{time}{rand:6}";
    private String allowedFormat = ".jpg,.gif,.png";
    private long maxAllowedSize = 1024 * 10000;

    @Autowired
    private AliyunConfig aliyunConfig;

    @Override
    public CommonResult uploadOssImage(MultipartFile file) {
        if (file == null) {
            throw new ApiException("file_not_found");
        }

        String directory = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());

        OSSClient ossClient = new OSSClient(aliyunConfig.getOssEndpoint(), aliyunConfig.getAccessKeyId(), aliyunConfig.getAccessKeySecret());
        try {
            String fileName = file.getOriginalFilename();
            String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String key = directory + GeneratorUtil.getUUID() + suffix;
            System.out.println(key);
            ossClient.putObject(aliyunConfig.getOssBucketName(), key, file.getInputStream());
            String uri = aliyunConfig.toUrl(key);
            CommonResult<String> success = CommonResult.success(uri, "success");
            return success;
        } catch (OSSException oe) {
            return CommonResult.failed(oe.getErrorMessage());
        } catch (ClientException ce) {
            System.out.println("Error Message: " + ce.getMessage());
            return CommonResult.failed(ce.getErrorMessage());
        } catch (Throwable e) {
            e.printStackTrace();
            return CommonResult.failed("failed");
        } finally {
            ossClient.shutdown();
        }
    }




}
