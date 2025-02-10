package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.enums.BillStatusEnum;
import top.gaogle.pojo.model.EnterpriseBillModel;
import top.gaogle.pojo.param.EnterpriseBillEditParam;
import top.gaogle.pojo.param.EnterpriseBillQueryParam;

import java.util.List;

@Repository
public interface EnterpriseBillMapper {

    int insert(EnterpriseBillEditParam editParam);

    EnterpriseBillModel queryOneById(String id);

    int updateStatusById(String id, BillStatusEnum status, String tradeStatus, String tradeNo, Long completionAt, Long balance);

    int updateBalanceAndStatusByBillId(String enterpriseBillId, Long amount, BillStatusEnum status, String tradeStatus, String tradeNo, Long completionAt);

    List<EnterpriseBillModel> enterpriseQueryByPage(EnterpriseBillQueryParam queryParam);
}
