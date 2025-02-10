package top.gaogle.pojo.domain;

import top.gaogle.pojo.enums.BusinessStatusEnum;
import top.gaogle.pojo.enums.BusinessTypeEnum;
import top.gaogle.pojo.enums.OperatorTypeEnum;

public class OperateLog {
    /**
     * 日志主键
     */
    private String id;

    /**
     * 操作模块
     */

    private String title;

    /**
     * 业务类型
     */

    private BusinessTypeEnum businessType;


    /**
     * 请求方法
     */

    private String method;

    /**
     * 请求方式
     */

    private String requestMethod;

    /**
     * 操作类别
     */

    private OperatorTypeEnum operateType;

    /**
     * 操作人员
     */

    private String operator;

    /**
     * 企业id
     */
    private String enterpriseId;

    /**
     * 请求url
     */
    private String operateUrl;

    /**
     * 操作地址
     */
    private String operateIp;

    /**
     * 操作地点
     */
    private String operateLocation;

    /**
     * 请求参数
     */
    private String operateParam;

    /**
     * 返回参数
     */
    private String jsonResult;

    /**
     * 操作状态（0正常 1异常）
     */
    private BusinessStatusEnum status;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 操作时间
     */
    private Long createAt;

    /**
     * 消耗时间
     */
    private Long costTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getOperateUrl() {
        return operateUrl;
    }

    public void setOperateUrl(String operateUrl) {
        this.operateUrl = operateUrl;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public String getOperateLocation() {
        return operateLocation;
    }

    public void setOperateLocation(String operateLocation) {
        this.operateLocation = operateLocation;
    }

    public String getOperateParam() {
        return operateParam;
    }

    public void setOperateParam(String operateParam) {
        this.operateParam = operateParam;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public BusinessTypeEnum getBusinessType() {
        return businessType;
    }

    public void setBusinessType(BusinessTypeEnum businessType) {
        this.businessType = businessType;
    }

    public OperatorTypeEnum getOperateType() {
        return operateType;
    }

    public void setOperateType(OperatorTypeEnum operateType) {
        this.operateType = operateType;
    }

    public BusinessStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BusinessStatusEnum status) {
        this.status = status;
    }
}
