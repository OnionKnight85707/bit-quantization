package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcMemberLog;

/**
 * 用户日志接口
 * @author: David Liang
 * @create: 2022-07-26 18:17
 */
public interface UcMemberLogService extends IService<UcMemberLog> {

    /**
     * 新增用户日志
     * @param memberLog
     */
    void addMemberLog(UcMemberLog memberLog);

}
