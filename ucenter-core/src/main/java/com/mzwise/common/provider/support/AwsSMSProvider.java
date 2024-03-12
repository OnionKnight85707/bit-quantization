package com.mzwise.common.provider.support;

import com.alibaba.fastjson.JSON;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 亚马逊短信
 * @author: David Liang
 * @create: 2022-07-20 16:04
 */
@Component
@Slf4j
public class AwsSMSProvider {

    private Map<String, MessageAttributeValue> smsAttributes;

    private static String AWS_ACCESS_KEYID;
    private static String AWS_SECRET_KEY;


    @Value("${aws.AWS_ACCESS_KEYID}")
    public void setKeyId(String keyid) {
        AWS_ACCESS_KEYID = keyid;
    }

    @Value("${aws.AWS_SECRET_KEY}")
    public void setKey(String key) {
        AWS_SECRET_KEY = key;
    }

    public boolean send2SNS(String phoneNumber, String message) {
        if (phoneNumber.contains("_")) {
            phoneNumber = StringUtils.replace(phoneNumber, "_", "");
        }
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+" + phoneNumber;
        }
        PublishResult result = sendSMSMessage(phoneNumber, message, getDefaultSMSAttributes());
        log.info("发送亚马逊短信结果：{}", JSON.toJSON(result));
        if (result != null && !StringUtils.isEmpty(result.getMessageId())) return true;
        return false;
    }

    public Map<String, MessageAttributeValue> getDefaultSMSAttributes() {
        if (smsAttributes == null) {
            smsAttributes = new HashMap<>();
            smsAttributes.put("AWS.SNS.SMS.SenderID", new MessageAttributeValue()
                    .withStringValue("1")
                    .withDataType("String"));
            smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                    .withStringValue("0.05")
                    .withDataType("Number"));
            smsAttributes.put("AWS.SNS.SMS.SMSType", new MessageAttributeValue()
                    .withStringValue("Transactional")
                    .withDataType("String"));
        }
        return smsAttributes;
    }

    public PublishResult sendSMSMessage(String phoneNumber, String message, Map<String, MessageAttributeValue> smsAttributes) {
        AWSCredentials awsCredentials = new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return AWS_ACCESS_KEYID;// 带有发短信权限的 IAM 的 ACCESS_KEY
            }

            @Override
            public String getAWSSecretKey() {
                return AWS_SECRET_KEY; // 带有发短信权限的 IAM 的 SECRET_KEY
            }

        };
        AWSCredentialsProvider provider = new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                return awsCredentials;
            }

            @Override
            public void refresh() {
            }
        };
        AmazonSNS amazonSNS = null;
        try {
            //设置aws区域
            amazonSNS = AmazonSNSClientBuilder.standard().withCredentials(provider).withRegion("us-east-1").build();
        } catch (Exception e) {

        }
        return amazonSNS.publish(
                new PublishRequest()
                        .withMessage(message)
                        .withPhoneNumber(phoneNumber)
                        .withMessageAttributes(smsAttributes)
        );
    }

//    public static void main(String[] args) {
//        //AmazonSNSClient client = new AmazonSNSClient(credentialsProvider);
//        //AWSCredentialsProviderChain chain = new AWSCredentialsProviderChain(credentialsProvider);
//        //chain.setReuseLastProvider(true);
//        //credentialsProvider.getCredentials();
//        AwsSMSProvider shortMessage = new AwsSMSProvider();
//        boolean publishResult = shortMessage.send2SNS("+8613713842175", "比特量化亚马逊短信test");
//        System.out.println(publishResult);
//
//    }

}
