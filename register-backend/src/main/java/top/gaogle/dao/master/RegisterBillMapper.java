package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.enums.BillStatusEnum;
import top.gaogle.pojo.model.RegisterBillModel;
import top.gaogle.pojo.param.RegisterBillEditParam;

@Repository
public interface RegisterBillMapper {

    int insert(RegisterBillEditParam editParam);

    RegisterBillModel queryOneById(String id);

    int updateStatusByBillId(String id, BillStatusEnum status, String tradeStatus, String tradeNo, Long completionAt);

}
