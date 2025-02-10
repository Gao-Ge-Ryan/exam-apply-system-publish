package top.gaogle.pojo.model;

import top.gaogle.pojo.enums.BusinessTypeEnum;
import top.gaogle.pojo.enums.CommentStatusEnum;

import java.util.List;

public class TestEnumModel {

    private String name;

    private List<BusinessTypeEnum> businessTypes;

    private CommentStatusEnum commentStatus;

    public List<BusinessTypeEnum> getBusinessTypes() {
        return businessTypes;
    }

    public void setBusinessTypes(List<BusinessTypeEnum> businessTypes) {
        this.businessTypes = businessTypes;
    }

    public CommentStatusEnum getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatusEnum commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
