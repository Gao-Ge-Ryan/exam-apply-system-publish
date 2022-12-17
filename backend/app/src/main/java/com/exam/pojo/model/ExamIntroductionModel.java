package com.exam.pojo.model;

import com.exam.common.enums.EnumEntity;
import com.exam.pojo.entity.ExamIntroduction;
import lombok.Data;

/**
 * (ExamIntroduction)实体类
 *
 * @author makejava
 * @since 2021-11-10 19:41:39
 */
@Data
public class ExamIntroductionModel extends ExamIntroduction {
    private static final long serialVersionUID = 993859514398744435L;


    /**
     * 类型
     */
    private EnumEntity examType;




}

