package com.exam.pojo.model;

import com.exam.common.enums.EnumEntity;
import com.exam.pojo.entity.Exam;
import lombok.Data;

/**
 * (Exam)实体类
 *
 * @author gaoge
 * @since 2021-11-10 19:32:14
 */
@Data
public class ExamModel extends Exam {
    private static final long serialVersionUID = -67783677645813997L;


    /**
     * 状态:0未开始，1报名中，2结束报名，
     */
    private EnumEntity status;

    /**
     * 类型
     */
    private EnumEntity examType;

    private String examUserStatus;




}

