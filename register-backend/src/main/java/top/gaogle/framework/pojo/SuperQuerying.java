package top.gaogle.framework.pojo;

public interface SuperQuerying {
    //页数
    Integer getPageNum();

    // 页大小
    Integer getPageSize();

    // 搜索关键词
    String getSearch();

    // 排序字段
    String getSort();

    // 正序倒叙
    String getOrder();

}
