package com.exam.security.handler;

import com.alibaba.fastjson.JSON;

import com.exam.common.Result;
import com.exam.common.enums.impl.AuthenticationEnum;
import com.exam.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 高歌
 * @Description 登录成功操作
 * @Date 2021-08-24
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
//        将token放入请求头中
        renderToken(httpServletResponse, token);
        System.out.println("登陆成功");
    }

    /**
     * 渲染返回 token 页面,因为前端页面接收的都是Result对象，故使用application/json返回
     *
     * @param response
     * @throws IOException
     */
    public void renderToken(HttpServletResponse response, String token) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.ok(AuthenticationEnum.Login_Successfully,token));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
