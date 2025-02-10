package top.gaogle.framework.pojo;


import java.util.HashMap;

public class UserAuthorityEnumModel extends EnumModel {

    private Boolean authorized;

    public UserAuthorityEnumModel(IndexedEnum<String> enumName, String value, String title) {
        super(enumName, value, title);
    }

    public UserAuthorityEnumModel(IndexedEnum<String> enumName, String value, String title, Boolean authorized) {
        super(enumName, value, title);
        this.authorized = authorized;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }


    @Override
    public String toString() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("enumName", getEnumName());
        map.put("value", getValue());
        map.put("title", title());
        map.put("authorized", authorized);
        return map.toString();
    }
}
