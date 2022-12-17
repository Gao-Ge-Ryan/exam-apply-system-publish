package com.exam.security.filter;


import com.exam.security.util.JwtTokenUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 高歌
 * @Description 请求拦截器，根据传过来的token，进行认证
 * @Date 2021-08-24
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(jwtTokenUtil.getHeader());
        if (!StringUtils.isEmpty(token)) {
            String username = jwtTokenUtil.getUsernameFromToken(token);
//            这段不用打开了，会报进程拒绝异常
//            try {
//                //令牌失效，重新登陆
//                if (username == null) {
//                    ServletOutputStream outputStream = response.getOutputStream();
//                    String loginInvalidate = JSON.toJSONString(new Result<String>(false, StatusCode.ERROR, "账号登录失效，请重新登录", "账号登录失效，请重新登录"));
//                    outputStream.write(loginInvalidate.getBytes());
//                    outputStream.flush();
//                    outputStream.close();
//                    response.setStatus(HttpServletResponse.SC_OK);
//                    throw new InvalidateTokenException("账号登录失效，请重新登录");
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    // 将用户信息存入 authentication，方便后续校验
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
