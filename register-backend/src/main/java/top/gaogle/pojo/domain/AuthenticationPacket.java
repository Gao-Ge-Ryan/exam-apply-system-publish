package top.gaogle.pojo.domain;

public class AuthenticationPacket {
    /**
     * 报文密文
     */
    private String cryptogram;
    /**
     * 公钥
     */
    private String key;

    private String email;

    private String password;

    public String getCryptogram() {
        return cryptogram;
    }

    public void setCryptogram(String cryptogram) {
        this.cryptogram = cryptogram;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
