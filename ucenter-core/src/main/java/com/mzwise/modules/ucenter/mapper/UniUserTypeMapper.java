package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UniUserType;
import com.mzwise.modules.ucenter.vo.AdminMemberUserTypeVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 666
 * @since 2022-08-02
 */
public interface UniUserTypeMapper extends BaseMapper<UniUserType> {

    /**
     * 查询用户类别
     * @return
     */
    List<UniUserType> queryUserType(@Param("id") Integer id);

    /**
     * 增加用户类别
     * @param uniUserType
     * @return
     */
    void addUserType(UniUserType uniUserType);

    /**
     * 修改用户类别
     * @param uniUserType
     */
    void modifyUserType(UniUserType uniUserType);

    /**
     * 修改 Member表 中用户类别
     * @param memberId
     * @param userTypeId
     */
    void modifyMemberType(@Param("memberId") Long memberId, @Param("userTypeId") Integer userTypeId);

    /**
     * 分页查询会员对应用户等级
     * @param page
     * @param userTypeId
     * @return
     */
    Page<AdminMemberUserTypeVO> queryAllUser(Page<AdminMemberUserTypeVO> page, @Param("userTypeId") Integer userTypeId,@Param("email") String email);

    /**
     * 判断用户类别在类别表中是否存在
     * @param userTypeId
     * @return
     */
    Integer checkUserTypeExist(@Param("userTypeId") Integer userTypeId);

    /**
     * 判断用户类别在 uni_rel_strategy 是否存在
     * @param userTypeId
     * @return
     */
    Integer checkUserTypeExistOnUniRelStrategy(@Param("userTypeId") Integer userTypeId);

}
