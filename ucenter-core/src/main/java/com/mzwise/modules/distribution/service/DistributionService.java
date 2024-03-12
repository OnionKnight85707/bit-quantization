package com.mzwise.modules.distribution.service;

import com.mzwise.modules.ucenter.vo.SimpleMemberTreeVO;

/**
 * @Author piao
 * @Date 2021/05/20
 */
public interface DistributionService {
    /**
     * 查询某用户下的简易团队信息
     *
     * @param memberId
     * @return
     */
    SimpleMemberTreeVO queryTreeByMemberId(Long memberId);

    /**
     * 获取用户最顶级的pid(非O)
     *
     * @param memberId
     * @return
     */
    Long getTopPid(Long memberId);

    /**
     * 更新用户有效状态
     *
     * @param memberId
     */
    void updateIsEffective(Long memberId);

    /**
     * 某个新用户充值服务费 团队升级
     *
     * @param memberId
     * @return
     */
    void updateLevel(Long memberId);
}
