package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.enums.FormTemplateFlagEnum;
import top.gaogle.pojo.model.FormTemplateModel;
import top.gaogle.pojo.param.FormTemplateEditParam;
import top.gaogle.pojo.param.FormTemplateQueryParam;

import java.util.List;

@Repository
public interface FormTemplateMapper {

    int insert(FormTemplateEditParam editParam);

    int update(FormTemplateEditParam editParam);

    List<FormTemplateModel> queryByPageAndCondition(FormTemplateQueryParam queryParam);

    int deleteById(String id);

    FormTemplateModel queryOneById(String id);

    FormTemplateModel queryOneByFlag(FormTemplateFlagEnum flag);


}
