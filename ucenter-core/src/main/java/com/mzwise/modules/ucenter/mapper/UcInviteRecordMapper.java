package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcInviteRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.vo.MySubMembersVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-05-19
 */
public interface UcInviteRecordMapper extends BaseMapper<UcInviteRecord> {

    /**
     * 分页查看下级
     * @param page
     * @param memberId
     * @return
     */
    Page<MySubMembersVO> mySubMember(Page<MySubMembersVO> page, @Param("supMemberId") Long memberId);

}
