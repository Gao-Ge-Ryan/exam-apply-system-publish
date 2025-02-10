package top.gaogle.framework.security;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.gaogle.common.RegisterConst;
import top.gaogle.pojo.enums.HttpStatusEnum;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.util.JsonUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

/**
 * 认证失败处理类 返回未授权
 *
 * @author goge
 * @since 1.0.0
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint, Serializable {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        try (ServletOutputStream out = response.getOutputStream()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            I18nResult<String> result = I18nResult.newInstance();
            response.setContentType(RegisterConst.JSON_CONTENT_TYPE);
            String str = JsonUtil.object2Json(result.failed().setStatus(HttpStatusEnum.UNAUTHORIZED).setMessage(I18ResultCode.MESSAGE, "您的认证信息失效，请您重新登录！"));
            out.write(str.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}