package top.gaogle.framework.pojo;


import org.quartz.JobExecutionContext;
import top.gaogle.framework.util.JobInvokeUtil;
import top.gaogle.pojo.domain.SysJob;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author gaogle
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
