package com.exam.security.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 高歌
 * @Description 解决了springSecurity跨域问题
 * @Date 2021-08-24
 */
public class WebSecurityCorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, x-requested-with, Cache-Control");
        //浏览器会有个欲请求（"OPTIONS"），请求通过才会发送get/post请求，所以直接放行
        HttpServletRequest req = (HttpServletRequest) request;
        if (req.getMethod().equals("OPTIONS")){
            res.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        chain.doFilter(request, res);
    }
    @Override
    public void destroy() {
    }
}
