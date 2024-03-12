package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcInviteRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.vo.MySubMembersVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author admin
 * @since 2021-05-19
 */
public interface UcInviteRecordService extends IService<UcInviteRecord> {

    /**
     * 创建邀请记录
     *
     * @param subMemberId
     * @param supMemberId
     */
    void create(Long subMemberId, Long supMemberId);

    /**
     * 通过下级id查找邀请记录
     *
     * @param subMemberId
     * @return
     */
    UcInviteRecord queryBySubMemberId(Long subMemberId);


    /**
     * 通过上级id查找邀请记录
     *
     * @param supMemberId 上级id
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<MySubMembersVO> queryMySubMembers(Long supMemberId, Integer pageNum, Integer pageSize);

}
