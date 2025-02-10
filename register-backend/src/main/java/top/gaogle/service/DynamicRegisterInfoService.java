package top.gaogle.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.page.PageMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.gaogle.dao.master.RegisterPublishMapper;
import top.gaogle.dao.slave.DynamicRegisterInfoMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.*;
import top.gaogle.pojo.enums.HttpStatusEnum;
import top.gaogle.pojo.model.RegisterPublishModel;
import top.gaogle.pojo.param.DynamicRegisterInfoEditParam;
import top.gaogle.pojo.param.DynamicRegisterInfoQueryParam;

import java.util.*;

import static top.gaogle.common.RegisterConst.*;

@Service
public class DynamicRegisterInfoService extends SuperService {
    private final DynamicRegisterInfoMapper dynamicRegisterInfoMapper;
    private final RegisterPublishMapper registerPublishMapper;

    @Autowired
    public DynamicRegisterInfoService(DynamicRegisterInfoMapper dynamicRegisterInfoMapper, RegisterPublishMapper registerPublishMapper) {
        this.dynamicRegisterInfoMapper = dynamicRegisterInfoMapper;
        this.registerPublishMapper = registerPublishMapper;
    }

    public I18nResult<Boolean> clientApplyInfo(DynamicRegisterInfoEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String registerPublishId = editParam.getRegisterPublishId();
            ObjectNode objectNode = editParam.getObjectNode();
            String name = editParam.getName();
            String phoneNumber = editParam.getPhoneNumber();
            if (StringUtils.isAnyEmpty(registerPublishId) || objectNode == null || objectNode.size() < 0) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺失必要参数");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(registerPublishId);
            if (registerPublishModel == null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("未查询到该场次");
            }
            String templateCopy = registerPublishModel.getTemplateCopy();
            ObjectNode templateCopyNode = JsonUtil.parseObjectNode(templateCopy);
            Iterator<Map.Entry<String, JsonNode>> iterator = templateCopyNode.fields();
            String loginUsername = SecurityUtil.getLoginUsername();
            Long timeMillis = DateUtil.currentTimeMillis();
            List<String> columns = new ArrayList<>();
            columns.add(ID);
            columns.add(COLUMN_STATUS);
            columns.add(COLUMN_CREATE_BY);
            columns.add(COLUMN_CREATE_AT);
            columns.add(COLUMN_UPDATE_BY);
            columns.add(COLUMN_UPDATE_AT);
            columns.add(COLUMN_NAME);
            columns.add(COLUMN_PHONE_NUMBER);
            List<Object> values = new ArrayList<>();
            values.add(UniqueUtil.getUniqueId());
            values.add(0);
            values.add(loginUsername);
            values.add(timeMillis);
            values.add(loginUsername);
            values.add(timeMillis);
            values.add(name);
            values.add(phoneNumber);
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> next = iterator.next();
                String key = next.getKey();
                JsonNode jsonNode = next.getValue();
                String type = jsonNode.get(TEMPLATE_TYPE).asText();
                String column = jsonNode.get(TEMPLATE_KEY).asText();
                String remark = jsonNode.get(TEMPLATE_REMARK).asText();
                boolean required = jsonNode.get(TEMPLATE_RULE).get(TEMPLATE_REQUIRED).asBoolean();
                String regex = jsonNode.get(TEMPLATE_RULE).get(TEMPLATE_REGEX).asText(null);
                Object columnValue = null;
                if (type.contains(TEMPLATE_VARCHAR)) {
                    String tmpValue = objectNode.path(key).asText(null);
                    if (required && StringUtils.isEmpty(tmpValue)) {
                        return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(remark + "值缺失");
                    }
                    columnValue = tmpValue;
                }
                if (StringUtils.isNotEmpty(regex) && !StringUtil.regexMatches(columnValue, regex)) {
                    return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(remark + "请输入正确格式");
                }
                columns.add(column);
                values.add(columnValue);
            }
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            dynamicRegisterInfoMapper.insertDynamic(tableName, columns, values);

        } catch (Exception e) {
            log.error("客户端报名申请发生异常:", e);
            result.failed().setMessage("客户端报名申请发生异常");
        }
        return result;
    }


    public I18nResult<Map<String, Object>> clientGetApplyInfo(DynamicRegisterInfoQueryParam queryParam) {
        I18nResult<Map<String, Object>> result = I18nResult.newInstance();
        try {
            String registerPublishId = queryParam.getRegisterPublishId();
            if (StringUtils.isAnyEmpty(registerPublishId)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺少必要参数");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(registerPublishId);
            if (registerPublishModel == null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("未查询到该场次");
            }
            String loginUsername = SecurityUtil.getLoginUsername();
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            List<String> fields = new ArrayList<>();
            fields.add(ID);
            List<Map<String, Object>> conditions = new ArrayList<>();
            String templateCopy = registerPublishModel.getTemplateCopy();
            ObjectNode templateCopyNode = JsonUtil.parseObjectNode(templateCopy);
            Iterator<Map.Entry<String, JsonNode>> iterator = templateCopyNode.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> next = iterator.next();
                String key = next.getKey();
                JsonNode jsonNode = next.getValue();
                String column = jsonNode.get(TEMPLATE_KEY).asText();
                fields.add(column + AS + key);
            }
            Map<String, Object> condition = new HashMap<>();
            condition.put(KEY, COLUMN_CREATE_BY);
            condition.put(VALUE, loginUsername);
            condition.put(MATCH_TYPE, MATCH_TYPE_EXACT); // 使用模糊匹配
            conditions.add(condition);
            List<Map<String, Object>> objectNodes = dynamicRegisterInfoMapper.selectDynamic(tableName, fields, conditions);
            if (CollectionUtils.isEmpty(objectNodes)) {
                result.succeed().setData(null);
            } else {
                Map<String, Object> objectMap = objectNodes.get(0);
                objectMap.put("templateFlag",registerPublishModel.getTemplateFlag());
                result.succeed().setData(objectMap);
            }
        } catch (Exception e) {
            log.error("查询个人报名发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询个人报名发生异常");
        }
        return result;
    }

    public I18nResult<PageModel<Map<String, Object>>> queryByPageAndCondition(DynamicRegisterInfoQueryParam queryParam) {
        I18nResult<PageModel<Map<String, Object>>> result = I18nResult.newInstance();
        try {
            String registerPublishId = queryParam.getRegisterPublishId();
            Integer approve = queryParam.getApprove();
            String enterpriseId = SecurityUtil.getEnterpriseId();
            if (StringUtils.isAnyEmpty(registerPublishId)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺少必要参数");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneByIdAndEnterpriseId(registerPublishId, enterpriseId);
            if (registerPublishModel == null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("未查询到该场次");
            }
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            List<String> fields = new ArrayList<>();
            fields.add(ID);
            List<Map<String, Object>> conditions = new ArrayList<>();
            String templateCopy = registerPublishModel.getTemplateCopy();
            ObjectNode templateCopyNode = JsonUtil.parseObjectNode(templateCopy);
            Iterator<Map.Entry<String, JsonNode>> iterator = templateCopyNode.fields();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonNode> next = iterator.next();
                String key = next.getKey();
                JsonNode jsonNode = next.getValue();
                String column = jsonNode.get(KEY).asText();
                fields.add(column + AS + key);
            }
            fields.addAll(DYNAMIC_FIELD);
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            if (approve != null) {
                Map<String, Object> condition = new HashMap<>();
                condition.put(KEY, COLUMN_APPROVE);
                condition.put(VALUE, approve);
                condition.put(MATCH_TYPE, MATCH_TYPE_EXACT); // 使用匹配
                conditions.add(condition);
            }
            List<Map<String, Object>> objectNodes = dynamicRegisterInfoMapper.selectDynamicByQueryParam(tableName, fields, conditions,queryParam);
            for (Map<String, Object> objectNode : objectNodes) {
                objectNode.put("templateFlag",registerPublishModel.getTemplateFlag());
            }
            PageModel<Map<String, Object>> pageModel = new PageModel<>(objectNodes);
            result.succeed().setData(pageModel);
        } catch (Exception e) {
            log.error("查询个人报名发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询个人报名发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> approve(DynamicRegisterInfoEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String registerPublishId = editParam.getRegisterPublishId();
            String id = editParam.getId();
            String enterpriseId = SecurityUtil.getEnterpriseId();
            if (StringUtils.isAnyEmpty(registerPublishId, id)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺少必要参数");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneByIdAndEnterpriseId(registerPublishId, enterpriseId);
            if (registerPublishModel == null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("未查询到该场次");
            }
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            List<Map<String, Object>> setClauses = new ArrayList<>();
            Map<String, Object> clause = new HashMap<>();
            clause.put(KEY, COLUMN_APPROVE);
            clause.put(VALUE, 1);
            setClauses.add(clause);
            List<Map<String, Object>> conditions = new ArrayList<>();
            HashMap<String, Object> condition = new HashMap<>();
            condition.put(KEY, COLUMN_ID);
            condition.put(VALUE, id);
            conditions.add(condition);
            dynamicRegisterInfoMapper.updateDynamic(tableName, setClauses, conditions);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("审批发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "审批发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> approveNot(DynamicRegisterInfoEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String registerPublishId = editParam.getRegisterPublishId();
            String id = editParam.getId();
            String reason = editParam.getReason();
            String enterpriseId = SecurityUtil.getEnterpriseId();
            if (StringUtils.isAnyEmpty(registerPublishId, id, reason)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺少必要参数");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneByIdAndEnterpriseId(registerPublishId, enterpriseId);
            if (registerPublishModel == null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("未查询到该场次");
            }
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            List<Map<String, Object>> setClauses = new ArrayList<>();
            Map<String, Object> statusClause = new HashMap<>();
            statusClause.put(KEY, COLUMN_APPROVE);
            statusClause.put(VALUE, 2);
            setClauses.add(statusClause);
            Map<String, Object> reasonClause = new HashMap<>();
            reasonClause.put(KEY, COLUMN_REASON);
            reasonClause.put(VALUE, reason);
            setClauses.add(reasonClause);
            List<Map<String, Object>> conditions = new ArrayList<>();
            HashMap<String, Object> condition = new HashMap<>();
            condition.put(KEY, COLUMN_ID);
            condition.put(VALUE, id);
            conditions.add(condition);
            dynamicRegisterInfoMapper.updateDynamic(tableName, setClauses, conditions);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("审批发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "审批发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> inputScore(DynamicRegisterInfoEditParam editParam) {

        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String registerPublishId = editParam.getRegisterPublishId();
            String id = editParam.getId();
            String score = editParam.getScore();
            String enterpriseId = SecurityUtil.getEnterpriseId();
            if (StringUtils.isAnyEmpty(registerPublishId, id, score)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺少必要参数");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneByIdAndEnterpriseId(registerPublishId, enterpriseId);
            if (registerPublishModel == null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("未查询到该场次");
            }
            String tableName = REGISTER_INFO_TABLE_NAME + registerPublishId;
            List<Map<String, Object>> setClauses = new ArrayList<>();
            Map<String, Object> scoreClause = new HashMap<>();
            scoreClause.put(KEY, COLUMN_SCORE);
            scoreClause.put(VALUE, score);
            setClauses.add(scoreClause);
            List<Map<String, Object>> conditions = new ArrayList<>();
            HashMap<String, Object> condition = new HashMap<>();
            condition.put(KEY, ID);
            condition.put(VALUE, id);
            conditions.add(condition);
            dynamicRegisterInfoMapper.updateDynamic(tableName, setClauses, conditions);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("审批发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "审批发生异常");
        }
        return result;

    }
}
