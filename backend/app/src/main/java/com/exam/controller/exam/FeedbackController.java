package com.exam.controller.exam;

import com.exam.common.Result;
import com.exam.common.SuperController;
import com.exam.pojo.param.FeedbackParam;
import com.exam.service.FeedbackService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 问题反馈(Feedback)表控制层
 *
 * @author gaoge
 * @since 2021-11-11 11:12:37
 */
@Api(tags = "Feedback")
@RestController
@RequestMapping("/feedback")
@CrossOrigin
public class FeedbackController extends SuperController {
    /**
     * 服务对象
     */
    @Resource
    private FeedbackService feedbackService;

    /**
     * 分页查询
     *
     * @param feedback 筛选条件
     * @param pageNum      当前页
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @PostMapping("/{pageNum}/{pageSize}")
    public Result queryByPage(@RequestBody FeedbackParam feedback,
                              @PathVariable("pageNum") Integer pageNum,
                              @PathVariable("pageSize") Integer pageSize) {
        return success(this.feedbackService.queryByPage(feedback, pageNum, pageSize));
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
        return success(this.feedbackService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param feedback 实体
     * @return 新增结果
     */
    @ApiOperation("新增数据")
    @PostMapping
    public Result add(@RequestBody FeedbackParam feedback) {
        return success(this.feedbackService.insert(feedback));
    }

    /**
     * 编辑数据
     *
     * @param feedback 实体
     * @return 编辑结果
     */
    @ApiOperation("编辑数据")
    @PutMapping
    public Result edit(@RequestBody FeedbackParam feedback) {
        return success(this.feedbackService.update(feedback));
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
        return success(this.feedbackService.deleteById(id));
    }

}

