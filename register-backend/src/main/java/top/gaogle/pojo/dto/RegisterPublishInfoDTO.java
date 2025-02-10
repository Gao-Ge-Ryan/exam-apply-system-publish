package top.gaogle.pojo.dto;

import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.enums.FormTemplateFlagEnum;
import top.gaogle.pojo.model.RegisterPublishModel;

import java.util.Map;

public class RegisterPublishInfoDTO {

    private Map<String, Object> infoModel;
    private RegisterPublishModel registerPublishModel;
    private PageModel<Map<String, Object>> pageInfoModel;

    private FormTemplateFlagEnum templateFlag;

    public Map<String, Object> getInfoModel() {
        return infoModel;
    }

    public void setInfoModel(Map<String, Object> infoModel) {
        this.infoModel = infoModel;
    }

    public RegisterPublishModel getRegisterPublishModel() {
        return registerPublishModel;
    }

    public void setRegisterPublishModel(RegisterPublishModel registerPublishModel) {
        this.registerPublishModel = registerPublishModel;
    }

    public PageModel<Map<String, Object>> getPageInfoModel() {
        return pageInfoModel;
    }

    public void setPageInfoModel(PageModel<Map<String, Object>> pageInfoModel) {
        this.pageInfoModel = pageInfoModel;
    }

    public FormTemplateFlagEnum getTemplateFlag() {
        return templateFlag;
    }

    public void setTemplateFlag(FormTemplateFlagEnum templateFlag) {
        this.templateFlag = templateFlag;
    }
}
