package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcBindBankCard;
import com.mzwise.modules.ucenter.vo.BindBankCardVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface UcBindBankCardMapper extends BaseMapper<UcBindBankCard> {
    /**
     * 后台展示所有银行卡列表
     * @param page
     * @param nickname
     * @param wrapper
     * @return
     */
    Page<BindBankCardVO> listAll(Page<BindBankCardVO> page, @Param("nickname")String nickname, @Param(Constants.WRAPPER) Wrapper wrapper);

}
