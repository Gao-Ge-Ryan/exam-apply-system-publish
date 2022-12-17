package com.exam.service.impl;

import com.exam.common.Utils.DateUtil;
import com.exam.common.Utils.IDGenerator;
import com.exam.common.enums.impl.ExamStatusEnum;
import com.exam.common.enums.impl.ExamUserStatusEnum;
import com.exam.dao.ExamDao;
import com.exam.dao.ExamUserDao;
import com.exam.pojo.model.ExamModel;
import com.exam.pojo.model.ExamUserModel;
import com.exam.pojo.param.ExamUserParam;
import com.exam.security.util.GetTokenInfoUtil;
import com.exam.service.ExamUserService;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * (ExamUser)表服务实现类
 *
 * @author gaoge
 * @since 2021-11-10 19:44:05
 */
@Service("examUserService")
public class ExamUserServiceImpl implements ExamUserService {
    @Resource
    private ExamUserDao examUserDao;
    @Resource
    private ExamDao examDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ExamUserModel queryById(String id) {
        return this.examUserDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param examUser 筛选条件
     * @param pageNum  当前页数
     * @param pageSize 每页显示数量
     * @return 查询结果
     */
    @Override
    public PageInfo<ExamUserModel> queryByPage(ExamUserParam examUser, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ExamUserModel> examUsers = this.examUserDao.queryAllByLimit(examUser);
        for (ExamUserModel user : examUsers) {
            user.setExamModel(examDao.queryById(user.getExamId()));
        }
        return new PageInfo<>(examUsers);
    }

    /**
     * 新增数据
     *
     * @param examUser 实例对象
     * @return 实例对象
     */
    @Override
    public ExamUserParam insert(ExamUserParam examUser) {
        examUser.setId(IDGenerator.StringID());
        examUser.setCreateTime(DateUtil.getCurrentTimeMillis());
        String username = GetTokenInfoUtil.getUsername();
        examUser.setUserId(username);
        examUser.setExamRoom(IDGenerator.authCode());
        examUser.setExamNumber(IDGenerator.StringID());
        examUser.setStatus(ExamUserStatusEnum.Apply_NoPay);
        this.examUserDao.insert(examUser);
        return examUser;
    }

    /**
     * 修改数据
     *
     * @param examUser 实例对象
     * @return 实例对象
     */
    @Override
    public ExamUserModel update(ExamUserParam examUser) {
        this.examUserDao.update(examUser);
        return this.queryById(examUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.examUserDao.deleteById(id) > 0;
    }

    @Override
    public PageInfo<ExamUserModel> queryByPageAndUser(ExamUserParam examUser, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String username = GetTokenInfoUtil.getUsername();
        examUser.setUserId(username);
        List<ExamUserModel> examUsers = this.examUserDao.queryAllByLimit(examUser);
        for (ExamUserModel examUserModel : examUsers) {
            examUserModel.setExamModel(examDao.queryById(examUserModel.getExamId()));
        }
        return new PageInfo<>(examUsers);
    }

    @Override
    public PageInfo<ExamUserModel> queryByPageAndPrint(ExamUserParam examUser, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String username = GetTokenInfoUtil.getUsername();
        examUser.setUserId(username);
        List<ExamUserModel> examUsers = this.examUserDao.queryAllByLimit(examUser);
        for (ExamUserModel examUserModel : examUsers) {
            examUserModel.setExamModel(examDao.queryById(examUserModel.getExamId()));
        }
        List<ExamUserModel> examUserModels = examUsers.stream().filter(n -> n.getStatus().getEnumCode().equals(ExamUserStatusEnum.Apply_Pay) && n.getExamModel().getStatus().getEnumCode().equals(ExamStatusEnum.Stop)).collect(Collectors.toList());

        return new PageInfo<>(examUserModels);
    }

    @Override
    public List<ExamUserModel> queryScore() {
        String username = GetTokenInfoUtil.getUsername();
        List<ExamUserModel> examUserModels = examUserDao.selectByUserId(username);
        for (ExamUserModel examUserModel : examUserModels) {
            examUserModel.setExamModel(examDao.queryById(examUserModel.getExamId()));
        }
        List<ExamUserModel> collect = examUserModels.stream().filter(n -> n.getExamModel().getStatus().getEnumCode().equals(ExamStatusEnum.Score_Inquiry) && n.getStatus().getEnumCode().equals(ExamUserStatusEnum.Apply_Pay)).collect(Collectors.toList());

        return collect;

    }

    @Override
    public List<ExamModel> queryExamName() {
        List<ExamUserModel>examUserModels=examUserDao.selectAll();
        Set<String> examIDS = examUserModels.stream().map(ExamUserModel::getExamId).collect(Collectors.toSet());
        List<ExamModel> examModels = new ArrayList<>();
        for (String examID : examIDS) {
            ExamModel examModel = examDao.queryById(examID);
            examModels.add(examModel);
        }
        return examModels;
    }
}
