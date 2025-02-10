package top.gaogle.pojo.dto;

import top.gaogle.pojo.enums.AuthorityEnum;

import java.util.List;

public class RoleAuthorityDTO {
    private String roleId;
    private List<AuthorityEnum> authorityEnums;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<AuthorityEnum> getAuthorityEnums() {
        return authorityEnums;
    }

    public void setAuthorityEnums(List<AuthorityEnum> authorityEnums) {
        this.authorityEnums = authorityEnums;
    }
}
