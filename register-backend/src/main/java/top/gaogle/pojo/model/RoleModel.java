package top.gaogle.pojo.model;


import top.gaogle.pojo.domain.Role;

public class RoleModel extends Role {
    private Boolean authorized = false;
    private String userId;

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
