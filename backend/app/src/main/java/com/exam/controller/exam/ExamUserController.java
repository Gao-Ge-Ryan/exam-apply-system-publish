package com.exam.controller.exam;

import com.exam.common.Result;
import com.exam.common.SuperController;
import com.exam.pojo.param.ExamUserParam;
import com.exam.security.util.GetTokenInfoUtil;
import com.exam.service.ExamUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * (ExamUser)表控制层
 *
 * @author gaoge
 * @since 2021-11-10 19:44:05
 */
@Api(tags = "ExamUser")
@RestController
@RequestMapping("/examUser")
@CrossOrigin
public class ExamUserController extends SuperController {
    /**
     * 服务对象
     */
    @Resource
    private ExamUserService examUserService;

    /**
     * 分页查询
     *
     * @param examUser 筛选条件
     * @param pageNum      当前页
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("/{pageNum}/{pageSize}")
    public Result queryByPage(@RequestBody ExamUserParam examUser,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize) {
        return success(this.examUserService.queryByPage(examUser, pageNum, pageSize));
    }

    @GetMapping("/score")
    public Result queryScore(){
        return success(this.examUserService.queryScore());
    }

    @GetMapping("/examName")
    public Result queryExamName(){
        return success(this.examUserService.queryExamName());
    }

    /**
     * 分页查询
     *
     * @param examUser 筛选条件
     * @param pageNum      当前页
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("/print/{pageNum}/{pageSize}")
    public Result queryByPageAndPrint(@RequestBody ExamUserParam examUser,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize) {
        return success(this.examUserService.queryByPageAndPrint(examUser, pageNum, pageSize));
    }

    /**
     * 分页查询用户
     *
     * @param examUser 筛选条件
     * @param pageNum      当前页
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    @ApiOperation("分页查询用户报名考试")
    @PostMapping("/user/{pageNum}/{pageSize}")
    public Result queryByPageAndUser(@RequestBody ExamUserParam examUser,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize) {
        return success(this.examUserService.queryByPageAndUser(examUser, pageNum, pageSize));
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
        return success(this.examUserService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param examUser 实体
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    public Result add(@RequestBody ExamUserParam examUser) {
        return success(this.examUserService.insert(examUser));
    }

    /**
     * 编辑数据
     *
     * @param examUser 实体
     * @return 编辑结果
     */
    @ApiOperation("编辑数据")
    @PutMapping
    public Result edit(@RequestBody ExamUserParam examUser) {
        return success(this.examUserService.update(examUser));
    }

    /**
     * 编辑数据
     *
     * @param examUser 实体
     * @return 编辑结果
     */
    @ApiOperation("编辑数据")
    @PatchMapping("/patch")
    public Result patchInfo(@RequestBody ExamUserParam examUser) {
        String username = GetTokenInfoUtil.getUsername();
        examUser.setUserId(username);
        return success(this.examUserService.update(examUser));
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
        return success(this.examUserService.deleteById(id));
    }

}

