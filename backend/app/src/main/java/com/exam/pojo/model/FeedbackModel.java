package com.exam.pojo.model;

import com.exam.common.enums.EnumEntity;
import com.exam.pojo.entity.Feedback;
import lombok.Data;

/**
 * 问题反馈(Feeback)实体类
 *
 * @author makejava
 * @since 2021-11-11 11:12:37
 */
@Data
public class FeedbackModel extends Feedback {
    private static final long serialVersionUID = -30211505852000448L;


    /**
     * 类型：1bug反馈，2建议，3投诉
     */
    private EnumEntity type;

    /**
     * 状态：0未回复，1已回复
     */
    private EnumEntity status;




}

