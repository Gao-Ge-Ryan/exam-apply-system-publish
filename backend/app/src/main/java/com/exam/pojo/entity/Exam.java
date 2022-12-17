package com.exam.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Exam)实体类
 *
 * @author gaoge
 * @since 2021-11-10 19:32:14
 */
@Data
public class Exam implements Serializable {
    private static final long serialVersionUID = -67783677645813997L;

    private String id;
    /**
     * 考试名称
     */
    private String title;
    /**
     * 描述
     */
    private String description;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 更新时间
     */
    private Long updateTime;
    /**
     * 考试开始时间
     */
    private Long startTime;
    /**
     * 考试结束时间
     */
    private Long endTime;

    /**
     * 考试费用
     */
    private String price;
    /**
     * 报名开始时间
     */
    private Long applyStartTime;
    /**
     * 报名结束时间
     */
    private Long applyEndTime;
    /**
     * 注意事项
     */
    private String announcements;
    /**
     * 创建人
     */
    private String userId;





}

