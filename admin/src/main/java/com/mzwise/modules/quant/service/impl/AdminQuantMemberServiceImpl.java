package com.mzwise.modules.quant.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.quant.mapper.UcQuantMapper;
import com.mzwise.modules.quant.service.AdminQuantMemberService;
import com.mzwise.modules.quant.vo.AdminQuantMemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author piao
 * @Date 2021/7/1 18:41
 * @Description
 */
@Service
public class AdminQuantMemberServiceImpl implements AdminQuantMemberService {
    @Autowired
    private UcQuantMapper quantMapper;

    @Override
    public Page<AdminQuantMemberVO> listQuantMember(
            String nickname,
            String phone,
            String email,
            Integer pageNum, Integer pageSize) {
        Page<AdminQuantMemberVO> page = new Page<>(pageNum, pageSize);
        return quantMapper.listQuantMember(page, nickname, phone, email);
    }
}
