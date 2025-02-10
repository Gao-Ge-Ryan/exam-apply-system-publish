package top.gaogle.pojo.param;


import top.gaogle.pojo.domain.User;

import java.util.List;

public class UserEditParam extends User {

    List<String> roleIds;

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
