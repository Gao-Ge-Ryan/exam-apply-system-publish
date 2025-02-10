package top.gaogle.service;

import com.github.pagehelper.page.PageMethod;
import org.springframework.stereotype.Service;
import top.gaogle.dao.master.OperateLogMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.pojo.domain.OperateLog;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.pojo.param.OperateLogQueryParam;

import java.util.List;

@Service
public class OperateLogService extends SuperService {
    private final OperateLogMapper operateLogMapper;

    public OperateLogService(OperateLogMapper operateLogMapper) {
        this.operateLogMapper = operateLogMapper;
    }

    public I18nResult<PageModel<OperateLog>> queryByPageAndCondition(OperateLogQueryParam queryParam) {
        I18nResult<PageModel<OperateLog>> result = I18nResult.newInstance();
        try {
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            List<OperateLog> operateLogs = operateLogMapper.queryByPageAndCondition(queryParam);
            result.succeed().setData(new PageModel<>(operateLogs));
        } catch (Exception e) {
            log.error("分页条件查询操作日志失败：", e);
            result.failed().setMessage(I18ResultCode.MESSAGE,"分页条件查询操作日志失败");
        }
        return result;
    }
}
