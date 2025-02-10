package top.gaogle.service.task;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.gaogle.dao.master.SysJobMapper;
import top.gaogle.framework.util.CacheUtil;
import top.gaogle.framework.util.ScheduleUtils;
import top.gaogle.framework.util.SpringUtil;
import top.gaogle.pojo.domain.SysJob;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class InitTask {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ScheduledExecutorService executor = SpringUtil.getBean("scheduledExecutorService");
    private final Scheduler scheduler;
    private final SysJobMapper jobMapper;

    @Autowired
    public InitTask(Scheduler scheduler, SysJobMapper jobMapper) {
        this.scheduler = scheduler;
        this.jobMapper = jobMapper;
    }


    @PostConstruct
    public void initTokenClean() {
        executor.scheduleWithFixedDelay(tokenClean(), 10, 60, TimeUnit.MINUTES);
        log.info("{initTokenClean} Initialization complete");
    }

    /**
     * 项目启动时，初始化定时器 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
     */
    @PostConstruct
    public void initQuartzJobs() throws SchedulerException {
        scheduler.clear();
        List<SysJob> jobList = jobMapper.selectJobAll();
        for (SysJob job : jobList) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        }
        log.info("{initQuartzJobs} Initialization complete");
    }

    public TimerTask tokenClean() {
        return new TimerTask() {
            @Override
            public void run() {
                ConcurrentMap<String, String> tokenMap = CacheUtil.getTokenMap();
                log.info("InitTask tokenClean tokenMap before size:{}", tokenMap.size());
                tokenMap.clear();
                log.info("InitTask tokenClean tokenMap after size:{}", tokenMap.size());
            }
        };
    }

}
