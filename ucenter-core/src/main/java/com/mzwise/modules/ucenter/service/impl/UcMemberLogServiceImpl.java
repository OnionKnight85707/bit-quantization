package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.entity.UcMemberLog;
import com.mzwise.modules.ucenter.mapper.UcMemberLogMapper;
import com.mzwise.modules.ucenter.service.UcMemberLogService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户日志服务
 * @author: David Liang
 * @create: 2022-07-26 18:18
 */
@Service
public class UcMemberLogServiceImpl extends ServiceImpl<UcMemberLogMapper, UcMemberLog> implements UcMemberLogService {

    /**
     * 新增用户日志
     *
     * @param memberLog
     */
    @Override
    public void addMemberLog(UcMemberLog memberLog) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String ip = request.getRemoteAddr();
            memberLog.setIp(ip);
            this.save(memberLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
