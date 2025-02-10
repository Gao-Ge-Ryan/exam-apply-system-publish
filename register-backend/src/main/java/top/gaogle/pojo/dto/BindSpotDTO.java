package top.gaogle.pojo.dto;

import java.util.List;

public class BindSpotDTO {

    private String registerPublishId;

    private List<String> spotInfoIds;

    private String unbindSpotInfoId;


    public String getRegisterPublishId() {
        return registerPublishId;
    }

    public void setRegisterPublishId(String registerPublishId) {
        this.registerPublishId = registerPublishId;
    }

    public List<String> getSpotInfoIds() {
        return spotInfoIds;
    }

    public void setSpotInfoIds(List<String> spotInfoIds) {
        this.spotInfoIds = spotInfoIds;
    }

    public String getUnbindSpotInfoId() {
        return unbindSpotInfoId;
    }

    public void setUnbindSpotInfoId(String unbindSpotInfoId) {
        this.unbindSpotInfoId = unbindSpotInfoId;
    }
}
