package com.mzwise.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.admin.entity.UcPartnerQuestion;


import java.util.List;

public interface UcPartnerQuestionService extends IService<UcPartnerQuestion> {
    List<UcPartnerQuestion> findAll();

    void delete(Integer id);

    void  edit(UcPartnerQuestion uc);
}
