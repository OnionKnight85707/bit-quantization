package com.mzwise.modules.home.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.constant.CarousePositionTypeEnum;
import com.mzwise.modules.home.dto.HomeCarouselPicParam;
import com.mzwise.modules.home.entity.HomeCarouselPic;
import com.mzwise.modules.home.mapper.HomeCarouselPicMapper;
import com.mzwise.modules.home.service.HomeCarouselPicService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Service
public class HomeCarouselPicServiceImpl extends ServiceImpl<HomeCarouselPicMapper, HomeCarouselPic> implements HomeCarouselPicService {

    @Override
    public void create(HomeCarouselPicParam carouselPicParam) {
        HomeCarouselPic carouselPic = new HomeCarouselPic();
        BeanUtils.copyProperties(carouselPicParam, carouselPic);
        save(carouselPic);
    }

    @Override
    public List<HomeCarouselPic> showHomeCarousePic(CarousePositionTypeEnum position) {
        QueryWrapper<HomeCarouselPic> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(HomeCarouselPic::getIsShow, true);
        String language = LocaleSourceUtil.getLanguage();
        wrapper.lambda().eq(HomeCarouselPic::getLanguage, language);
        if (position != null) {
            wrapper.lambda().eq(HomeCarouselPic::getPosition, position);
        }
        return list(wrapper);
    }

    @Override
    public void updateIsShow(Long id) {
        HomeCarouselPic carouselPic = getById(id);
        carouselPic.setIsShow(!carouselPic.getIsShow());
        updateById(carouselPic);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        QueryWrapper<HomeCarouselPic> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(HomeCarouselPic::getId, Arrays.asList(ids));
        remove(wrapper);
    }

    @Override
    public Page<HomeCarouselPic> listPage(String language, String name, Boolean isShow, CarousePositionTypeEnum position, Integer pageNum, Integer pageSize) {
        Page<HomeCarouselPic> page = new Page<>(pageNum, pageSize);
        QueryWrapper<HomeCarouselPic> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(language)) {
            wrapper.lambda().eq(HomeCarouselPic::getLanguage, language);
        }
        if (StringUtils.isNotBlank(name)) {
            wrapper.lambda().like(HomeCarouselPic::getName, name);
        }
        if (position != null) {
            wrapper.lambda().eq(HomeCarouselPic::getPosition, position);
        }
        if (isShow != null) {
            wrapper.lambda().eq(HomeCarouselPic::getIsShow, isShow);
        }
        wrapper.lambda().orderByAsc(HomeCarouselPic::getLanguage);
        return page(page, wrapper);
    }
}
