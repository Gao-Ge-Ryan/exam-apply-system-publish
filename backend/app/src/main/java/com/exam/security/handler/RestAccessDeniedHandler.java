package com.exam.security.handler;

import com.alibaba.fastjson.JSON;

import com.exam.common.Result;
import com.exam.common.enums.impl.AuthenticationEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 高歌
 * @Description 权限校验处理器
 * @Date 2021-08-24
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        String str = JSON.toJSONString(Result.failed(AuthenticationEnum.Have_No_Right));
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }
}
