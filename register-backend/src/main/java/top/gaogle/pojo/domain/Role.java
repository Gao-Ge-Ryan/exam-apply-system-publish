package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.RoleTypeEnum;

import java.io.Serializable;

/**
 * (Role)实体类
 *
 * @author goge
 * @since 1.0.0
 */
public class Role implements Serializable {
    private static final long serialVersionUID = 276525491897252020L;

    private String id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色作用描述
     */
    private String description;

    /**
     * 角色类型: 1 系统角色 2企业角色
     */
    private RoleTypeEnum type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public RoleTypeEnum getType() {
        return type;
    }

    public void setType(RoleTypeEnum type) {
        this.type = type;
    }
}

