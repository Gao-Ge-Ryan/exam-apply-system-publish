package top.gaogle.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.page.PageMethod;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import top.gaogle.dao.master.FormTemplateMapper;
import top.gaogle.dao.master.PublishSpotMapper;
import top.gaogle.dao.master.RegisterPublishMapper;
import top.gaogle.dao.slave.DynamicRegisterInfoMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.JsonUtil;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.framework.util.UniqueUtil;
import top.gaogle.pojo.dto.BindSpotDTO;
import top.gaogle.pojo.enums.HttpStatusEnum;
import top.gaogle.pojo.model.FormTemplateModel;
import top.gaogle.pojo.model.RegisterPublishModel;
import top.gaogle.pojo.param.PublishSpotEditParam;
import top.gaogle.pojo.param.RegisterPublishEditParam;
import top.gaogle.pojo.param.RegisterPublishQueryParam;

import java.util.List;
import java.util.Objects;

@Service
public class RegisterPublishService extends SuperService {
    private final RegisterPublishMapper registerPublishMapper;
    private final FormTemplateMapper formTemplateMapper;
    private final TransactionTemplate transactionTemplate;
    private final PublishSpotMapper publishSpotMapper;
    private final TransactionTemplate slaveTransactionTemplate;
    private final DynamicRegisterInfoMapper dynamicRegisterInfoMapper;
    private final MoreTransactionService moreTransactionService;


    @Autowired
    public RegisterPublishService(RegisterPublishMapper registerPublishMapper, FormTemplateMapper formTemplateMapper, @Qualifier("transactionTemplate") TransactionTemplate transactionTemplate, PublishSpotMapper publishSpotMapper, @Qualifier("slaveTransactionTemplate") TransactionTemplate slaveTransactionTemplate, DynamicRegisterInfoMapper dynamicRegisterInfoMapper, MoreTransactionService moreTransactionService) {
        this.registerPublishMapper = registerPublishMapper;
        this.formTemplateMapper = formTemplateMapper;
        this.transactionTemplate = transactionTemplate;
        this.publishSpotMapper = publishSpotMapper;
        this.slaveTransactionTemplate = slaveTransactionTemplate;
        this.dynamicRegisterInfoMapper = dynamicRegisterInfoMapper;
        this.moreTransactionService = moreTransactionService;
    }

    public I18nResult<Boolean> add(RegisterPublishEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String loginUsername = SecurityUtil.getLoginUsername();
            String enterpriseId = SecurityUtil.getEnterpriseId();
            editParam.setCreateBy(loginUsername);
            editParam.setUpdateBy(loginUsername);
            editParam.setEnterpriseId(enterpriseId);
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setCreateAt(timeMillis);
            editParam.setUpdateAt(timeMillis);
            FormTemplateModel formTemplateModel = formTemplateMapper.queryOneByFlag(editParam.getTemplateFlag());
            String templateContent = formTemplateModel.getContent();
            editParam.setTemplateCopy(templateContent);
            ObjectNode objectNode = JsonUtil.parseObjectNode(templateContent);
            moreTransactionService.registerPublish(objectNode, editParam);

            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> put(RegisterPublishEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String loginUsername = SecurityUtil.getLoginUsername();
            editParam.setUpdateBy(loginUsername);
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setUpdateAt(timeMillis);
            registerPublishMapper.update(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("修改发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "修改发生异常");
        }
        return result;
    }

    public I18nResult<PageModel<RegisterPublishModel>> queryByPageAndCondition(RegisterPublishQueryParam queryParam) {
        I18nResult<PageModel<RegisterPublishModel>> result = I18nResult.newInstance();
        try {
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            List<RegisterPublishModel> registerPublishModels = registerPublishMapper.queryByPageAndCondition(queryParam);
            result.succeed().setData(new PageModel<>(registerPublishModels));
        } catch (Exception e) {
            log.error("分页条件查询发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "分页条件查询发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> deleteById(String id) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            registerPublishMapper.deleteById(id);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("根据id删除发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "根据id删除发生异常");
        }
        return result;
    }

    public I18nResult<RegisterPublishModel> queryOneById(String id) {
        I18nResult<RegisterPublishModel> result = I18nResult.newInstance();
        try {
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(id);
            result.succeed().setData(registerPublishModel);
        } catch (Exception e) {
            log.error("根据id查询详情发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "根据id查询详情发生异常");
        }
        return result;
    }

    public I18nResult<PageModel<RegisterPublishModel>> enterpriseQueryByPageAndCondition(RegisterPublishQueryParam queryParam) {
        I18nResult<PageModel<RegisterPublishModel>> result = I18nResult.newInstance();
        try {
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            queryParam.setEnterpriseId(SecurityUtil.getEnterpriseId());
            List<RegisterPublishModel> registerPublishModels = registerPublishMapper.queryByPageAndCondition(queryParam);
            result.succeed().setData(new PageModel<>(registerPublishModels));
        } catch (Exception e) {
            log.error("分页条件查询发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "分页条件查询发生异常");
        }
        return result;
    }

    public I18nResult<PageModel<RegisterPublishModel>> clientQueryByPageAndCondition(RegisterPublishQueryParam queryParam) {
        I18nResult<PageModel<RegisterPublishModel>> result = I18nResult.newInstance();
        try {
            String enterpriseId = queryParam.getEnterpriseId();
            if (StringUtils.isEmpty(enterpriseId)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺失必要参数");
            }
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            queryParam.setEnterpriseId(enterpriseId);
            List<RegisterPublishModel> registerPublishModels = registerPublishMapper.queryByPageAndCondition(queryParam);
            result.succeed().setData(new PageModel<>(registerPublishModels));
        } catch (Exception e) {
            log.error("分页条件查询发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "分页条件查询发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> bindSpot(BindSpotDTO bindSpotDTO) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String registerPublishId = bindSpotDTO.getRegisterPublishId();
            List<String> spotInfoIds = bindSpotDTO.getSpotInfoIds();
            if (StringUtils.isEmpty(registerPublishId) || CollectionUtils.isEmpty(spotInfoIds)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺失必要参数");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(registerPublishId);
            String enterpriseId = SecurityUtil.getEnterpriseId();
            String publishEnterpriseId = registerPublishModel.getEnterpriseId();
            if (!Objects.equals(enterpriseId, publishEnterpriseId)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("没有权限操作");
            }
            Long timeMillis = DateUtil.currentTimeMillis();
            String loginUsername = SecurityUtil.getLoginUsername();
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(@NonNull TransactionStatus status) {
                    publishSpotMapper.deleteByRegisterPublishId(registerPublishId);
                    for (String spotInfoId : spotInfoIds) {
                        PublishSpotEditParam editParam = new PublishSpotEditParam();
                        editParam.setId(UniqueUtil.getUniqueId());
                        editParam.setRegisterPublishId(registerPublishId);
                        editParam.setSpotInfoId(spotInfoId);
                        editParam.setCreateAt(timeMillis);
                        editParam.setCreateBy(loginUsername);
                        publishSpotMapper.insert(editParam);
                    }
                }
            });

            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("操作发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "操作发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> unbindSpot(BindSpotDTO bindSpotDTO) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String registerPublishId = bindSpotDTO.getRegisterPublishId();
            String unbindSpotInfoId = bindSpotDTO.getUnbindSpotInfoId();
            if (StringUtils.isAnyEmpty(registerPublishId, unbindSpotInfoId)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺失必要参数");
            }
            RegisterPublishModel registerPublishModel = registerPublishMapper.queryOneById(registerPublishId);
            String enterpriseId = SecurityUtil.getEnterpriseId();
            String publishEnterpriseId = registerPublishModel.getEnterpriseId();
            if (!Objects.equals(enterpriseId, publishEnterpriseId)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("没有权限操作");
            }
            publishSpotMapper.deleteByRegisterPublishIdAndSpotInfoId(registerPublishId, unbindSpotInfoId);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("操作发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "操作发生异常");
        }
        return result;
    }
}
