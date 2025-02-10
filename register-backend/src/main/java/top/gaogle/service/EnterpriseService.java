package top.gaogle.service;

import com.github.pagehelper.page.PageMethod;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.gaogle.dao.master.EnterpriseMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.framework.util.StringUtil;
import top.gaogle.framework.util.UniqueUtil;
import top.gaogle.pojo.enums.BusinessTypeEnum;
import top.gaogle.pojo.enums.CommentStatusEnum;
import top.gaogle.pojo.enums.EnterpriseStatusEnum;
import top.gaogle.pojo.enums.HttpStatusEnum;
import top.gaogle.pojo.model.EnterpriseModel;
import top.gaogle.pojo.model.TestEnumModel;
import top.gaogle.pojo.param.EnterpriseEditParam;
import top.gaogle.pojo.param.EnterpriseQueryParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnterpriseService extends SuperService {
    private final EnterpriseMapper enterpriseMapper;

    @Autowired
    public EnterpriseService(EnterpriseMapper enterpriseMapper) {
        this.enterpriseMapper = enterpriseMapper;
    }


    public I18nResult<Boolean> insert(EnterpriseEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            if (StringUtils.isAnyEmpty()) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "缺少必要参数");
            }
            editParam.setId(UniqueUtil.getUniqueId());
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setCreateAt(timeMillis);
            editParam.setUpdateAt(timeMillis);
            String loginUsername = SecurityUtil.getLoginUsername();
            editParam.setCreateBy(loginUsername);
            editParam.setUpdateBy(loginUsername);
            enterpriseMapper.insert(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<PageModel<EnterpriseModel>> queryByPageAndCondition(EnterpriseQueryParam queryParam) {
        I18nResult<PageModel<EnterpriseModel>> result = I18nResult.newInstance();
        try {
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            List<EnterpriseModel> enterpriseModels = enterpriseMapper.queryByPageAndCondition(queryParam);
            PageModel<EnterpriseModel> pageModel = new PageModel<>(enterpriseModels);
            result.succeed().setData(pageModel);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> putEnterprise(EnterpriseEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            enterpriseMapper.putEnterprise(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> deleteById(String id) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            enterpriseMapper.deleteById(id);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("删除企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "删除企业发生异常");
        }
        return result;
    }

    public I18nResult<EnterpriseModel> queryOneById(String id) {
        I18nResult<EnterpriseModel> result = I18nResult.newInstance();
        try {
            EnterpriseModel enterpriseModel = enterpriseMapper.queryOneById(id);
            result.succeed().setData(enterpriseModel);
        } catch (Exception e) {
            log.error("删除企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "删除企业发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> apply(EnterpriseEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String description = editParam.getDescription();
            String name = editParam.getName();
            if (StringUtils.isAnyEmpty(name, description)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "缺少必要参数");
            }
            String loginUsername = SecurityUtil.getLoginUsername();
            EnterpriseModel enterpriseModel = enterpriseMapper.queryByCreateBy(loginUsername);
            if (enterpriseModel != null) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage(I18ResultCode.MESSAGE, "该账号下存在企业");
            }
            editParam.setId(UniqueUtil.getUniqueId());
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setCreateAt(timeMillis);
            editParam.setUpdateAt(timeMillis);

            editParam.setCreateBy(loginUsername);
            editParam.setUpdateBy(loginUsername);
            editParam.setStatus(EnterpriseStatusEnum.NEW);
            editParam.setBalance(0L);
            enterpriseMapper.insert(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> approve(EnterpriseStatusEnum statusEnum, String enterpriseId) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            EnterpriseEditParam editParam = new EnterpriseEditParam();
            editParam.setId(enterpriseId);
            editParam.setStatus(statusEnum);
            enterpriseMapper.putEnterprise(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<List<EnterpriseModel>> queryAllByAndCondition(EnterpriseQueryParam queryParam) {
        I18nResult<List<EnterpriseModel>> result = I18nResult.newInstance();
        try {
            EnterpriseEditParam editParam = new EnterpriseEditParam();
            editParam.setStatus(EnterpriseStatusEnum.REVIEW_PASSED);
            List<EnterpriseModel> enterpriseModels = enterpriseMapper.queryAllByAndCondition(queryParam);
            result.succeed().setData(enterpriseModels);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<PageModel<EnterpriseModel>> clientQueryByPage(EnterpriseQueryParam queryParam) {
        I18nResult<PageModel<EnterpriseModel>> result = I18nResult.newInstance();
        try {
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            List<EnterpriseModel> enterpriseModels = enterpriseMapper.clientQueryByPage();
            PageModel<EnterpriseModel> pageModel = new PageModel<>(enterpriseModels);
            result.succeed().setData(pageModel);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> enterprisePutEnterprise(EnterpriseEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String id = editParam.getId();
            if (StringUtils.isEmpty(id)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺失必要参数");
            }
            enterpriseMapper.enterprisePutEnterprise(id, editParam.getLogo(), editParam.getSlideshow(), editParam.getDescription());
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<EnterpriseModel> clientQueryEnterprise(String enterpriseId) {
        I18nResult<EnterpriseModel> result = I18nResult.newInstance();
        try {
            if (StringUtils.isEmpty(enterpriseId)) {
                return result.failed().setStatus(HttpStatusEnum.BAD_REQUEST).setMessage("缺失必要参数");
            }
            EnterpriseModel enterpriseModel = enterpriseMapper.clientQueryEnterprise(enterpriseId);
            result.succeed().setData(enterpriseModel);
        } catch (Exception e) {
            log.error("客户端查询企业详情发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "客户端查询企业详情发生异常");
        }
        return result;
    }

    public I18nResult<EnterpriseModel> enterpriseQueryEnterprise() {
        I18nResult<EnterpriseModel> result = I18nResult.newInstance();
        try {
            String enterpriseId = SecurityUtil.getEnterpriseId();
            EnterpriseModel enterpriseModel = enterpriseMapper.queryOneById(enterpriseId);
            result.succeed().setData(enterpriseModel);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<TestEnumModel> testData() {
        I18nResult<TestEnumModel> result = I18nResult.newInstance();
        TestEnumModel testEnumModel = new TestEnumModel();
        List<BusinessTypeEnum> businessTypes =new ArrayList<>();
        businessTypes.add(BusinessTypeEnum.CLEAN);
        businessTypes.add(BusinessTypeEnum.UPDATE);
        businessTypes.add(BusinessTypeEnum.DELETE);
        testEnumModel.setBusinessTypes(businessTypes);
        testEnumModel.setCommentStatus(CommentStatusEnum.NEW);
        testEnumModel.setName("gaogle12345678910111213");
        log.info("测试请求yu");
        return result.succeed().setData(testEnumModel);

    }

    public I18nResult<String> enterpriseQueryEnterpriseBalance() {
        I18nResult<String> result = I18nResult.newInstance();
        try {
            String enterpriseId = SecurityUtil.getEnterpriseId();
            long balance = enterpriseMapper.queryBalanceById(enterpriseId);
            result.succeed().setData(StringUtil.amountLong2String(balance));
        } catch (Exception e) {
            log.error("查询企业余额发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询企业余额发生异常");
        }
        return result;
    }
}
