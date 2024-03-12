package com.mzwise.modules.common.service.impl;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mzwise.common.exception.ApiException;
import com.mzwise.modules.common.service.AmazonS3UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 亚马逊s3服务实现
 * @Author LiangZaiChao
 * @Date 2022/8/5 16:55
 */
@Service
public class AmazonS3UploadServiceImpl implements AmazonS3UploadService {

    private AmazonS3 s3;
    @Value("${amazon.s3.access-key-id}")
    private String awsAccessKey;
    @Value("${amazon.s3.access-key-secret}")
    private String awsSecretKey;
    @Value("${amazon.s3.region}")
    private String region;
    @Value("${amazon.s3.bucket}")
    private String bucketName;

    private String path = "/pic";
    private String pathDir = "pic/";

    @Autowired
    public void setS3Client() {
        s3 = new AmazonS3Client(new BasicAWSCredentials(awsAccessKey, awsSecretKey));
        s3.setRegion(Region.getRegion(Regions.fromName(region)));
    }

    @Override
    public String uploadImage(InputStream inputstream, String remoteFileName, String contentType, long contentLength) {
        String theContentType = StringUtils.isNotEmpty(contentType) ? contentType : "image/jpeg";
        try {
            String bucketPath = bucketName + path;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(theContentType);
            metadata.setContentLength(contentLength);
            s3.putObject(new PutObjectRequest(bucketPath, remoteFileName, inputstream, metadata));
            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, remoteFileName)
                    .withExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 3600 * 1000 - 100000)).withMethod(HttpMethod.POST);
            URL url = s3.generatePresignedUrl(urlRequest);
            /**
             * 返回url: https://hktd-dev.s3.ap-northeast-1.amazonaws.com/20220805191435-h4wdIG.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220805T111506Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604699&X-Amz-Credential=AKIARJJWHLKCDXRPLCS2%2F20220805%2Fap-northeast-1%2Fs3%2Faws4_request&X-Amz-Signature=d02abfef90a329c401859b9b3840f280dd97f7f11f107a061b2c5b13dc604d89
             * 需要去掉?后面的参数，并且在文件名和.com之间加上“/pic”
             */
            return getUrlPath(remoteFileName, url.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("上传图片错误");
        }
    }

    /**
     * 重新获取文件路径
     * @param remoteFileName 文件名(eg: 20220805191435-h4wdIG.jpg)
     * @param originUrl 亚马逊直接返回路径(eg: https://hktd-dev.s3.ap-northeast-1.amazonaws.com/20220805191435-h4wdIG.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220805T111506Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604699&X-Amz-Credential=AKIARJJWHLKCDXRPLCS2%2F20220805%2Fap-northeast-1%2Fs3%2Faws4_request&X-Amz-Signature=d02abfef90a329c401859b9b3840f280dd97f7f11f107a061b2c5b13dc604d89)
     * @return
     */
    public String getUrlPath(String remoteFileName, String originUrl) {
        String[] split = originUrl.split(remoteFileName);
        return split[0] + pathDir + remoteFileName;
    }

    public static void main(String[] args) {
        String path = "pic/";
        String remoteFileName = "20220805191435-h4wdIG.jpg";
        String url = "https://hktd-dev.s3.ap-northeast-1.amazonaws.com/20220805191435-h4wdIG.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220805T111506Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604699&X-Amz-Credential=AKIARJJWHLKCDXRPLCS2%2F20220805%2Fap-northeast-1%2Fs3%2Faws4_request&X-Amz-Signature=d02abfef90a329c401859b9b3840f280dd97f7f11f107a061b2c5b13dc604d89";
        String[] split = url.split(remoteFileName);
        String url2 = split[0] + path + remoteFileName;
        System.out.println(url2);
    }

}
