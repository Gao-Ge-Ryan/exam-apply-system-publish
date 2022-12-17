package com.exam.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (ExamIntroduction)实体类
 *
 * @author makejava
 * @since 2021-11-10 19:41:39
 */
@Data
public class ExamIntroduction implements Serializable {
    private static final long serialVersionUID = 993859514398744435L;

    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 规则
     */
    private String rule;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改时间
     */
    private Long updateTime;
    /**
     * 创建人
     */
    private String userId;
    /**
     * 考试内容
     */
    private String examContent;
    /**
     * 等级
     */
    private String grade;
    /**
     * 证书
     */
    private String certificate;





}

