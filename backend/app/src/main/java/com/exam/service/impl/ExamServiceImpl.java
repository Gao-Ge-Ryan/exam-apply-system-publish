package com.exam.service.impl;

import com.exam.common.Result;
import com.exam.common.Utils.DateUtil;
import com.exam.common.Utils.IDGenerator;
import com.exam.common.enums.impl.ExamStatusEnum;
import com.exam.common.enums.impl.ExamTypeEnum;
import com.exam.dao.ExamDao;
import com.exam.dao.ExamUserDao;
import com.exam.pojo.model.ExamModel;
import com.exam.pojo.model.ExamUserModel;
import com.exam.pojo.param.ExamParam;
import com.exam.security.util.GetTokenInfoUtil;
import com.exam.service.ExamService;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Exam)表服务实现类
 *
 * @author gaoge
 * @since 2021-11-10 19:32:16
 */
@Service("examService")
public class ExamServiceImpl implements ExamService {
    @Resource
    private ExamDao examDao;
    @Resource
    private ExamUserDao examUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ExamModel queryById(String id) {
        return this.examDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param exam     筛选条件
     * @param pageNum  当前页数
     * @param pageSize 每页显示数量
     * @return 查询结果
     */
    @Override
    public PageInfo<ExamModel> queryByPage(ExamParam exam, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExamModel> exams = this.examDao.queryAllByLimit(exam);
        return new PageInfo<>(exams);
    }

    /**
     * 新增数据
     *
     * @param exam 实例对象
     * @return 实例对象
     */
    @Override
    public ExamParam insert(ExamParam exam) {
        exam.setId(IDGenerator.StringID());
        exam.setCreateTime(DateUtil.getCurrentTimeMillis());
        exam.setUpdateTime(DateUtil.getCurrentTimeMillis());
        exam.setStatus(ExamStatusEnum.NotStarted);
        exam.setUserId(GetTokenInfoUtil.getUsername());
        this.examDao.insert(exam);
        return exam;
    }

    /**
     * 修改数据
     *
     * @param exam 实例对象
     * @return 实例对象
     */
    @Override
    public ExamModel update(ExamParam exam) {
        this.examDao.update(exam);
        exam.setUpdateTime(DateUtil.getCurrentTimeMillis());
        return this.queryById(exam.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public Result deleteById(String id) {
        List<ExamUserModel> examUserModels = examUserDao.selectByExampleId(id);
        if (examUserModels != null && examUserModels.size() > 0) {
            return Result.failed("该场考试已有" + examUserModels.size() + "人报名，请先删除");
        }
        return Result.ok(this.examDao.deleteById(id) > 0);
    }

    @Override
    public List<ExamModel> queryByExamType(String exampType) {
        ExamTypeEnum examTypeEnum = ExamTypeEnum.returnValue(exampType);
        ExamParam examParam = new ExamParam();
        examParam.setExamType(examTypeEnum);
        List<ExamModel> exams = this.examDao.queryAllByLimit(examParam);
        long currentTimeMillis = DateUtil.getCurrentTimeMillis();
        List<ExamModel> collect = exams.stream().filter(n -> n.getApplyEndTime() >= currentTimeMillis && n.getApplyStartTime() <= currentTimeMillis).collect(Collectors.toList());
        for (ExamModel examModel : collect) {
            ExamUserModel examUserModel = examUserDao.queryByExamIdAndUserId(examModel.getId(), GetTokenInfoUtil.getUsername());
            if (examUserModel != null) {
                examModel.setExamUserStatus("Registered");
            }else {
                examModel.setExamUserStatus("NoRegistered");
            }
        }
        return collect;
    }
}
