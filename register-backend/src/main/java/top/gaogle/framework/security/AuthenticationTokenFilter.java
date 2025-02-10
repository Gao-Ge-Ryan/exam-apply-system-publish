package top.gaogle.framework.security;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.gaogle.framework.util.CacheUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证信息拦截器
 *
 * @author goge
 * @since 1.0.0
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private final RequestMatcher bypassRequestMatcher = new AntPathRequestMatcher("/auth/**");
    private final UserDetailsService userDetailsService;
    private final StringRedisTemplate stringRedisTemplate;
    private final CacheUtil cacheUtil;

    @Autowired
    public AuthenticationTokenFilter(UserDetailsService userDetailsService, StringRedisTemplate stringRedisTemplate, CacheUtil cacheUtil) {
        this.userDetailsService = userDetailsService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.cacheUtil = cacheUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(Auth0TokenUtil.AUTHENTICATION_HEADER);
        if (!StringUtils.isEmpty(token) && Boolean.TRUE.equals(Auth0TokenUtil.validateToken(token)) && cacheUtil.getTokenCacheExist(token)) {
            String username = Auth0TokenUtil.getUsernameFromToken(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 将用户信息存入 authentication，方便后续校验
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}