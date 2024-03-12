package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 666
 * @since 2022-08-10
 */
public interface DictionaryMapper extends BaseMapper<Dictionary> {

    /**
     * 查询最大短信发送条数
     * @return
     */
    String queryMaxSendMessage();

    /**
     * 列表查询字典表数据
     * @param page
     * @param explanation
     * @return
     */
    Page<Dictionary> listAllDictionary(Page<Dictionary> page, @Param("explanation") String explanation);

    /**
     *  平台佣金比例
     * @return
     */
    BigDecimal getPlatfromCommissionRatio();

    /**
     *  合伙人最大返佣比例
     * @return
     */
    BigDecimal getPartnerMaxCommissionRatio();
}
