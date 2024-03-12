package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.common.api.CommonResult;
import com.mzwise.modules.ucenter.dto.ShowPartnerParam;
import com.mzwise.modules.ucenter.dto.UcMemberRegisterParam;
import com.mzwise.modules.ucenter.dto.UcenterPartnerParam;
import com.mzwise.modules.ucenter.entity.UcMember;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

public interface UcenterMemberService extends IService<UcMember> {

    /**
     * 注册功能
     */
    UcMember register(UcMemberRegisterParam param);

    /**
     * 根据用户ID取用户信息
     * @param id
     * @return
     */
    UserDetails loadUserById(String id);

    /**
     * 根据用户ID取用户信息
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);

    /**
     * 手机/邮箱登录功能
     *
     * @param account 用户名
     * @return 生成的JWT的token
     */
    String login(String account);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password, HttpServletRequest request);


    /**
     * 查看上级返佣额度
     * @param param
     * @return
     */
    CommonResult checkParentCommissionRate(UcenterPartnerParam param, Long parentId);



    /**
     *  检查用户输入的验证码是否正确
     * @param privateKey
     * @param authCode
     * @return
     */
    Boolean checkInputGoogleCode( String privateKey, String authCode) throws Exception;

    /**
     *  给用户生成谷歌验证器私钥二维码
     *
     * @return
     */
    String cratePrivateKeyQRCode(Long id,String privateKey);

}
