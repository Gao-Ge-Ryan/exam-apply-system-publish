package top.gaogle.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.gaogle.common.RegisterConst;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.util.CacheUtil;
import top.gaogle.framework.util.JsonUtil;
import top.gaogle.pojo.enums.HttpStatusEnum;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 自定义退出处理类 返回成功
 *
 * @author gaogle
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final CacheUtil cacheUtil;

    @Autowired
    public LogoutSuccessHandlerImpl(CacheUtil cacheUtil) {
        this.cacheUtil = cacheUtil;
    }

    /**
     * 退出处理
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        try (ServletOutputStream out = response.getOutputStream()) {
            String token = request.getHeader(Auth0TokenUtil.AUTHENTICATION_HEADER);
            cacheUtil.tokenCacheClean(token);
            response.setStatus(HttpServletResponse.SC_OK);
            I18nResult<String> result = I18nResult.newInstance();
            response.setContentType(RegisterConst.JSON_CONTENT_TYPE);
            String str = JsonUtil.object2Json(result.failed().setStatus(HttpStatusEnum.OK).setMessage(I18ResultCode.MESSAGE, "退出成功！"));
            out.write(str.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}
