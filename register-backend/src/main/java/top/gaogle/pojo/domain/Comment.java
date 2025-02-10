package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.CommentStatusEnum;

import java.io.Serializable;

/**
 * (Comment)实体类
 *
 * @author makejava
 * @since 2024-06-25 22:21:35
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = -31285515459903828L;

    private String id;

    private String enterpriseId;

    private String parentId;

    private String rootId;

    private String title;

    private CommentStatusEnum status;

    private String content;

    private String createBy;

    private String replyBy;

    private Long createAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CommentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CommentStatusEnum status) {
        this.status = status;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getReplyBy() {
        return replyBy;
    }

    public void setReplyBy(String replyBy) {
        this.replyBy = replyBy;
    }
}

