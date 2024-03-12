package com.mzwise.modules.quant.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.quant.vo.AdminQuantMemberVO;

/**
 * @Author piao
 * @Date 2021/7/1 18:41
 * @Description
 */
public interface AdminQuantMemberService {

    /**
     * 展示量化用户列表
     *
     * @param nickname
     * @param phone
     * @param email
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminQuantMemberVO> listQuantMember(
            String nickname,
            String phone,
            String email,
            Integer pageNum, Integer pageSize);
}
