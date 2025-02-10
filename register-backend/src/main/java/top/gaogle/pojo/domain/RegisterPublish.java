package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.FormTemplateFlagEnum;
import top.gaogle.pojo.enums.RegisterPublishPublishStatusEnum;
import top.gaogle.pojo.enums.RegisterPublishStatusEnum;

import java.io.Serializable;

/**
 * (RegisterPublish)实体类
 *
 * @author makejava
 * @since 2024-07-06 10:46:21
 */
public class RegisterPublish implements Serializable {
    private static final long serialVersionUID = -44669047570663240L;

    private String id;

    private String title;

    private RegisterPublishPublishStatusEnum publishStatus;

    /**
     * 状态
     */
    private RegisterPublishStatusEnum status;

    private String description;

    private String enterpriseId;

    private FormTemplateFlagEnum templateFlag;

    private String templateCopy;

    private Long startAt;

    private Long endAt;

    private Boolean activityFlag;

    private Long activityStartAt;

    private Long activityEndAt;

    private Boolean ticketFlag;

    private Long ticketStartAt;

    private Long ticketEndAt;
    /**
     * 拼在准考证下方
     */
    private String ticketAttach;

    private Boolean payFlag;

    private Long cost;

    private Boolean scoreFlag;

    private Long scoreStartAt;

    private Long scoreEndAt;
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

    private Boolean delFlag;


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

    public RegisterPublishStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RegisterPublishStatusEnum status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public FormTemplateFlagEnum getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(FormTemplateFlagEnum templateFlag) {
        this.templateFlag = templateFlag;
    }

    public String getTemplateCopy() {
        return templateCopy;
    }

    public void setTemplateCopy(String templateCopy) {
        this.templateCopy = templateCopy;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public Boolean getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(Boolean activityFlag) {
        this.activityFlag = activityFlag;
    }

    public Long getActivityStartAt() {
        return activityStartAt;
    }

    public void setActivityStartAt(Long activityStartAt) {
        this.activityStartAt = activityStartAt;
    }

    public Long getActivityEndAt() {
        return activityEndAt;
    }

    public void setActivityEndAt(Long activityEndAt) {
        this.activityEndAt = activityEndAt;
    }

    public Boolean getTicketFlag() {
        return ticketFlag;
    }

    public void setTicketFlag(Boolean ticketFlag) {
        this.ticketFlag = ticketFlag;
    }

    public Long getTicketStartAt() {
        return ticketStartAt;
    }

    public void setTicketStartAt(Long ticketStartAt) {
        this.ticketStartAt = ticketStartAt;
    }

    public Long getTicketEndAt() {
        return ticketEndAt;
    }

    public void setTicketEndAt(Long ticketEndAt) {
        this.ticketEndAt = ticketEndAt;
    }

    public String getTicketAttach() {
        return ticketAttach;
    }

    public void setTicketAttach(String ticketAttach) {
        this.ticketAttach = ticketAttach;
    }

    public Boolean getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Boolean payFlag) {
        this.payFlag = payFlag;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }



    public Long getScoreStartAt() {
        return scoreStartAt;
    }

    public void setScoreStartAt(Long scoreStartAt) {
        this.scoreStartAt = scoreStartAt;
    }

    public Long getScoreEndAt() {
        return scoreEndAt;
    }

    public void setScoreEndAt(Long scoreEndAt) {
        this.scoreEndAt = scoreEndAt;
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

    public Boolean getScoreFlag() {
        return scoreFlag;
    }

    public void setScoreFlag(Boolean scoreFlag) {
        this.scoreFlag = scoreFlag;
    }

    public RegisterPublishPublishStatusEnum getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(RegisterPublishPublishStatusEnum publishStatus) {
        this.publishStatus = publishStatus;
    }
}

