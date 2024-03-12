package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.Dictionary;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author 666
 * @since 2022-08-10
 */
public interface DictionaryService extends IService<Dictionary> {

    /**
     * 查询最大短信发送条数
     * @return
     */
    Integer queryMaxSendMessage();

    /**
     * 列表查询字典表数据
     * @param explanation
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Dictionary> listAllDictionary(String explanation, Integer pageNum, Integer pageSize);



    /**
     *  合伙人最大返佣比例
     * @return
     */
    BigDecimal getPartnerMaxCommissionRatio();
}
