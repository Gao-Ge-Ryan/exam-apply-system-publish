package com.exam.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (ExamUser)实体类
 *
 * @author makejava
 * @since 2021-11-10 19:44:05
 */
@Data
public class ExamUser implements Serializable {
    private static final long serialVersionUID = 574507876925790470L;

    private String id;
    /**
     * 考试id
     */
    private String examId;
    /**
     * 报名时间
     */
    private Long createTime;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 分数
     */
    private String score;

    /**
     * 考试人姓名
     */
    private String applyName;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 考场
     */
    private String examRoom;
    /**
     * 准考证号
     */
    private String examNumber;
    private String identificationPhoto;


}

