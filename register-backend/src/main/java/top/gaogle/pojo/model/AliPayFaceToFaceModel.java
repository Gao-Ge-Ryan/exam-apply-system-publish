package top.gaogle.pojo.model;


public class AliPayFaceToFaceModel {
    private String outTradeNo;
    private String subject;
    private String totalAmount;
    private String body;
    //分
    private Long numAmount;

    private String registerPublishId;

    /**
     * 备注
     */
    private String comment;

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getNumAmount() {
        return numAmount;
    }

    public void setNumAmount(Long numAmount) {
        this.numAmount = numAmount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRegisterPublishId() {
        return registerPublishId;
    }

    public void setRegisterPublishId(String registerPublishId) {
        this.registerPublishId = registerPublishId;
    }
}

