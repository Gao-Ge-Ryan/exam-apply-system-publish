package com.exam.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-11-11 11:13:35
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 247229862520241272L;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 身份证号
     */
    private String identityNum;
    /**
     * 居住地址
     */
    private String address;

    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 修改时间
     */
    private Long updateTime;
    /**
     * id
     */
    private String id;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 注销：逻辑删除
     */
    private Integer deleted;

}

