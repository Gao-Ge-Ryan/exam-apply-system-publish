package top.gaogle.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Component
public class CacheUtil {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final StringRedisTemplate stringRedisTemplate;

    private final RedisTemplate redisTemplate;

    private static final ConcurrentMap<String, String> tokenMap = new ConcurrentHashMap<>();

    @Autowired
    public CacheUtil(StringRedisTemplate stringRedisTemplate, RedisTemplate redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    public boolean tokenCache(String token) {
        log.info("tokenCache tokenMap before size:{}", tokenMap.size());
        String base64 = SecretUtil.getEncoderByBase64(token);
        tokenMap.put(base64, base64);
        stringRedisTemplate.opsForValue().set(StringUtil.redisKey("user", "token", base64), base64, 100, TimeUnit.HOURS);
        log.info("tokenCache tokenMap after size:{}", tokenMap.size());
        return true;
    }

    public boolean getTokenCacheExist(String token) {
        String base64 = SecretUtil.getEncoderByBase64(token);
        String base64Value = tokenMap.getOrDefault(base64, "");
        if (StringUtils.isEmpty(base64Value)) {
            String redisValue = stringRedisTemplate.opsForValue().get(StringUtil.redisKey("user", "token", base64));
            if (StringUtils.isEmpty(redisValue)) {
                return false;
            }
            base64Value = redisValue;
            tokenMap.put(base64, redisValue);
        }
        return Objects.equals(base64, base64Value);
    }

    public boolean tokenCacheClean(String token) {
        log.info("tokenCacheClean tokenMap before size:{}", tokenMap.size());
        String base64 = SecretUtil.getEncoderByBase64(token);
        tokenMap.remove(base64);
        stringRedisTemplate.delete(StringUtil.redisKey("user", "token", base64));
        log.info("tokenCacheClean tokenMap after size:{}", tokenMap.size());
        return true;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public String getCacheObjectJSON(final String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObjectJSON(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, JsonUtil.object2Json(value), timeout, timeUnit);
    }


    public static ConcurrentMap<String, String> getTokenMap() {
        return tokenMap;
    }
}
