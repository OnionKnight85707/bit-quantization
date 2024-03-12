package com.mzwise.modules.common.service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author piao
 * @Date 2021/04/15
 */
public interface LocalStorageService {

    /**
     * 通用本地存储
     *
     * @param file
     * @param request
     * @param response
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    String upload(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file);
}
