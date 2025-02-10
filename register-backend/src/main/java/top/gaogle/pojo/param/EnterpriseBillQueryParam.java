package top.gaogle.pojo.param;

import top.gaogle.framework.pojo.SuperQuerying;
import top.gaogle.pojo.domain.EnterpriseBill;

public class EnterpriseBillQueryParam extends EnterpriseBill implements SuperQuerying {

    private Integer pageNum = 1;
    private Integer pageSize = Integer.MAX_VALUE;
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
