package top.gaogle.framework.config;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
public class MinioConfig {

    /**
     * 是一个URL，域名，IPv4或者IPv6地址")
     */
    private String endpoint;

    /**
     * TCP/IP端口号
     */
    private Integer port;

    /**
     * accessKey类似于用户ID，用于唯一标识你的账户
     */
    private String accessKey;

    /**
     * secretKey是你账户的密码
     */
    private String secretKey;

    /**
     * /如果是true，则用的是https而不是http,默认值是true
     */
    private boolean secure;

    /**
     * 公开存储桶
     */
    private String bucketNamePublic;

    /**
     * 私有存储桶
     */
    private String bucketNamePrivate;

    /**
     * 图片的最大大小
     */
    private long imageSize;

    /**
     * 其他文件的最大大小
     */
    private long fileSize;


    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .credentials(accessKey, secretKey)
                .endpoint(endpoint, port, secure)
                .build();
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getBucketNamePublic() {
        return bucketNamePublic;
    }

    public void setBucketNamePublic(String bucketNamePublic) {
        this.bucketNamePublic = bucketNamePublic;
    }

    public String getBucketNamePrivate() {
        return bucketNamePrivate;
    }

    public void setBucketNamePrivate(String bucketNamePrivate) {
        this.bucketNamePrivate = bucketNamePrivate;
    }

    public long getImageSize() {
        return imageSize;
    }

    public void setImageSize(long imageSize) {
        this.imageSize = imageSize;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}