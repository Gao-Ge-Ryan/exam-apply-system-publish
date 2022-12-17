package com.exam.controller.exam;

import com.exam.common.Result;
import com.exam.common.Utils.EnumUtil;
import com.exam.common.enums.EnumEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 枚举访问
 *
 * @author gaoge
 * @since 2021-11-07 13:42:39
 */
@Api(tags = "枚举访问")
@RestController
@RequestMapping("/enum")
@CrossOrigin
public class EnumController {

    /**
     * 本方法通过枚举类名进行访问枚举
     *
     * @param enumName  枚举的类名
     * @return
     */
    @ApiOperation("枚举访问--通过类名访问")
    @GetMapping("/{enumName}")
    public Result enumerationAccess(@PathVariable("enumName") String enumName) {
        List<EnumEntity> enumEntities = EnumUtil.getByName(enumName);
        return Result.ok(enumEntities);
    }
}
