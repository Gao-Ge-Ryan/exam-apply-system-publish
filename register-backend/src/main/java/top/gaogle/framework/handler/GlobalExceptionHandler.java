package top.gaogle.framework.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;
import top.gaogle.framework.exception.RateLimiterException;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 *
 * @author goge
 * @since 1.0.0
 */
@ControllerAdvice
public class GlobalExceptionHandler extends AbstractHandlerExceptionResolver {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<I18nResult<String>> accessDeniedException(Exception ex) throws Exception {
        throw ex;

    }

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<I18nResult<String>> authenticationException(Exception ex) throws Exception {
        throw ex;
    }

    /**
     * 限流异常拦截
     */
    @ExceptionHandler({RateLimiterException.class})
    @ResponseBody
    public ResponseEntity<I18nResult<String>> handleRateLimiterException(Exception ex) {
        I18nResult<String> result = I18nResult.newInstance();
        result.failedBadRequest().setMessage(ex.getMessage());
        return result.toResponseEntity();
    }

    @ExceptionHandler({Throwable.class})
    @ResponseBody
    public ResponseEntity<I18nResult<String>> handleException(Exception ex) {
        I18nResult<String> result = I18nResult.newInstance();
        result.failed().setMessage(I18ResultCode.GLOBAL_EXCEPTION_MESSAGE, ex.getMessage());
        log.error("GlobalExceptionHandler: ", ex);
        return result.toResponseEntity();
    }

    @Override
    protected ModelAndView doResolveException(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, Object handler, @NonNull Exception ex) {
        log.error("doResolveException", ex);
        return null;
    }
}
