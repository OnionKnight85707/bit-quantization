package com.mzwise.modules.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.util.IpUtils;
import com.mzwise.common.util.SecurityUtils;
import com.mzwise.modules.admin.dto.AdminLogListParam;
import com.mzwise.modules.admin.entity.UmsAdminLog;
import com.mzwise.modules.admin.mapper.UmsAdminLogMapper;
import com.mzwise.modules.admin.service.UmsAdminLogService;
import com.mzwise.modules.admin.vo.AdminLogListVo;
import com.mzwise.modules.ucenter.vo.ProfitDetailsVo;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 管理员日志实现
 * @author: David Liang
 * @create: 2022-07-21 17:15
 */
@Service
public class UmsAdminLogServiceImpl extends ServiceImpl<UmsAdminLogMapper, UmsAdminLog> implements UmsAdminLogService {

    /**
     * 新增管理员日志
     * @param adminLog
     */
    @Override
    public void addAdminLog(UmsAdminLog adminLog) {
        try {
            Long adminId = SecurityUtils.getCurrentAdminId();
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
//            String ip = request.getRemoteAddr();
            String ip = IpUtils.getIP(request);
            adminLog.setAdminId(adminId);
            adminLog.setIp(ip);
            this.save(adminLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 管理员日志列表
     *
     * @param param
     * @return
     */
    @Override
    public Page<AdminLogListVo> adminLogList(AdminLogListParam param) {
        Page<UmsAdminLog> page = new Page<>(param.getPageNum(), param.getPageSize());
        return baseMapper.adminLogList(page, param);
    }

}
