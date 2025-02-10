package top.gaogle.service;

import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.gaogle.dao.master.FormTemplateMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.pojo.model.FormTemplateModel;
import top.gaogle.pojo.param.FormTemplateEditParam;
import top.gaogle.pojo.param.FormTemplateQueryParam;

import java.util.List;

@Service
public class FormTemplateService extends SuperService {
    private final FormTemplateMapper formTemplateMapper;

    @Autowired
    public FormTemplateService(FormTemplateMapper formTemplateMapper) {
        this.formTemplateMapper = formTemplateMapper;
    }

    public I18nResult<Boolean> add(FormTemplateEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String loginUsername = SecurityUtil.getLoginUsername();
            editParam.setCreateBy(loginUsername);
            editParam.setUpdateBy(loginUsername);
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setCreateAt(timeMillis);
            editParam.setUpdateAt(timeMillis);
            formTemplateMapper.insert(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加表单发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> put(FormTemplateEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            String loginUsername = SecurityUtil.getLoginUsername();
            editParam.setUpdateBy(loginUsername);
            Long timeMillis = DateUtil.currentTimeMillis();
            editParam.setUpdateAt(timeMillis);
            formTemplateMapper.update(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加表单发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<PageModel<FormTemplateModel>> queryByPageAndCondition(FormTemplateQueryParam queryParam) {
        I18nResult<PageModel<FormTemplateModel>> result = I18nResult.newInstance();
        try {
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            List<FormTemplateModel> formTemplateModels = formTemplateMapper.queryByPageAndCondition(queryParam);
            result.succeed().setData(new PageModel<>(formTemplateModels));
        } catch (Exception e) {
            log.error("添加表单发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> deleteById(String id) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            formTemplateMapper.deleteById(id);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加表单发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加企业发生异常");
        }
        return result;
    }

    public I18nResult<FormTemplateModel> queryOneById(String id) {
        I18nResult<FormTemplateModel> result = I18nResult.newInstance();
        try {
            FormTemplateModel formTemplateModel =formTemplateMapper.queryOneById(id);
            result.succeed().setData(formTemplateModel);
        } catch (Exception e) {
            log.error("根据id查询表单详情发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "根据id查询表单详情发生异常");
        }
        return result;
    }
}
