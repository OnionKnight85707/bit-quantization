package com.mzwise.modules.common.service;

import java.io.InputStream;

/**
 * 亚马逊s3服务
 * @Author LiangZaiChao
 * @Date 2022/8/5 16:53
 */
public interface AmazonS3UploadService {

    String uploadImage(InputStream inputstream, String remoteFileName, String contentType, long contentLength);

}
