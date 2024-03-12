package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.dto.UniTemplateParam;
import com.mzwise.modules.ucenter.entity.UniTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UniTemplateService extends IService<UniTemplate> {


    List<UniTemplate> queryUniTemplate();

    /**
     * 新增模板
     * @param param
     */
    void addTemplate(UniTemplateParam param);

    /**
     * 修改模板
     * @param param
     */
    void updateTemplate(UniTemplateParam param);

    /**
     * 模板列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UniTemplate> templateList(Integer pageNum, Integer pageSize);


    List<UniTemplate> UniTemplateList();

    UniTemplate getOneById(Integer id);

    /**
     *  根据模版id删除
     * @param templateId
     */
    void deleteTemplate(Long templateId);

    /**
     *  根据id删除一条记录
     * @param id
     */
    void deleteByTemplateId(@Param("id") Long id);
}
