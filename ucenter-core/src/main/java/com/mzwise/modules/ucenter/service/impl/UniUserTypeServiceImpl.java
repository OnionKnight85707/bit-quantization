package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.exception.ApiException;
import com.mzwise.modules.ucenter.entity.UniUserType;
import com.mzwise.modules.ucenter.mapper.UniUserTypeMapper;
import com.mzwise.modules.ucenter.service.UniUserTypeService;
import com.mzwise.modules.ucenter.vo.AdminMemberUserTypeVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UniUserTypeServiceImpl extends ServiceImpl<UniUserTypeMapper,UniUserType> implements UniUserTypeService {

    /**
     * 查询用户类别
     *
     * @return
     */
    @Override
    public List<UniUserType> queryUserType() {
        return this.baseMapper.queryUserType(null);
    }

    /**
     * 查询用户类别
     *
     * @param id 用户类别id
     * @return
     */
    @Override
    public List<UniUserType> queryUserType(Integer id) {
        if (ObjectUtils.isEmpty(id)) {
            return this.list();
        }
        return this.list(Wrappers.<UniUserType>query().lambda().eq(UniUserType::getId, id));
    }

    /**
     * 增加用户类别
     *
     * @param uniUserType
     * @return
     */
    @Override
    public void addUserType(UniUserType uniUserType) {
        List<UniUserType> allUserType = baseMapper.queryUserType(null);
        for (UniUserType type : allUserType) {
            checkLimitInterval(uniUserType.getLowerLimit(), type.getLowerLimit(), type.getUpperLimit());
            checkLimitIntervalOnUpdate(uniUserType.getUpperLimit(), type.getLowerLimit(), type.getUpperLimit());
        }
        baseMapper.addUserType(uniUserType);
    }

    /**
     * 修改用户类别
     * @param uniUserType
     */
    @Override
    public void modifyUserType(UniUserType uniUserType) {
        List<UniUserType> allUserType = baseMapper.queryUserType(uniUserType.getId());
        for (UniUserType type : allUserType) {
            checkLimitInterval(uniUserType.getLowerLimit(), type.getLowerLimit(), type.getUpperLimit());
            checkLimitIntervalOnUpdate(uniUserType.getUpperLimit(), type.getLowerLimit(), type.getUpperLimit());
        }
        baseMapper.modifyUserType(uniUserType);
    }

    /**
     * 判断值是否在区间内 (判断下限值)
     * @param needCheckValue 需要判断的值
     * @param lowerLimit 区间最小值(包含)
     * @param upperLimit 区间最大值(不包含)
     */
    public void checkLimitInterval(BigDecimal needCheckValue, BigDecimal lowerLimit, BigDecimal upperLimit) {
        if (needCheckValue.compareTo(lowerLimit) >= 0 && needCheckValue.compareTo(upperLimit) == -1) {
            throw new ApiException("传入数值存在冲突，请重试");
        }
    }

    /**
     * 判断值是否在区间内 (判断上限值)
     * @param needCheckValue 需要判断的值
     * @param lowerLimit 区间最小值(包含)
     * @param upperLimit 区间最大值(不包含)
     */
    public void checkLimitIntervalOnUpdate(BigDecimal needCheckValue, BigDecimal lowerLimit, BigDecimal upperLimit) {
        if (needCheckValue.compareTo(lowerLimit) > 0 && needCheckValue.compareTo(upperLimit) == -1) {
            throw new ApiException("传入数值存在冲突，请重试");
        }
    }

    /**
     * 修改 Member表 中用户类别
     * @param memberId
     * @param userTypeId
     */
    @Override
    public void modifyMemberType(Long memberId, Integer userTypeId) {
        baseMapper.modifyMemberType(memberId, userTypeId);
    }

    /**
     * 分页查询会员对应用户等级
     *
     * @param userTypeId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<AdminMemberUserTypeVO> queryAllUser(Integer userTypeId, String email,Integer pageNum, Integer pageSize) {
        Page<AdminMemberUserTypeVO> page = new Page<>(pageNum, pageSize);
        return baseMapper.queryAllUser(page, userTypeId,email);
    }

    /**
     * 判断用户类别是否存在
     *
     * @param userTypeId
     * @return
     */
    @Override
    public Boolean checkUserTypeExist(Integer userTypeId) {
        boolean flag = true;
        if (baseMapper.checkUserTypeExist(userTypeId) == 0 && baseMapper.checkUserTypeExistOnUniRelStrategy(userTypeId) == 0) {
            flag = false;
            return flag;
        }
        return flag;
    }


}
