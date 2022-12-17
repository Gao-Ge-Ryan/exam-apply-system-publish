package com.exam.dao;

import com.exam.pojo.entity.User;
import com.exam.pojo.model.UserModel;
import com.exam.pojo.param.UserParam;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (User)表数据库访问层
 *
 * @author gaoge
 * @since 2021-11-11 11:13:35
 */
@Repository
public interface UserDao extends BaseMapper<User> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserModel queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param user 查询条件
     * @return 对象列表
     */
    List<UserModel> queryAllByLimit(UserParam user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(UserParam user);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<User> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<User> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<User> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<User> entities);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int update(UserParam user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    UserModel selectByUserName(String userName);

    void patchInfo(UserParam user);


}

