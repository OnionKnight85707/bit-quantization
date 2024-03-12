/*
 *  Copyright 2019-2020 admin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mzwise.modules.common.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mzwise.common.config.FileProperties;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.FileUtil;
import com.mzwise.modules.common.service.LocalStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.mzwise.constant.SysConstant.MB;


/**
 * @author admin
 * @date 2019-09-05
 */
@Service
@RequiredArgsConstructor
public class LocalStorageServiceImpl implements LocalStorageService {
    @Autowired
    private final FileProperties properties;

    /**
     * 通用上传文件
     *
     * @param multipartFile
     * @return
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String upload(HttpServletRequest request, HttpServletResponse response, MultipartFile multipartFile) {
        String suffix = getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        if ("pic".equals(type)) {
            float size = Float.parseFloat(String.valueOf((multipartFile.getSize())));
            if (size > 2 * MB) {
                throw new ApiException("图片大小不能超过2MB");
            }
        }
        File file = FileUtil.upload(multipartFile, properties.getPath().getPath() + type + File.separator);
        if (ObjectUtil.isNull(file)) {
            throw new ApiException("上传失败");
        }
        String s1 = file.getName();
//        String s = s1.replaceAll(" ","");
        String prefix = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/file/";
        String path = prefix + type + "/" + s1;
        String name = multipartFile.getOriginalFilename();
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("path", path);
        return path;
    }

    /**
     * 获取文件扩展名，不带 .
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

}
