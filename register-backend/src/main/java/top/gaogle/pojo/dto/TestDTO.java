package top.gaogle.pojo.dto;

import top.gaogle.pojo.enums.BusinessTypeEnum;

import java.util.List;

public class TestDTO {

    private String name;
    private BusinessTypeEnum businessTypeEnum;
    private List<BusinessTypeEnum> businessTypeEnums;
    private BusinessTypeEnum[] businessTypesArrayEnum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BusinessTypeEnum getBusinessType() {
        return businessTypeEnum;
    }

    public void setBusinessType(BusinessTypeEnum businessTypeEnum) {
        this.businessTypeEnum = businessTypeEnum;
    }

    public List<BusinessTypeEnum> getBusinessTypes() {
        return businessTypeEnums;
    }

    public void setBusinessTypes(List<BusinessTypeEnum> businessTypeEnums) {
        this.businessTypeEnums = businessTypeEnums;
    }

    public BusinessTypeEnum[] getBusinessTypesArray() {
        return businessTypesArrayEnum;
    }

    public void setBusinessTypesArray(BusinessTypeEnum[] businessTypesArrayEnum) {
        this.businessTypesArrayEnum = businessTypesArrayEnum;
    }
}
