package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.BillStatusEnum;

import java.io.Serializable;

/**
 * (RegisterBill)实体类
 *
 * @author makejava
 * @since 2025-01-11 18:55:18
 */
public class RegisterBill implements Serializable {
    private static final long serialVersionUID = -54338882755429860L;
    
    private String id;
    /**
     * 注册发布id
     */
    private String registerPublishId;
    /**
     * 企业id
     */
    private String enterpriseId;
    /**
     * 状态：0等待判断，1生效，2等待删除
     */
    private BillStatusEnum status;
    /**
     * 阿里交易状态
     */
    private String alipayTradeStatus;
    /**
     * 支付宝交易号。支付宝交易凭证号。
     */
    private String alipayTradeNo;
    /**
     * 金额，单位分
     */
    private Long amount;
    /**
     * 阿里订单绝对超时时间。 格式为yyyy-MM-dd HH:mm:ss。
     */
    private String alipayTimeExpire;
    /**
     * 阿里订单绝对超时时间。 格式为时间戳。
     */
    private Long alipayTimeExpireAt;
    /**
     * 完成时间，生效时间
     */
    private Long completionAt;
    /**
     * 主题
     */
    private String subject;
    /**
     * 备注
     */
    private String comment;
    /**
     * 系统备注
     */
    private String systemComment;
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

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public BillStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BillStatusEnum status) {
        this.status = status;
    }

    public String getAlipayTradeStatus() {
        return alipayTradeStatus;
    }

    public void setAlipayTradeStatus(String alipayTradeStatus) {
        this.alipayTradeStatus = alipayTradeStatus;
    }

    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getAlipayTimeExpire() {
        return alipayTimeExpire;
    }

    public void setAlipayTimeExpire(String alipayTimeExpire) {
        this.alipayTimeExpire = alipayTimeExpire;
    }

    public Long getAlipayTimeExpireAt() {
        return alipayTimeExpireAt;
    }

    public void setAlipayTimeExpireAt(Long alipayTimeExpireAt) {
        this.alipayTimeExpireAt = alipayTimeExpireAt;
    }

    public Long getCompletionAt() {
        return completionAt;
    }

    public void setCompletionAt(Long completionAt) {
        this.completionAt = completionAt;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSystemComment() {
        return systemComment;
    }

    public void setSystemComment(String systemComment) {
        this.systemComment = systemComment;
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

