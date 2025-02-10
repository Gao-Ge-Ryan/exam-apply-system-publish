package top.gaogle.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.CastUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.gaogle.framework.annotation.Anonymous;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.security.Auth0TokenUtil;
import top.gaogle.framework.security.UserDetailsCustomizer;
import top.gaogle.framework.util.CacheUtil;
import top.gaogle.pojo.domain.AuthenticationPacket;
import top.gaogle.pojo.dto.RegistryDTO;
import top.gaogle.pojo.dto.VerificationCodeDTO;
import top.gaogle.service.AuthService;

/**
 * 认证
 *
 * @author gaogle
 * @since 1.0.0
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final CacheUtil cacheUtil;

    public AuthController(AuthenticationManager authenticationManager, AuthService authService, CacheUtil cacheUtil) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.cacheUtil = cacheUtil;
    }

    /**
     * 获取邮箱验证码
     */
    @Anonymous
    @PostMapping("/verification_code")
    public ResponseEntity<I18nResult<Boolean>> verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        return authService.verificationCode(verificationCodeDTO).toResponseEntity();
    }

    /**
     * 注册接口
     */
    @Anonymous
    @PostMapping("/registry")
    public ResponseEntity<I18nResult<Object>> registry(@RequestBody RegistryDTO registryDTO) {
        return authService.registry(registryDTO).toResponseEntity();
    }

    /**
     * 登录接口
     */
    @Anonymous
    @PostMapping("/login")
    public ResponseEntity<I18nResult<String>> login(@RequestBody AuthenticationPacket authenticationPacket) {
        I18nResult<String> result = I18nResult.newInstance();
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationPacket.getEmail(), authenticationPacket.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsCustomizer userDetailsCustomizer = CastUtils.cast(authentication.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = Auth0TokenUtil.generateToken(userDetailsCustomizer);
            cacheUtil.tokenCache(token);
            return result.succeed().setData(token).toResponseEntity();
        } catch (BadCredentialsException badCredentialsException) {
            log.error("账号或密码错误：", badCredentialsException);
            result.failed().setMessage(I18ResultCode.MESSAGE, "账号或密码错误");
        } catch (AccountExpiredException accountExpiredException) {
            log.error("User account has expired：", accountExpiredException);
            result.failed().setMessage(I18ResultCode.MESSAGE, "User account has expired");
        } catch (LockedException lockedException) {
            log.error("User account is locked：", lockedException);
            result.failed().setMessage(I18ResultCode.MESSAGE, "User account is locked");
        } catch (DisabledException disabledException) {
            log.error("User is disabled：", disabledException);
            result.failed().setMessage(I18ResultCode.MESSAGE, "User is disabled");
        } catch (Exception e) {
            log.error("登录失败：", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "登录失败，请联系管理员！");
        }
        return result.toResponseEntity();
    }
}

