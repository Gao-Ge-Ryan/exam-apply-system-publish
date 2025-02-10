package top.gaogle.dao.master;


import org.springframework.stereotype.Repository;
import top.gaogle.pojo.domain.SysJob;

import java.util.List;

/**
 * 调度任务信息 数据层
 *
 * @author gaogle
 */
@Repository
public interface SysJobMapper {

    /**
     * 新增调度任务信息
     *
     * @param job 调度任务信息
     * @return 结果
     */
    int insertJob(SysJob job);

    /**
     * 查询所有调度任务
     *
     * @return 调度任务列表
     */
    List<SysJob> selectJobAll();

    /**
     * 修改调度任务信息
     *
     * @param job 调度任务信息
     * @return 结果
     */
    int updateJob(SysJob job);

    /**
     * 通过调度ID删除调度任务信息
     *
     * @param jobId 调度ID
     * @return 结果
     */
    int deleteJobById(String jobId);

    /**
     * 通过调度ID查询调度任务信息
     *
     * @param jobId 调度ID
     * @return 角色对象信息
     */
    SysJob selectJobById(String jobId);
}
