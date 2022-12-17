package com.exam.controller.exam;

import com.exam.common.Result;
import com.exam.common.SuperController;
import com.exam.dao.ExamUserDao;
import com.exam.pojo.param.ExamParam;
import com.exam.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * (Exam)表控制层
 *
 * @author gaoge
 * @since 2021-11-10 19:32:12
 */
@Api(tags = "Exam")
@RestController
@RequestMapping("/exam")
@CrossOrigin
public class ExamController extends SuperController {
    /**
     * 服务对象
     */
    @Resource
    private ExamService examService;

    @Autowired
    private ExamUserDao examUserDao;

    /**
     * 分页查询
     *
     * @param exam 筛选条件
     * @param pageNum      当前页
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("/{pageNum}/{pageSize}")
    public Result queryByPage(@RequestBody ExamParam exam,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize) {
        return success(this.examService.queryByPage(exam, pageNum, pageSize));
    }

    @ApiOperation("")
    @GetMapping("/examType/{type}")
    public Result queryByExamType(@PathVariable("type") String examType)
                              {
        return success(this.examService.queryByExamType(examType));
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
        return success(this.examService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param exam 实体
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    public Result add(@RequestBody ExamParam exam) {
        return success(this.examService.insert(exam));
    }

    /**
     * 编辑数据
     *
     * @param exam 实体
     * @return 编辑结果
     */
    @ApiOperation("编辑数据")
    @PutMapping
    public Result edit(@RequestBody ExamParam exam) {
        return success(this.examService.update(exam));
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
        return this.examService.deleteById(id);
    }

}

