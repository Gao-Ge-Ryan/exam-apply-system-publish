package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.model.SpotInfoModel;
import top.gaogle.pojo.param.SpotInfoEditParam;
import top.gaogle.pojo.param.SpotInfoQueryParam;

import java.util.List;

@Repository
public interface SpotInfoMapper {

    int insert(SpotInfoEditParam editParam);

    int putSpotInfo(SpotInfoEditParam editParam);

    List<SpotInfoModel> queryByPageAndCondition(SpotInfoQueryParam queryParam);

    int deleteById(String id);

    SpotInfoModel queryOneById(String id);

    List<SpotInfoModel> queryByEnableAndEnterpriseId(String enterpriseId);

    List<SpotInfoModel> queryByRegisterPublishId(String registerPublishId);
}
