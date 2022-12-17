package com.exam.pojo.model;

import com.exam.common.enums.EnumEntity;
import com.exam.pojo.entity.User;
import lombok.Data;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-11-11 11:13:35
 */
@Data
public class UserModel extends User {
    private static final long serialVersionUID = 247229862520241272L;

    /**
     * 角色：1普通用户，2管理员
     */
    private EnumEntity role;


}

