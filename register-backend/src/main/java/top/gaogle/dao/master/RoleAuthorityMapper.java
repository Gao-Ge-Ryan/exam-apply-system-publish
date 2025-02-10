package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.entity.RoleAuthorityEntity;
import top.gaogle.pojo.model.RoleAuthorityModel;

import java.util.List;

@Repository
public interface RoleAuthorityMapper {

    int insert(RoleAuthorityEntity entity);

    List<RoleAuthorityModel> queryByRoleIds(List<String> roleIds);

    List<RoleAuthorityModel> queryByRoleId(String roleId);

    int deleteByRoleId(String roleId);
}
