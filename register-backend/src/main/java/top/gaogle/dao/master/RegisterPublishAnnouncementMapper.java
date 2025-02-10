package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.model.RegisterPublishAnnouncementModel;
import top.gaogle.pojo.param.RegisterPublishAnnouncementEditParam;
import top.gaogle.pojo.param.RegisterPublishAnnouncementQueryParam;

import java.util.List;

@Repository
public interface RegisterPublishAnnouncementMapper {

    int insert(RegisterPublishAnnouncementEditParam editParam);

    List<RegisterPublishAnnouncementModel> queryByPageAndCondition(RegisterPublishAnnouncementQueryParam queryParam);

    int update(RegisterPublishAnnouncementEditParam editParam);

    int deleteById(String id);

    int deleteByIdAndEnterpriseId(String id, String enterpriseId);

    RegisterPublishAnnouncementModel queryOneByIdAndEnterpriseId(String id, String enterpriseId);

    RegisterPublishAnnouncementModel queryOneById(String id);

    int updateByIdAndEnterpriseId(RegisterPublishAnnouncementEditParam editParam);
}
