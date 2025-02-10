package top.gaogle.framework.manager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.gaogle.dao.master.OperateLogMapper;
import top.gaogle.pojo.domain.OperateLog;
import top.gaogle.framework.util.AddressUtil;
import top.gaogle.framework.util.SpringUtil;

import java.util.TimerTask;

/**
 * 异步工厂（产生任务用）
 *
 * @author gaogle
 */
public class AsyncFactory {
    private static final Logger sys_user_logger = LoggerFactory.getLogger("sys-user");


    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOperate(final OperateLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operLog.setOperateLocation(AddressUtil.getRealAddressByIP(operLog.getOperateIp()));
                SpringUtil.getBean(OperateLogMapper.class).insert(operLog);
            }
        };
    }
}
