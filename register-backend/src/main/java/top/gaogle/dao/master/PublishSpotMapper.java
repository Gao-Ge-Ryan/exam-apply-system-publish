package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.param.PublishSpotEditParam;

@Repository
public interface PublishSpotMapper {

    int deleteByRegisterPublishId(String registerPublishId);

    int insert(PublishSpotEditParam editParam);

    int deleteByRegisterPublishIdAndSpotInfoId(String registerPublishId, String unbindSpotInfoId);
}
