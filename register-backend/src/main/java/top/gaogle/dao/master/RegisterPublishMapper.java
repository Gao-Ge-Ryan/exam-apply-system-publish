package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.model.RegisterPublishModel;
import top.gaogle.pojo.param.RegisterPublishEditParam;
import top.gaogle.pojo.param.RegisterPublishQueryParam;

import java.util.List;

@Repository
public interface RegisterPublishMapper {

    int insert(RegisterPublishEditParam editParam);

    int update(RegisterPublishEditParam editParam);

    List<RegisterPublishModel> queryByPageAndCondition(RegisterPublishQueryParam queryParam);

    int deleteById(String id);

    RegisterPublishModel queryOneById(String id);

    RegisterPublishModel queryOneByIdAndEnterpriseId(String id, String enterpriseId);
}
