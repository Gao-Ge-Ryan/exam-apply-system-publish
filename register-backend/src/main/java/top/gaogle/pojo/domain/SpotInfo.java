package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.SpotInfoStatusEnum;

import java.io.Serializable;

/**
 * (SpotInfo)实体类
 *
 * @author makejava
 * @since 2024-10-12 15:59:44
 */
public class SpotInfo implements Serializable {
    private static final long serialVersionUID = -43629897580934256L;
    
    private String id;
    /**
     * 考点
     */
    private String spot;
    /**
     * 考点地址
     */
    private String spotAddress;
    /**
     * 房间数量
     */
    private Integer roomQuantity;
    /**
     * 每个房间座位数量
     */
    private Integer seatQuantity;
    /**
     * 企业id
     */
    private String enterpriseId;
    /**
     * 状态:0未启用，1启用
     */
    private SpotInfoStatusEnum status;
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

    public String getSpot() {
        return spot;
    }

    public void setSpot(String spot) {
        this.spot = spot;
    }

    public String getSpotAddress() {
        return spotAddress;
    }

    public void setSpotAddress(String spotAddress) {
        this.spotAddress = spotAddress;
    }

    public Integer getRoomQuantity() {
        return roomQuantity;
    }

    public void setRoomQuantity(Integer roomQuantity) {
        this.roomQuantity = roomQuantity;
    }

    public Integer getSeatQuantity() {
        return seatQuantity;
    }

    public void setSeatQuantity(Integer seatQuantity) {
        this.seatQuantity = seatQuantity;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public SpotInfoStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SpotInfoStatusEnum status) {
        this.status = status;
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

