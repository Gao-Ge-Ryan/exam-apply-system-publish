package top.gaogle.framework.security;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
import java.nio.charset.StandardCharsets;

/**
 * 权限校验处理器
 *
 * @author goge
 * @since 1.0.0
 */
@Component
public class ResetAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        try (ServletOutputStream out = response.getOutputStream()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            I18nResult<String> result = I18nResult.newInstance();
            response.setContentType(RegisterConst.JSON_CONTENT_TYPE);
            String str = JsonUtil.object2Json(result.failed().setStatus(HttpStatusEnum.FORBIDDEN).setMessage(I18ResultCode.MESSAGE, "您的权限不足，无法访问该资源！"));
            out.write(str.getBytes(StandardCharsets.UTF_8));
            out.flush();
        }
    }
}
