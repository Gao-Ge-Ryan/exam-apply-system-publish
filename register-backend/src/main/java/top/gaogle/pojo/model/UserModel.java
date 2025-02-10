package top.gaogle.pojo.model;


import top.gaogle.pojo.domain.User;

import java.util.List;

public class UserModel extends User {

    List<RoleModel> roleModels;

    public List<RoleModel> getRoleModels() {
        return roleModels;
    }

    public void setRoleModels(List<RoleModel> roleModels) {
        this.roleModels = roleModels;
    }
}
