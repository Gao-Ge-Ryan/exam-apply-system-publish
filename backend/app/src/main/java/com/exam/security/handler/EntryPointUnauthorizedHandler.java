package com.exam.security.handler;

import com.alibaba.fastjson.JSON;

import com.exam.common.Result;
import com.exam.common.enums.impl.AuthenticationEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 高歌
 * @Description 身份校验失败处理器，如 token 错误
 * @Date 2021-08-24
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.failed(AuthenticationEnum.Not_Login));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
