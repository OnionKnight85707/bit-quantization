package com.mzwise.modules.ucenter.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.entity.UcAreaCode;
import org.apache.ibatis.annotations.Param;
import com.mzwise.modules.ucenter.vo.AreaCodeVO;

import java.util.List;

public interface UcAreaCodeMapper extends BaseMapper<UcAreaCode> {

    UcAreaCode selectByAreaCode(@Param("areaCode") String areaCode,@Param("language") String language);

    List<AreaCodeVO> areaCodelist(@Param("language") String language);

}
