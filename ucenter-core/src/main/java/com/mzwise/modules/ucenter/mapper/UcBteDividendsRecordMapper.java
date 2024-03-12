package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcBteDividendsRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.vo.AdminBTEDividendRecordVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-05-26
 */
public interface UcBteDividendsRecordMapper extends BaseMapper<UcBteDividendsRecord> {

    /**
     * 分页条件查询平台币分红记录
     *
     * @param page
     * @param nickname
     * @param phone
     * @param email
     * @param wrapper
     * @return
     */
    Page<AdminBTEDividendRecordVO> listSelective(Page<AdminBTEDividendRecordVO> page,
                                                 @Param("nickname") String nickname,
                                                 @Param("phone") String phone,
                                                 @Param("email") String email,
                                                 @Param(Constants.WRAPPER) Wrapper wrapper
    );
}
