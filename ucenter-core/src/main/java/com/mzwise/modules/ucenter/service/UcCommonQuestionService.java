package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.constant.CommonQuestionTypeEnum;
import com.mzwise.modules.ucenter.dto.UcCommonQuestionParam;
import com.mzwise.modules.ucenter.entity.UcCommonQuestion;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface UcCommonQuestionService extends IService<UcCommonQuestion> {
    /**
     * 创建常见问题
     * @param commonQuestionParam
     * @return
     */
    void create(UcCommonQuestionParam commonQuestionParam);

    /**
     * 展示所有常见问题
     * @return
     */
    List<UcCommonQuestion> listAll();

    /**
     * 后台 条件查询分页显示常见问题列表
     * @param language
     * @param title
     * @param type
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcCommonQuestion> listPage(String language, String title, Integer type, Integer pageNum, Integer pageSize);

    /**
     * 多选删除常见问题
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 修改显示与否
     * @param id
     */
    void updateIsShow(Long id);

    /**
     * 修改置顶与否
     * @param id
     */
    void updateIsTop(Long id);

    /**
     * 常见问题返回每个类别的前#{limit}个
     * @param limit
     * @return
     */
    List<UcCommonQuestion> showPageView(Integer limit);

    /**
     * 根据常见问题type 查询
     * @param type
     * @return
     */
    List<UcCommonQuestion> listByType(CommonQuestionTypeEnum type);

    List<UcCommonQuestion> listByType(Integer type);

}
