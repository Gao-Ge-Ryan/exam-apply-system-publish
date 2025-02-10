package top.gaogle.framework.pojo;

import top.gaogle.pojo.enums.AuthorityEnum;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuthorityEnumModel extends EnumModel {

    private Long shift;
    private Boolean authorized = false;
    private Long authorityNum = 0L;
    private List<EnumModel> childEnums;

    public AuthorityEnumModel() {
    }

    public AuthorityEnumModel(IndexedEnum<String> enumName) {
        super(enumName);
        AuthorityEnum authorityEnum = (AuthorityEnum) enumName;
        super.setValue(authorityEnum.value());
        super.setTitle(authorityEnum.title());
        this.shift = authorityEnum.shift();
        this.childEnums = authorityEnum.children().stream()
                .map(child -> {
                    AuthorityEnumModel enumModel = new AuthorityEnumModel();
                    enumModel.setEnumName(child);
                    enumModel.setValue(child.value());
                    enumModel.setTitle(child.title());
                    enumModel.setShift(child.shift());
                    return enumModel;
                })
                .collect(Collectors.toList());
    }

    public AuthorityEnumModel(IndexedEnum<String> enumName, Long authorityNum) {
        super(enumName);
        AuthorityEnum authorityEnum = (AuthorityEnum) enumName;
        super.setValue(authorityEnum.value());
        super.setTitle(authorityEnum.title());
        this.shift = authorityEnum.shift();
        this.authorized = Objects.equals(this.shift, this.shift & authorityNum);
        this.authorityNum = authorityNum;
        this.childEnums = authorityEnum.children().stream()
                .map(child -> {
                    AuthorityEnumModel enumModel = new AuthorityEnumModel();
                    enumModel.setEnumName(child);
                    enumModel.setValue(child.value());
                    enumModel.setTitle(child.title());
                    enumModel.setShift(child.shift());
                    enumModel.setAuthorized(Objects.equals(child.shift(), child.shift() & authorityNum));
                    return enumModel;
                })
                .collect(Collectors.toList());
    }


    public List<EnumModel> getChildEnums() {
        return childEnums;
    }

    public void setChildEnums(List<EnumModel> childEnums) {
        this.childEnums = childEnums;
    }

    public Long getShift() {
        return shift;
    }

    public void setShift(Long shift) {
        this.shift = shift;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public Long getAuthorityNum() {
        return authorityNum;
    }

    public void setAuthorityNum(Long authorityNum) {
        this.authorityNum = authorityNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorityEnumModel)) return false;
        if (!super.equals(o)) return false;
        AuthorityEnumModel that = (AuthorityEnumModel) o;
        return Objects.equals(childEnums, that.childEnums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), childEnums);
    }
}
