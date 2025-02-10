package top.gaogle.framework.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import top.gaogle.framework.annotation.RateLimiter;
import top.gaogle.framework.exception.RateLimiterException;
import top.gaogle.framework.util.IpUtil;
import top.gaogle.framework.util.StringUtil;
import top.gaogle.pojo.enums.LimitType;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * 限流切面处理
 */
@Aspect
@Component
public class RateLimiterAspect {

    private static final Logger log = LoggerFactory.getLogger(RateLimiterAspect.class);

    private final RedisTemplate<Object, Object> redisTemplate;
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisScript<Long> limitScript;

    @Autowired
    public RateLimiterAspect(RedisTemplate<Object, Object> redisTemplate, StringRedisTemplate stringRedisTemplate, RedisScript<Long> limitScript) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.limitScript = limitScript;
    }

    /**
     * 在方法执行前进行限流校验
     *
     * @param point       JoinPoint
     * @param rateLimiter RateLimiter 注解
     */
    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) {
        String combineKey = buildCombineKey(rateLimiter, point);
        List<String> keys = Collections.singletonList(combineKey);
        Long requestCount = stringRedisTemplate.execute(limitScript, keys, String.valueOf(rateLimiter.count()), String.valueOf(rateLimiter.time()));
        validateRequest(requestCount, rateLimiter.count(), combineKey);
    }

    /**
     * 验证请求是否超过限流阈值
     *
     * @param requestCount 当前请求数
     * @param maxCount     最大允许请求数
     * @param combineKey   缓存键
     */
    private void validateRequest(Long requestCount, int maxCount, String combineKey) {
        if (StringUtil.isNull(requestCount) || requestCount > maxCount) {
            log.info("限制请求数 '{}', 当前请求数 '{}', 缓存 key '{}'", maxCount, requestCount, combineKey);
            throw new RateLimiterException("系统访问过于频繁，请稍候再试");
        }
    }

    /**
     * 构建 Redis 缓存键
     *
     * @param rateLimiter RateLimiter 注解
     * @param point       JoinPoint
     * @return 缓存键
     */
    private String buildCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder keyBuilder = new StringBuilder(rateLimiter.key());

        if (rateLimiter.limitType() == LimitType.IP) {
            keyBuilder.append(IpUtil.getIpAddr()).append("-");
        }

        Method method = ((MethodSignature) point.getSignature()).getMethod();
        keyBuilder.append(method.getDeclaringClass().getName())
                .append("-")
                .append(method.getName());

        return keyBuilder.toString();
    }
}
