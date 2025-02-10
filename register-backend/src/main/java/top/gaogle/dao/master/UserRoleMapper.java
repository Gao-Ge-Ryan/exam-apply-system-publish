package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.param.UserRoleEditParam;

import java.util.List;

@Repository
public interface UserRoleMapper {

    List<String> queryRoleIdByUserId(String userId);

    int deleteByRoleId(String roleId);

    List<String> queryUserIdsByRoleId(String roleId);

    int deleteByUserId(String userId);

    int insert(UserRoleEditParam editParam);
}

