package com.exam.dao;

import com.exam.pojo.entity.Exam;
import com.exam.pojo.model.ExamModel;
import com.exam.pojo.param.ExamParam;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Exam)表数据库访问层
 *
 * @author gaoge
 * @since 2021-11-10 19:32:12
 */
@Repository
public interface ExamDao extends BaseMapper<Exam> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ExamModel queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param exam 查询条件
     * @return 对象列表
     */
    List<ExamModel> queryAllByLimit(ExamParam exam);

    /**
     * 新增数据
     *
     * @param exam 实例对象
     * @return 影响行数
     */
    int insert(ExamParam exam);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Exam> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Exam> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Exam> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Exam> entities);

    /**
     * 修改数据
     *
     * @param exam 实例对象
     * @return 影响行数
     */
    int update(ExamParam exam);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    List<ExamModel> queryAll();
}

