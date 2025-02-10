package top.gaogle.dao.master;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.gaogle.pojo.entity.UserEntity;
import top.gaogle.pojo.model.UserModel;
import top.gaogle.pojo.param.UserQueryParam;

import java.util.List;

/**
 * User表数据库访问层
 *
 * @author goge
 * @since 1.0.0
 */
@Repository
public interface UserMapper {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserModel selectOneById(String id);

    /**
     * 条件查询数据列表
     *
     * @param user 查询条件
     * @return 对象列表
     */
    List<UserModel> selectMultiple(UserQueryParam user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int insertOne(UserEntity user);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<User> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<UserEntity> entities);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 影响行数
     */
    int patchOneById(UserEntity user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteOneById(String id);

    UserModel selectByUsername(String username);

    int selectExistByUsername(String username);
}
