package top.gaogle.framework.util;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.CastUtils;
import top.gaogle.common.RegisterConst;
import top.gaogle.framework.pojo.IndexedEnum;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 枚举工具类
 *
 * @author goge
 * @since 1.0.0
 */
public class EnumUtil {
    private EnumUtil() {
        throw new IllegalStateException(RegisterConst.PROHIBIT_INSTANTIATION);
    }

    private static final Logger logger = LoggerFactory.getLogger(EnumUtil.class);

    /**
     * 根据类名称获取所有枚举值
     *
     * @param className 枚举类名
     * @return 枚举值列表
     */
    public static <T extends IndexedEnum<Serializable>> T[] getEnumsByClassName(String className) {
        T[] indexedEnums = null;
        try {
            if (StringUtils.isEmpty(className)) {
                return null;
            }
            String referencePath = RegisterConst.ENUM_PACKAGE_PREFIX + className;
            Class<?> cls = Class.forName(referencePath);
            Method method = cls.getMethod(RegisterConst.ENUM_VALUES);
            indexedEnums = CastUtils.cast(method.invoke(null, (Object[]) null));

        } catch (Exception e) {
            logger.error("Error while processing enum: " + className, e);
        }
        return indexedEnums;
    }


}
