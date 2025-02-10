package top.gaogle.framework.pojo;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import top.gaogle.framework.util.JobInvokeUtil;
import top.gaogle.pojo.domain.SysJob;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @author gaogle
 */
@DisallowConcurrentExecution
public class QuartzDisallowConcurrentExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
