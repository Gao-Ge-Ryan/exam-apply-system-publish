package top.gaogle.pojo.param;

import top.gaogle.pojo.domain.OperateLog;
import top.gaogle.framework.pojo.SuperQuerying;

public class OperateLogQueryParam extends OperateLog implements SuperQuerying {

    private Integer pageNum = 1;
    private Integer pageSize = 20;
    private String search;
    private String sort;
    private String order;

    @Override
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
