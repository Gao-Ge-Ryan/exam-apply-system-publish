package com.exam.security.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class GetTokenInfoUtil {
    /**
     * 获取登陆的用户名
     *
     * @author gaoge
     * @since 2021-11-14 12:14:54
     */
    public static String getUsername() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return username;
    }
}
