package top.gaogle.pojo.domain;


import top.gaogle.framework.pojo.SuperDomain;

import java.io.Serializable;

/**
 * (User)实体类
 *
 * @author goge
 * @since 1.0.0
 */
public class User extends SuperDomain implements Serializable {
    private static final long serialVersionUID = 46166004386824706L;

    private String id;
    /**
     * 用户名（邮箱格式）
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * null为永久有效
     */
    private Long expireAt;
    /**
     * 锁定期间不可使用
     */
    private Long lockAt;
    /**
     * 是否禁用：0正常，1禁用
     */
    private Boolean disabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }

    public Long getLockAt() {
        return lockAt;
    }

    public void setLockAt(Long lockAt) {
        this.lockAt = lockAt;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", expireAt=" + expireAt +
                ", lockAt=" + lockAt +
                ", disabled=" + disabled +
                '}';
    }
}

