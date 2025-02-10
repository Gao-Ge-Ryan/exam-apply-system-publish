package top.gaogle.dao.master;


import org.springframework.stereotype.Repository;
import top.gaogle.pojo.model.RoleModel;
import top.gaogle.pojo.param.RoleEditParam;
import top.gaogle.pojo.param.RoleQueryParam;

import java.util.List;

@Repository
public interface RoleMapper {

    List<RoleModel> getAllRoleList();

    int insert(RoleEditParam editParam);

    List<RoleModel> queryByPageAndCondition(RoleQueryParam queryParam);

    int patchRole(RoleEditParam editParam);

    Integer querySizeByName(String name);

    Integer querySizeExcludeSelfByName(String name,String id);

    int deleteRole(String roleId);

    List<RoleModel> queryAll();

    RoleModel queryDetailByRoleId(String roleId);

    List<RoleModel> queryRoleByUserId(String userId);

    List<RoleModel> queryRolesByUserIds(List<String> userIds);
}
