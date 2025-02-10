package top.gaogle.service.task.quartz;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * 定时任务调度测试
 *
 * @author gaogle
 */
@Component("ryTask")
public class RyTask {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        log.info("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i);
    }

    public void ryParams(String params) {
        System.out.println("执行有参方法：" + params);
    }

    public void ryNoParams() {
        System.out.println("执行无参方法");
    }
}
