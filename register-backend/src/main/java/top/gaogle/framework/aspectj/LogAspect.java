package top.gaogle.framework.aspectj;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import top.gaogle.framework.annotation.Log;
import top.gaogle.framework.manager.AsyncFactory;
import top.gaogle.framework.manager.AsyncManager;
import top.gaogle.framework.security.UserDetailsCustomizer;
import top.gaogle.framework.util.*;
import top.gaogle.pojo.domain.OperateLog;
import top.gaogle.pojo.enums.BusinessStatusEnum;
import top.gaogle.pojo.enums.HttpMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * 操作日志记录处理
 *
 * @author gaogle
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 排除敏感属性字段
     */
    protected static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    /**
     * 计算操作消耗时间
     */
    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = new NamedThreadLocal<>("Cost Time");

    /**
     * 处理请求前执行
     */
    @Before(value = "@annotation(controllerLog)")
    public void boBefore(JoinPoint joinPoint, Log controllerLog) {
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, final Exception e, Object jsonResult) {
        try {
            // 获取当前的用户
            UserDetailsCustomizer loginUser = SecurityUtil.getLoginUser();
            // *========数据库日志=========* //
            OperateLog operLog = new OperateLog();
            operLog.setStatus(BusinessStatusEnum.SUCCESS);
            // 请求的地址
            String ip = IpUtil.getIpAddr();
            operLog.setOperateIp(ip);
            operLog.setOperateUrl(StringUtil.substring(ServletUtil.getRequest().getRequestURI(), 0, 255));
            if (loginUser != null) {
                operLog.setOperator(loginUser.getUsername());
                operLog.setEnterpriseId(loginUser.getEnterpriseId());
            }
            if (e != null) {
                operLog.setStatus(BusinessStatusEnum.FAIL);
                operLog.setErrorMsg(StringUtil.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtil.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, controllerLog, operLog, jsonResult);
            // 设置消耗时间
            operLog.setCostTime(System.currentTimeMillis() - TIME_THREAD_LOCAL.get());
            operLog.setCreateAt(DateUtil.currentTimeMillis());
            operLog.setId(UniqueUtil.getUniqueId());
            // 保存数据库
            AsyncManager.me().execute(AsyncFactory.recordOperate(operLog));
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("异常信息:", exp);
        } finally {
            TIME_THREAD_LOCAL.remove();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperateLog operLog, Object jsonResult) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperateType(log.operatorType());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog, log.excludeParamNames());
        }
        // 是否需要保存response，参数和值
        if (log.isSaveResponseData() && StringUtil.isNotNull(jsonResult)) {
            operLog.setJsonResult(StringUtil.substring(JsonUtil.object2Json(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, OperateLog operLog, String[] excludeParamNames) throws Exception {
        Map<?, ?> paramsMap = ServletUtil.getParamMap(ServletUtil.getRequest());
        String requestMethod = operLog.getRequestMethod();
        if (StringUtil.isEmpty(paramsMap) && (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod) || HttpMethod.PATCH.name().equals(requestMethod))) {
            String params = argsArrayToString(joinPoint.getArgs(), excludeParamNames);
            operLog.setOperateParam(StringUtil.substring(params, 0, 2000));
        } else {
            operLog.setOperateParam(StringUtil.substring(JsonUtil.object2Json(paramsMap), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray, String[] excludeParamNames) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (StringUtil.isNotNull(o) && !isFilterObject(o)) {
                    try {//todo gaoge 忽略敏感字段
//                        String jsonObj = JSON.toJSONString(o, excludePropertyPreFilter(excludeParamNames));
                        String jsonObj = JsonUtil.object2Json(o);
                        params.append(jsonObj).append(" ");
                    } catch (Exception e) {
                    }
                }
            }
        }
        return params.toString().trim();
    }

//    /**
//     * 忽略敏感属性
//     */
//    public PropertyPreExcludeFilter excludePropertyPreFilter(String[] excludeParamNames) {
//        return new PropertyPreExcludeFilter().addExcludes(ArrayUtils.addAll(EXCLUDE_PROPERTIES, excludeParamNames));
//    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse || o instanceof BindingResult;
    }
}
