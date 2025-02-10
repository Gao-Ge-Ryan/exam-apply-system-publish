package top.gaogle.pojo.param;


import top.gaogle.pojo.enums.AuthorityEnum;
import top.gaogle.pojo.domain.Role;

import java.util.List;

public class RoleEditParam extends Role {
    private List<AuthorityEnum> authorityEnums;

    public List<AuthorityEnum> getUserAuthorityEnums() {
        return authorityEnums;
    }

    public void setUserAuthorityEnums(List<AuthorityEnum> authorityEnums) {
        this.authorityEnums = authorityEnums;
    }
}
