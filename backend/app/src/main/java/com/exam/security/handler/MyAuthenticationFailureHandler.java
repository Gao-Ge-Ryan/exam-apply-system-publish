package com.exam.security.handler;

import com.alibaba.fastjson.JSON;

import com.exam.common.Result;
import com.exam.common.enums.impl.AuthenticationEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 高歌
 * @Description 登录失败操作
 * @Date 2021-08-24
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = httpServletResponse.getOutputStream();
        String str = JSON.toJSONString(Result.failed(AuthenticationEnum.Login_Failure));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
