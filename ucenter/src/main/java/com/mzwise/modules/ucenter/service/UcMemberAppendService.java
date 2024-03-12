package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.entity.UcMemberAppend;

public interface UcMemberAppendService extends IService<UcMemberAppend> {

    /**
     *  根据memberId删除记录
     * @param memberId
     * @return
     */
    Boolean deleteByMemberId(Long memberId);

    /**
     *  根据memberId查询一条记录
     * @param memberId
     * @return
     */
    UcMemberAppend getByMemberId(Long memberId);
}
