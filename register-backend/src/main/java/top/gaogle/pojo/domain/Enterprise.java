package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.EnterpriseStatusEnum;

import java.io.Serializable;

/**
 * (Enterprise)实体类
 *
 * @author makejava
 * @since 2024-06-28 15:38:36
 */
public class Enterprise implements Serializable {
    private static final long serialVersionUID = -56828590399627636L;

    private String id;

    private String name;

    private String logo;

    private String reason;
    /**
     * 状态
     */
    private EnterpriseStatusEnum status;

    private String description;
    /**
     * 信用代码
     */
    private String creditCode;
    /**
     * 余额，单位分
     */
    private Long balance;
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
     * 轮播图
     */
    private String slideshow;

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

    public EnterpriseStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EnterpriseStatusEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSlideshow() {
        return slideshow;
    }

    public void setSlideshow(String slideshow) {
        this.slideshow = slideshow;
    }
}

