package com.exam.dao;

import com.exam.pojo.entity.Feedback;
import com.exam.pojo.model.FeedbackModel;
import com.exam.pojo.param.FeedbackParam;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 问题反馈(Feeback)表数据库访问层
 *
 * @author gaoge
 * @since 2021-11-11 11:12:37
 */
@Repository
public interface FeedbackDao extends BaseMapper<Feedback> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    FeedbackModel queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param feedback 查询条件
     * @return 对象列表
     */
    List<FeedbackModel> queryAllByLimit(FeedbackParam feedback);

    /**
     * 新增数据
     *
     * @param feedback 实例对象
     * @return 影响行数
     */
    int insert(FeedbackParam feedback);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Feeback> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Feedback> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Feeback> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Feedback> entities);

    /**
     * 修改数据
     *
     * @param feedback 实例对象
     * @return 影响行数
     */
    int update(FeedbackParam feedback);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

}

