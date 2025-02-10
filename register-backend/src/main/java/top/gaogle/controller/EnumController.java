package top.gaogle.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.IndexedEnum;
import top.gaogle.framework.util.EnumUtil;

import java.io.Serializable;

/**
 * 枚举访问
 *
 * @author goge
 * @since 1.0.0
 */
@RestController
@RequestMapping("/enums")
public class EnumController {
    /**
     * 根据枚举类名称获取所有枚举值
     *
     * @param className 枚举类名
     * @return 枚举值列表
     */
    @GetMapping("/{class_name}")
    public ResponseEntity<I18nResult<IndexedEnum<Serializable>[]>> enumerationAccess(@PathVariable("class_name") String className) {
        I18nResult<IndexedEnum<Serializable>[]> result = I18nResult.newInstance();
        result.succeed().setData(EnumUtil.getEnumsByClassName(className));
        return result.toResponseEntity();
    }
}
