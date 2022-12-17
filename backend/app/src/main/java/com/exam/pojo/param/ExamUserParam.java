package com.exam.pojo.param;

import com.exam.common.enums.impl.ExamUserStatusEnum;
import com.exam.pojo.entity.ExamUser;
import lombok.Data;

/**
 * (ExamUser)实体类
 *
 * @author makejava
 * @since 2021-11-10 19:44:05
 */
@Data
public class ExamUserParam extends ExamUser {
    private static final long serialVersionUID = 574507876925790470L;

    /**
     * 状态：0未报名，1已报名，未支付，2报名已支付，3取消报名
     */
    private ExamUserStatusEnum status;



}

