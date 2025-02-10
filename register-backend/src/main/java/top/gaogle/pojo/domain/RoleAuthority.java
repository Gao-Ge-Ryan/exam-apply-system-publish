package top.gaogle.pojo.domain;

import java.io.Serializable;

/**
 * (RoleAuthority)实体类
 *
 * @author goge
 * @since 1.0.0
 */
public class RoleAuthority implements Serializable {
    private static final long serialVersionUID = -36387806658834121L;

    private String id;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 权限模块
     */
    private String authorityModule;
    /**
     * 授权合集数
     */
    private Long authorityNum;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Long createAt;
    /**
     * 修改者
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Long updateAt;
    /**
     * 是否删除：0正常，1删除
     */
    private Boolean delFlag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAuthorityModule() {
        return authorityModule;
    }

    public void setAuthorityModule(String authorityModule) {
        this.authorityModule = authorityModule;
    }

    public Long getAuthorityNum() {
        return authorityNum;
    }

    public void setAuthorityNum(Long authorityNum) {
        this.authorityNum = authorityNum;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

}

