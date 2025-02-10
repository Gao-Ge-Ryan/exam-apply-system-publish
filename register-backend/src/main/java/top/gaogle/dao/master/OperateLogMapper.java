package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.domain.OperateLog;
import top.gaogle.pojo.param.OperateLogQueryParam;

import java.util.List;

@Repository
public interface OperateLogMapper {

    int insert(OperateLog operateLog);

    List<OperateLog> queryByPageAndCondition(OperateLogQueryParam queryParam);

}
