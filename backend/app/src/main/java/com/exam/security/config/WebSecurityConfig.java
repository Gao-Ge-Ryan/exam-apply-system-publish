package com.exam.security.config;


import com.exam.security.filter.CustomAuthenticationFilter;
import com.exam.security.filter.JwtAuthenticationTokenFilter;
import com.exam.security.filter.WebSecurityCorsFilter;
import com.exam.security.handler.EntryPointUnauthorizedHandler;
import com.exam.security.handler.MyAuthenticationFailureHandler;
import com.exam.security.handler.MyAuthenticationSuccessHandler;
import com.exam.security.handler.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Spring Security 配置类
 *
 * @author gaoge
 * @EnableGlobalMethodSecurity 开启注解的权限控制，默认是关闭的。
 * prePostEnabled：使用表达式实现方法级别的控制，如：@PreAuthorize("hasRole('ADMIN')")
 * securedEnabled: 开启 @Secured 注解过滤权限，如：@Secured("ROLE_ADMIN")
 * jsr250Enabled: 开启 @RolesAllowed 注解过滤权限，如：@RolesAllowed("ROLE_ADMIN")
 * @create 2021/11/11
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    /**
     * 从容器中取出 AuthenticationManagerBuilder，执行方法里面的逻辑之后，放回容器
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
         */
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)//用登录之后的认证信息，再次访问时可以直接访问了，UsernamePasswordAuthenticationFilter之前添加拦截器
            .addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class);//自定义拦截器解决跨域问题，在security过滤链之前拦截


        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 角色校验时，会自动拼接 "ROLE_"
//                .antMatchers("/user/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/non-auth/**").permitAll()
                .antMatchers("/swagger-ui.html#/**","/swagger-ui.html/**","/**",
                        "/user/**","/goods/**",
                        "/paySuccessful/**").permitAll()
                .anyRequest().authenticated()   // 任何请求,登录后可以访问
                .and().addFilterAt(customAuthenticationFilter(),//JSON登陆实现，在 UsernamePasswordAuthenticationFilter，之前添加JSON格式
                UsernamePasswordAuthenticationFilter.class)
                //.addFilter(corsFilter())
                .formLogin().loginProcessingUrl("/user/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailureHandler)
                .and().logout()
                .and().headers().cacheControl();



//         打开运行报错，拦截链创建失败
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
//        //让Spring security 放行所有preflight request（cors 预检请求）
//        registry.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
//        // 处理异常情况：认证失败和权限不足
        http.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);
    }

//    没有解决跨域问题， 待理解
//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration cors = new CorsConfiguration();
//        cors.setAllowCredentials(true);
//        cors.addAllowedOrigin("http://192.168.1.6:8081/");
//        cors.addAllowedHeader("*");
//        cors.addAllowedMethod("*");
//        configurationSource.registerCorsConfiguration("/**", cors);
//        return new CorsFilter(configurationSource);
//    }
//     没有解决跨域问题，待理解
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowedOrigins(Arrays.asList("http://192.168.1.6:8081/"));
//    configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//}

    //注册自定义的UsernamePasswordAuthenticationFilter
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
        filter.setFilterProcessesUrl("/user/login");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

}
