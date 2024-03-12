package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UniUserType;
import com.mzwise.modules.ucenter.vo.AdminMemberUserTypeVO;
import com.mzwise.modules.ucenter.vo.UserTypeStrategyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UniUserTypeService extends IService<UniUserType> {

    /**
     * 查询用户类别
     * @return
     */
    List<UniUserType> queryUserType();

    /**
     * 查询用户类别
     * @param id 用户类别id
     * @return
     */
    List<UniUserType> queryUserType(Integer id);

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
    void modifyMemberType(Long memberId, Integer userTypeId);

    /**
     * 分页查询会员对应用户等级
     *
     * @param userTypeId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminMemberUserTypeVO> queryAllUser(Integer userTypeId,String email, Integer pageNum, Integer pageSize);

    /**
     * 判断用户类别是否存在
     * @param userTypeId
     * @return
     */
    Boolean checkUserTypeExist(Integer userTypeId);

}
