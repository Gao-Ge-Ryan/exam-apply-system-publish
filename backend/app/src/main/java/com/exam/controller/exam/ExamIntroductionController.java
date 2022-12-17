package com.exam.controller.exam;

import com.exam.common.Result;
import com.exam.common.SuperController;
import com.exam.pojo.param.ExamIntroductionParam;
import com.exam.service.ExamIntroductionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * (ExamIntroduction)表控制层
 *
 * @author gaoge
 * @since 2021-11-10 19:41:39
 */
@Api(tags = "ExamIntroduction")
@RestController
@RequestMapping("/examIntroduction")
@CrossOrigin
public class ExamIntroductionController extends SuperController {
    /**
     * 服务对象
     */
    @Resource
    private ExamIntroductionService examIntroductionService;

    /**
     * 分页查询
     *
     * @param examIntroduction 筛选条件
     * @param pageNum      当前页
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("/{pageNum}/{pageSize}")
    public Result queryByPage(@RequestBody ExamIntroductionParam examIntroduction,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize) {
        return success(this.examIntroductionService.queryByPage(examIntroduction, pageNum, pageSize));
    }

    /**
     * 根据类型查询
     * @return 查询结果
     */
    @ApiOperation("通过类型查询")
    @GetMapping("type")
    public Result queryByType(String typeEnum) {
        return success(this.examIntroductionService.queryByType(typeEnum));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("id") String id) {
        return success(this.examIntroductionService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param examIntroduction 实体
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    public Result add(@RequestBody ExamIntroductionParam examIntroduction) {
        return success(this.examIntroductionService.insert(examIntroduction));
    }

    /**
     * 编辑数据
     *
     * @param examIntroduction 实体
     * @return 编辑结果
     */
    @ApiOperation("编辑数据")
    @PutMapping
    public Result edit(@RequestBody ExamIntroductionParam examIntroduction) {
        return success(this.examIntroductionService.update(examIntroduction));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @ApiOperation("删除数据")
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") String id) {
        return this.examIntroductionService.deleteById(id);
    }

}

