package com.exam.service.impl;

import com.exam.common.Utils.DateUtil;
import com.exam.common.Utils.IDGenerator;
import com.exam.dao.FeedbackDao;
import com.exam.pojo.model.FeedbackModel;
import com.exam.pojo.param.FeedbackParam;
import com.exam.service.FeedbackService;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import java.util.List;

/**
 * 问题反馈(Feeback)表服务实现类
 *
 * @author gaoge
 * @since 2021-11-11 11:12:37
 */
@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {
    @Resource
    private FeedbackDao feedbackDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public FeedbackModel queryById(String id) {
        return this.feedbackDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param feedback 筛选条件
     * @param pageNum      当前页数
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    @Override
    public PageInfo<FeedbackModel> queryByPage(FeedbackParam feedback, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<FeedbackModel> feedbacks =this.feedbackDao.queryAllByLimit(feedback);
        return new PageInfo<>(feedbacks);
    }

    /**
     * 新增数据
     *
     * @param feedback 实例对象
     * @return 实例对象
     */
    @Override
    public FeedbackParam insert(FeedbackParam feedback) {
        feedback.setId(IDGenerator.StringID());
        feedback.setCreateTime(DateUtil.getCurrentTimeMillis());
        this.feedbackDao.insert(feedback);
        return feedback;
    }

    /**
     * 修改数据
     *
     * @param feedback 实例对象
     * @return 实例对象
     */
    @Override
    public FeedbackModel update(FeedbackParam feedback) {
        this.feedbackDao.update(feedback);
        return this.queryById(feedback.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.feedbackDao.deleteById(id) > 0;
    }
}
