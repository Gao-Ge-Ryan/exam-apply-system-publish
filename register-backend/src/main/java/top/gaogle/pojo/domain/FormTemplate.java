package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.FormTemplateFlagEnum;
import top.gaogle.pojo.enums.FormTemplateRangeEnum;
import top.gaogle.pojo.enums.FormTemplateStatusEnum;

import java.io.Serializable;

/**
 * (FormTemplate)实体类
 *
 * @author makejava
 * @since 2024-06-29 13:29:57
 */
public class FormTemplate implements Serializable {
    private static final long serialVersionUID = 747868265142972457L;
    
    private String id;
    
    private String title;
    
    private String description;
    
    private String content;
    
    private String enterpriseId;
    /**
     * 范围
     */
    private FormTemplateRangeEnum range;
    /**
     * 状态
     */
    private FormTemplateStatusEnum status;

    /**
     * 标志
     */
    private FormTemplateFlagEnum flag;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public FormTemplateRangeEnum getRange() {
        return range;
    }

    public void setRange(FormTemplateRangeEnum range) {
        this.range = range;
    }

    public FormTemplateStatusEnum getStatus() {
        return status;
    }

    public void setStatus(FormTemplateStatusEnum status) {
        this.status = status;
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

    public FormTemplateFlagEnum getFlag() {
        return flag;
    }

    public void setFlag(FormTemplateFlagEnum flag) {
        this.flag = flag;
    }
}

