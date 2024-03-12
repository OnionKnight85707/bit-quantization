package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcAreaCode;
import com.mzwise.modules.ucenter.vo.AreaCodeVO;

import java.util.List;

public interface UcAreaCodeService extends IService<UcAreaCode> {


    /**
     *  返回区号列表
     * @return
     */
    List<AreaCodeVO> areaCodelist(String language);

    UcAreaCode findByAreaCode(String areaCode,String language);


}
