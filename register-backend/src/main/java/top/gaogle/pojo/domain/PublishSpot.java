package top.gaogle.pojo.domain;

import java.io.Serializable;

/**
 * (PublishSpot)实体类
 *
 * @author makejava
 * @since 2024-10-26 13:46:35
 */
public class PublishSpot implements Serializable {
    private static final long serialVersionUID = -52019141094284483L;
    
    private String id;
    
    private String registerPublishId;
    
    private String spotInfoId;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Long createAt;
    /**
     * 修改者
     */
    private String updateBy;
    /**
     * 修改时间
     */
    private Long updateAt;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegisterPublishId() {
        return registerPublishId;
    }

    public void setRegisterPublishId(String registerPublishId) {
        this.registerPublishId = registerPublishId;
    }

    public String getSpotInfoId() {
        return spotInfoId;
    }

    public void setSpotInfoId(String spotInfoId) {
        this.spotInfoId = spotInfoId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

}

