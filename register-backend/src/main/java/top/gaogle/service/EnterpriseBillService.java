package top.gaogle.service;

import com.github.pagehelper.page.PageMethod;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.gaogle.dao.master.EnterpriseBillMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.framework.util.StringUtil;
import top.gaogle.pojo.enums.BillStatusEnum;
import top.gaogle.pojo.model.EnterpriseBillModel;
import top.gaogle.pojo.param.EnterpriseBillQueryParam;

import java.util.List;

@Service
public class EnterpriseBillService extends SuperService {

    private final EnterpriseBillMapper enterpriseBillMapper;

    @Autowired
    public EnterpriseBillService(EnterpriseBillMapper enterpriseBillMapper) {
        this.enterpriseBillMapper = enterpriseBillMapper;
    }

    public I18nResult<PageModel<EnterpriseBillModel>> enterpriseQueryByPage(EnterpriseBillQueryParam queryParam) {
        I18nResult<PageModel<EnterpriseBillModel>> result = I18nResult.newInstance();
        try {
            String enterpriseId = SecurityUtil.getEnterpriseId();
            if (StringUtils.isAnyEmpty(enterpriseId)) {
                return result.failedBadRequest().setMessage("缺少必要参数");
            }
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            queryParam.setEnterpriseId(enterpriseId);
            queryParam.setStatus(BillStatusEnum.VALID);
            List<EnterpriseBillModel> models = enterpriseBillMapper.enterpriseQueryByPage(queryParam);
            if (CollectionUtils.isNotEmpty(models)) {
                for (EnterpriseBillModel model : models) {
                    model.setStrAmount(StringUtil.amountLong2String(model.getAmount()));
                    model.setStrBalance(StringUtil.amountLong2String(model.getBalance()));
                }
            }
            PageModel<EnterpriseBillModel> pageModel = new PageModel<>(models);
            result.succeed().setData(pageModel);
        } catch (Exception e) {
            log.error("添加企业发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }
}
