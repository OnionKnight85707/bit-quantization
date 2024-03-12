package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.constant.AnnouncementTypeEnum;
import com.mzwise.modules.ucenter.dto.UcAnnouncementParam;
import com.mzwise.modules.ucenter.entity.UcAnnouncement;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
public interface UcAnnouncementService extends IService<UcAnnouncement> {
    /**
     * 新增公告
     * @param announcementParam
     */
    void create(UcAnnouncementParam announcementParam);

    /**
     * 多选删除
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 前台根据语言分页查询所有公告
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcAnnouncement> listByLanguage(Integer pageNum, Integer pageSize);

    /**
     * 后台条件分页查询所有公告
     * @param language
     * @param title
     * @param label
     * @param isShow
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<UcAnnouncement> listPage(String language, String title, AnnouncementTypeEnum label, Boolean isShow, Integer pageNum, Integer pageSize);

    /**
     * 切换展示与否
     * @param announcementId
     */
    void updateIsShow(Long announcementId);

    /**
     * 切换置顶与否
     * @param announcementId
     */
    void updateIsTop(Long announcementId);
}
