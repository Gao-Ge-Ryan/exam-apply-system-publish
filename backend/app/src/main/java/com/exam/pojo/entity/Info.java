package com.exam.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Info)实体类
 *
 * @author makejava
 * @since 2021-11-11 11:13:06
 */
@Data
public class Info implements Serializable {
    private static final long serialVersionUID = -29270330816255566L;

    private String id;
    /**
     * 信息标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    /**
     * 状态：0上架，1下架
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改时间
     */
    private Long updateTime;
    /**
     * 创建人id
     */
    private String userId;
    /**
     * 考试id
     */
    private String examId;

}

