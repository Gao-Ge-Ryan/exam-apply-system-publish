package com.exam.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class JwtUser implements UserDetails {
    private String username;
    private String nickname;
    private String password;
    private String avatar;
    private String userId;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(String username, String nickname, String password, String avatar, String userId, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.avatar = avatar;
        this.userId = userId;
        this.authorities = authorities;
    }

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public JwtUser(String username, String nickname, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.authorities = authorities;
    }

    public JwtUser(String username, String nickname, String password, String avatar, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.avatar = avatar;
        this.authorities = authorities;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
