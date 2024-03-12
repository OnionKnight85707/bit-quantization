package com.mzwise.modules.common.service;

import com.mzwise.common.api.CommonResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传服务
 * @author wmf
 */
public interface UploadService {
    CommonResult uploadOssImage(MultipartFile file);
}
