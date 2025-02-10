package top.gaogle.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

    //网关名称
    private String openApiDomain;
    //app id
    private String appId;
    // pid
    private String pid;
    //私钥
    private String privateKey;
    //公钥
    private String publicKey;
    //SHA256withRsa对应支付宝公钥
    private String alipayPublicKey;
    //签名类型: RSA->SHA1withRsa,RSA2->SHA256withRsa
    private String signType;
    //异步回调地址 后端
    private String notifyUrl;
    //同步回调地址 响应前端界面
    private String returnUrl;
    //前端界面
    private String frontendUrl;

    //异步回调地址 后端
    private String enterpriseNotifyUrl;
    //同步回调地址 响应前端界面
    private String enterpriseReturnUrl;
    //前端界面
    private String enterpriseFrontendUrl;

    //异步回调地址 后端
    private String clientNotifyUrl;
    //同步回调地址 响应前端界面
    private String clientReturnUrl;
    //前端界面
    private String clientFrontendUrl;



    public String getOpenApiDomain() {
        return openApiDomain;
    }

    public void setOpenApiDomain(String openApiDomain) {
        this.openApiDomain = openApiDomain;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getFrontendUrl() {
        return frontendUrl;
    }

    public void setFrontendUrl(String frontendUrl) {
        this.frontendUrl = frontendUrl;
    }

    public String getEnterpriseNotifyUrl() {
        return enterpriseNotifyUrl;
    }

    public void setEnterpriseNotifyUrl(String enterpriseNotifyUrl) {
        this.enterpriseNotifyUrl = enterpriseNotifyUrl;
    }

    public String getEnterpriseReturnUrl() {
        return enterpriseReturnUrl;
    }

    public void setEnterpriseReturnUrl(String enterpriseReturnUrl) {
        this.enterpriseReturnUrl = enterpriseReturnUrl;
    }

    public String getEnterpriseFrontendUrl() {
        return enterpriseFrontendUrl;
    }

    public void setEnterpriseFrontendUrl(String enterpriseFrontendUrl) {
        this.enterpriseFrontendUrl = enterpriseFrontendUrl;
    }

    public String getClientNotifyUrl() {
        return clientNotifyUrl;
    }

    public void setClientNotifyUrl(String clientNotifyUrl) {
        this.clientNotifyUrl = clientNotifyUrl;
    }

    public String getClientReturnUrl() {
        return clientReturnUrl;
    }

    public void setClientReturnUrl(String clientReturnUrl) {
        this.clientReturnUrl = clientReturnUrl;
    }

    public String getClientFrontendUrl() {
        return clientFrontendUrl;
    }

    public void setClientFrontendUrl(String clientFrontendUrl) {
        this.clientFrontendUrl = clientFrontendUrl;
    }
}