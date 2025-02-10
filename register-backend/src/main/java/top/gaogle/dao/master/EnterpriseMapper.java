package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.model.EnterpriseModel;
import top.gaogle.pojo.param.EnterpriseEditParam;
import top.gaogle.pojo.param.EnterpriseQueryParam;

import java.util.List;

@Repository
public interface EnterpriseMapper {

    int insert(EnterpriseEditParam editParam);

    List<EnterpriseModel> queryByPageAndCondition(EnterpriseQueryParam queryParam);

    int putEnterprise(EnterpriseEditParam editParam);

    int deleteById(String id);

    EnterpriseModel queryOneById(String id);

    List<EnterpriseModel> queryAllByAndCondition(EnterpriseQueryParam queryParam);

    EnterpriseModel queryByCreateBy(String loginUsername);

    EnterpriseModel queryByAccountBy(String accountBy);

    List<EnterpriseModel> clientQueryByPage();

    int enterprisePutEnterprise(String id, String logo, String slideshow, String description);

    EnterpriseModel clientQueryEnterprise(String enterpriseId);

    long queryBalanceById(String enterpriseId);

    int updateBalanceById(String enterpriseId, long lastBalance);
}
