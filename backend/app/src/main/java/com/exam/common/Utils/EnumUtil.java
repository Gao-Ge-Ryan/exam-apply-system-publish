package com.exam.common.Utils;

import com.exam.common.enums.EnumCode;
import com.exam.common.enums.EnumEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射  实现访问路径一劳永逸
 *
 * @author gaoge
 */
@Component
public class EnumUtil {
    /**
     * 枚举类对应的包路径
     *
     * @Value("${application.enum.default.package.path}")
     * @Value 给静态变量赋值赋不进去，用get/set方法进行复制
     */
    private static String enum_DefaultPackage;

    public static List<EnumEntity> getByName(String shortName) {
        if (StringUtils.isEmpty(shortName)) {
            return new ArrayList<>();
        }
        String methodName = "values";
        String packageName = enum_DefaultPackage + "." + shortName;
        List<EnumEntity> enumEntities = new ArrayList<>();
        try {
            Class<?> cls = Class.forName(packageName);
            Method method = cls.getMethod(methodName);
            EnumCode[] enumCodes = (EnumCode[]) method.invoke(null, null);
            for (EnumCode enumCode : enumCodes) {
                EnumEntity enumEntity = new EnumEntity();
                enumEntity.setCode(enumCode.getCode());
                enumEntity.setMsg(enumCode.getMsg());
                enumEntity.setEnumCode(enumCode);
                enumEntities.add(enumEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumEntities;
    }

    @Value("${application.enum.default.package.path}")
    private void setEnum_DefaultPackage(String enum_DefaultPackage) {
        EnumUtil.enum_DefaultPackage = enum_DefaultPackage;
    }
}
