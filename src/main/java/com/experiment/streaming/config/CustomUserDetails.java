package com.experiment.streaming.config;

import com.experiment.streaming.entity.UserEntity;
import com.experiment.streaming.mapper.ConstantMapper;
import com.experiment.streaming.mapper.UserMapper;
import com.experiment.streaming.mapper.UserSummaryMapper;
import com.experiment.streaming.model.Role;
import com.experiment.streaming.model.User;
import com.experiment.streaming.model.UserSummary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class CustomUserDetails implements UserDetails
{
    private UserEntity user;

    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        return Arrays.asList(authority);
    }

    public UserSummary getUser(){
        return UserSummaryMapper.toModel(user);
    }

    public UserEntity getUserEntity(){ return user;}

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.isActive();
    }
}
