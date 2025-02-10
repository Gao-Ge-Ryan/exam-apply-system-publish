package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.BillStatusEnum;
import top.gaogle.pojo.enums.EnterpriseBillTypeEnum;

import java.io.Serializable;

/**
 * (EnterpriseBill)实体类
 *
 * @author makejava
 * @since 2025-01-06 10:16:41
 */
public class EnterpriseBill implements Serializable {
    private static final long serialVersionUID = -30264846952340732L;
    
    private String id;
    /**
     * 企业id
     */
    private String enterpriseId;
    /**
     * 余额，单位分
     */
    private Long balance;
    /**
     * 收支类型
     */
    private EnterpriseBillTypeEnum type;
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

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public EnterpriseBillTypeEnum getType() {
        return type;
    }

    public void setType(EnterpriseBillTypeEnum type) {
        this.type = type;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSystemComment() {
        return systemComment;
    }

    public void setSystemComment(String systemComment) {
        this.systemComment = systemComment;
    }

    public String getAlipayTradeNo() {
        return alipayTradeNo;
    }

    public void setAlipayTradeNo(String alipayTradeNo) {
        this.alipayTradeNo = alipayTradeNo;
    }
}

