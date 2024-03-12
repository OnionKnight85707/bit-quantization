package com.mzwise.user;

import com.mzwise.constant.MemberStatusEnum;
import com.mzwise.modules.ucenter.entity.UcMember;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoginDetail implements UserDetails {
    @Getter
    private UcMember ucMember;

    public LoginDetail(UcMember ucMember) {
        this.ucMember = ucMember;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return ucMember.getPassword();
    }

    @Override
    public String getUsername() {
        return ucMember.getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ucMember.getStatus().equals(MemberStatusEnum.NORMAL);
    }
}
