package com.exam.dao;

import com.exam.pojo.entity.ExamUser;
import com.exam.pojo.model.ExamUserModel;
import com.exam.pojo.param.ExamUserParam;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (ExamUser)表数据库访问层
 *
 * @author gaoge
 * @since 2021-11-10 19:44:05
 */
@Repository
public interface ExamUserDao extends BaseMapper<ExamUser> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExamUserModel queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param examUser 查询条件
     * @return 对象列表
     */
    List<ExamUserModel> queryAllByLimit(ExamUserParam examUser);

    /**
     * 新增数据
     *
     * @param examUser 实例对象
     * @return 影响行数
     */
    int insert(ExamUserParam examUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ExamUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ExamUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ExamUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ExamUser> entities);

    /**
     * 修改数据
     *
     * @param examUser 实例对象
     * @return 影响行数
     */
    int update(ExamUserParam examUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    ExamUserModel queryByExamIdAndUserId(String examId, String userId);

    List<ExamUserModel> selectByExampleId(String examId);

    List<ExamUserModel> selectByUserId(String username);

    List<ExamUserModel> selectAll();
}

