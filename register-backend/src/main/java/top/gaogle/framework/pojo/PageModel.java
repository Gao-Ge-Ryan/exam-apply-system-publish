package top.gaogle.framework.pojo;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageModel<T> {

    private List<T> rows;
    private Long total;
    private Integer pageNum;
    private Integer pageSize;

    public PageModel(List<T> rows) {
        PageInfo<T> pageInfo = new PageInfo<>(rows);
        this.total = pageInfo.getTotal();
        this.rows = rows;
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
