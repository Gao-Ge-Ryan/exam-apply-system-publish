package com.exam.service;

import com.github.pagehelper.PageInfo;
import com.exam.pojo.model.InfoModel;
import com.exam.pojo.param.InfoParam;

/**
 * (Info)表服务接口
 *
 * @author gaoge
 * @since 2021-11-11 11:13:06
 */
public interface InfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InfoModel queryById(String id);

    /**
     * 分页查询
     *
     * @param info 筛选条件
     * @param pageNum      当前页数
     * @param pageSize     每页显示数量
     * @return 查询结果
     */
    PageInfo<InfoModel> queryByPage(InfoParam info, Integer pageNum, Integer pageSize);

    /**
     * 新增数据
     *
     * @param info 实例对象
     * @return 实例对象
     */
    InfoParam insert(InfoParam info);

    /**
     * 修改数据
     *
     * @param info 实例对象
     * @return 实例对象
     */
    InfoModel update(InfoParam info);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

}
