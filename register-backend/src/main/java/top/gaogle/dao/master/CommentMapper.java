package top.gaogle.dao.master;

import org.springframework.stereotype.Repository;
import top.gaogle.pojo.model.CommentModel;
import top.gaogle.pojo.param.CommentEditParam;
import top.gaogle.pojo.param.CommentQueryParam;

import java.util.List;

@Repository
public interface CommentMapper {

    int insert(CommentEditParam editParam);

    List<CommentModel> queryByParentId(String parentId);

    List<CommentModel> queryByPageAndCondition(CommentQueryParam queryParam);

    int putComment(CommentEditParam editParam);

    int deleteById(String id);

    CommentModel queryOneByIdAndRoot(String id, String parentId);

    List<CommentModel> queryByRootId(String rootId);
}
