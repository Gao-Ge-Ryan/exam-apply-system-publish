package top.gaogle.pojo.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class DynamicRegisterInfo {
    @ExcelIgnore
    private String registerPublishId;
    @ExcelIgnore
    private ObjectNode objectNode;

    @ExcelProperty(value = "编码")
    private String id;
    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名")
    private String name;
    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码")
    private String idNumber;
    /**
     * 准考证号
     */
    @ExcelProperty(value = "准考证号")
    private String admissionTicketNumber;
    /**
     * 照片
     */
    @ExcelProperty(value = "照片",converter = StringImageConverter.class)
    private String photo;
    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phoneNumber;
    /**
     * 邮箱
     */
    @ExcelProperty(value = "邮箱")
    private String email;
    /**
     * 考点
     */
    @ExcelProperty(value = "考点")
    private String spot;
    /**
     * 考点地址
     */
    @ExcelProperty(value = "考点地址")
    private String spotAddress;
    /**
     * 考场号
     */
    @ExcelProperty(value = "考场号")
    private String roomNumber;
    /**
     * 座号
     */
    @ExcelProperty(value = "座号")

    private String seatNumber;
    /**
     * 性别
     */
    @ExcelProperty(value = "性别")
    private String gender;
    /**
     * 学历
     */
    @ExcelProperty(value = "学历")
    private String education;
    /**
     * 专业
     */
    @ExcelProperty(value = "专业")
    private String major;
    /**
     * 成绩
     */
    @ExcelProperty(value = "成绩")
    private String score;
    /**
     * 状态:0初始化，1有效，2无效，3手动处理
     */
    @ExcelProperty(value = "状态:0待审核，1审核通过")
    private Integer status;
    /**
     * 审核状态:0待审核，1审核通过,2审核失败
     */
    @ExcelProperty(value = "状态:0待审核，1审核通过,2审核失败")
    private Integer approve;
    /**
     * 理由
     */
    @ExcelProperty(value = "理由")
    private String reason;
    /**
     * 创建者
     */
    @ExcelProperty(value = "创建者")
    private String createBy;
    /**
     * 创建时间
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty(value = "创建时间")
    private Long createAt;
    /**
     * 修改者
     */
    @ExcelProperty(value = "修改者")
    private String updateBy;
    /**
     * 修改时间
     */
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty(value = "修改时间")
    private Long updateAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAdmissionTicketNumber() {
        return admissionTicketNumber;
    }

    public void setAdmissionTicketNumber(String admissionTicketNumber) {
        this.admissionTicketNumber = admissionTicketNumber;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApprove() {
        return approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String getRegisterPublishId() {
        return registerPublishId;
    }

    public void setRegisterPublishId(String registerPublishId) {
        this.registerPublishId = registerPublishId;
    }

    public ObjectNode getObjectNode() {
        return objectNode;
    }

    public void setObjectNode(ObjectNode objectNode) {
        this.objectNode = objectNode;
    }
}
