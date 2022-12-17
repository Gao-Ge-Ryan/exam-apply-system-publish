package com.exam.security.service;



import com.exam.pojo.model.UserModel;
import com.exam.security.entity.JwtUser;

import com.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userService.selectByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s.这个用户不存在", username));
        }
//        权限方法
//        List<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(Role::getRolename).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//        return new JwtUser(user.getUsername(), user.getPassword(), user.getState(), authorities);
//        权限
//        String permissions = "user,admin";
        String role = user.getRole().getEnumCode().toString();
        return new JwtUser(user.getUserName(),user.getNickName(), user.getPassword(), user.getAvatar(),user.getId(),AuthorityUtils.commaSeparatedStringToAuthorityList(role));
    }
}
