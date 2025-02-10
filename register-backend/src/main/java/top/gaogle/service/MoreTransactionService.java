package top.gaogle.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import top.gaogle.dao.master.RegisterBillMapper;
import top.gaogle.dao.master.RegisterPublishMapper;
import top.gaogle.dao.slave.DynamicRegisterInfoMapper;
import top.gaogle.framework.annotation.MoreTransaction;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.UniqueUtil;
import top.gaogle.pojo.enums.BillStatusEnum;
import top.gaogle.pojo.enums.RegisterPublishPublishStatusEnum;
import top.gaogle.pojo.enums.RegisterPublishStatusEnum;
import top.gaogle.pojo.param.RegisterPublishEditParam;

import java.util.*;

import static top.gaogle.common.RegisterConst.REGISTER_INFO_TABLE_NAME;

@Service
public class MoreTransactionService extends SuperService {

    private final DynamicRegisterInfoMapper dynamicRegisterInfoMapper;
    private final RegisterPublishMapper registerPublishMapper;
    private final TransactionTemplate transactionTemplate;
    private final RegisterBillMapper registerBillMapper;

    @Autowired
    public MoreTransactionService(DynamicRegisterInfoMapper dynamicRegisterInfoMapper, RegisterPublishMapper registerPublishMapper, TransactionTemplate transactionTemplate, RegisterBillMapper registerBillMapper) {
        this.dynamicRegisterInfoMapper = dynamicRegisterInfoMapper;
        this.registerPublishMapper = registerPublishMapper;
        this.transactionTemplate = transactionTemplate;
        this.registerBillMapper = registerBillMapper;
    }

    @MoreTransaction(value = {"transactionManager", "slaveTransactionManager"})
    public boolean registerPublish(ObjectNode objectNode, RegisterPublishEditParam editParam) {
        String uniqueId = UniqueUtil.getUniqueId();
        editParam.setId(uniqueId);
        String tableName = REGISTER_INFO_TABLE_NAME + uniqueId;
        Iterator<Map.Entry<String, JsonNode>> iterator = objectNode.fields();
        List<Map<String, Object>> columns = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonNode> next = iterator.next();
            JsonNode jsonNode = next.getValue();
            Map<String, Object> column = new HashMap<>();
            column.put("name", jsonNode.get("key").asText());
            column.put("type", jsonNode.get("type").asText());
            column.put("comment", jsonNode.get("remark").asText());
            columns.add(column);
        }
        editParam.setStatus(RegisterPublishStatusEnum.NEW);
        editParam.setPublishStatus(RegisterPublishPublishStatusEnum.PUBLISHED);
        registerPublishMapper.insert(editParam);
        dynamicRegisterInfoMapper.createTableDynamic(tableName, columns);
        return true;
    }

    @MoreTransaction(value = {"transactionManager", "slaveTransactionManager"})
    public boolean updateClientRegisterBill(String billId, BillStatusEnum status, String tradeStatus, String tradeNo,
                                            Long completionAt, String registerPublishId, String createBy) {
        int billEffectRow = registerBillMapper.updateStatusByBillId(billId, BillStatusEnum.VALID,
                tradeStatus, tradeNo, DateUtil.currentTimeMillis());
        String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
        int statusEffectRow = dynamicRegisterInfoMapper.updateStatusByCreateBy(tableName, createBy, 1);
        log.info("账单{}-用户缴费更新完成影响行数billEffectRow:{},statusEffectRow:{}", billId, billEffectRow, statusEffectRow);
        return true;
    }

}
