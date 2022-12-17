package com.exam.pojo.param;

import com.exam.common.enums.impl.ExamStatusEnum;
import com.exam.common.enums.impl.ExamTypeEnum;
import com.exam.pojo.entity.Exam;
import lombok.Data;

/**
 * (Exam)实体类
 *
 * @author gaoge
 * @since 2021-11-10 19:32:14
 */
@Data
public class ExamParam extends Exam {
    private static final long serialVersionUID = -67783677645813997L;


    /**
     * 状态:0未开始，1报名中，2结束报名，
     */
    private ExamStatusEnum status;

    /**
     * 类型
     */
    private ExamTypeEnum examType;




}

