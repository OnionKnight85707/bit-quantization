package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.constant.AnnouncementTypeEnum;
import com.mzwise.modules.ucenter.dto.UcAnnouncementParam;
import com.mzwise.modules.ucenter.entity.UcAnnouncement;
import com.mzwise.modules.ucenter.mapper.UcAnnouncementMapper;
import com.mzwise.modules.ucenter.service.UcAnnouncementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class UcAnnouncementServiceImpl extends ServiceImpl<UcAnnouncementMapper, UcAnnouncement> implements UcAnnouncementService {

    @Override
    public void create(UcAnnouncementParam announcementParam) {
        UcAnnouncement announcement = new UcAnnouncement();
        BeanUtils.copyProperties(announcementParam, announcement);
        save(announcement);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        QueryWrapper<UcAnnouncement> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(UcAnnouncement::getId, Arrays.asList(ids));
        remove(wrapper);
    }

    @Override
    public Page<UcAnnouncement> listByLanguage(Integer pageNum, Integer pageSize) {
        Page<UcAnnouncement> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcAnnouncement> wrapper = new QueryWrapper<>();
        String language = LocaleSourceUtil.getLanguage();
        wrapper.lambda().eq(UcAnnouncement::getLanguage, language).eq(UcAnnouncement::getIsShow, true)
                .orderByDesc(UcAnnouncement::getIsTop, UcAnnouncement::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<UcAnnouncement> listPage(String language, String title, AnnouncementTypeEnum label, Boolean isShow, Integer pageNum, Integer pageSize) {
        Page<UcAnnouncement> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcAnnouncement> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(language)) {
            wrapper.lambda().eq(UcAnnouncement::getLanguage, language);
        }
        if (StringUtils.isNotBlank(title)) {
            wrapper.lambda().like(UcAnnouncement::getTitle, title);
        }
        if (label != null) {
            wrapper.lambda().eq(UcAnnouncement::getLabel, label);
        }
        if (isShow != null) {
            wrapper.lambda().eq(UcAnnouncement::getIsShow, isShow);
        }
        wrapper.lambda().orderByDesc(UcAnnouncement::getIsTop, UcAnnouncement::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public void updateIsShow(Long announcementId) {
        UcAnnouncement announcement = getById(announcementId);
        announcement.setIsShow(!announcement.getIsShow());
        updateById(announcement);
    }

    @Override
    public void updateIsTop(Long announcementId) {
        UcAnnouncement announcement = getById(announcementId);
        announcement.setIsTop(!announcement.getIsTop());
        updateById(announcement);
    }
}
