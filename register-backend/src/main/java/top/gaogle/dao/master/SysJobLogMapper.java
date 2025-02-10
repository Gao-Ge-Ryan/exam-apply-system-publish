package top.gaogle.dao.master;


import org.springframework.stereotype.Repository;
import top.gaogle.pojo.domain.SysJobLog;

/**
 * 调度任务日志信息 数据层
 *
 * @author gaogle
 */
@Repository
public interface SysJobLogMapper {

    /**
     * 新增任务日志
     *
     * @param jobLog 调度日志信息
     * @return 结果
     */
    int insertJobLog(SysJobLog jobLog);

}
