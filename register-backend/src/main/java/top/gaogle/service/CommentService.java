package top.gaogle.service;

import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.gaogle.dao.master.CommentMapper;
import top.gaogle.framework.i18n.I18ResultCode;
import top.gaogle.framework.i18n.I18nResult;
import top.gaogle.framework.pojo.PageModel;
import top.gaogle.framework.util.DateUtil;
import top.gaogle.framework.util.SecurityUtil;
import top.gaogle.framework.util.UniqueUtil;
import top.gaogle.pojo.model.CommentModel;
import top.gaogle.pojo.param.CommentEditParam;
import top.gaogle.pojo.param.CommentQueryParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CommentService extends SuperService {

    private final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public I18nResult<Boolean> addComment(CommentEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            editParam.setId(UniqueUtil.getUniqueId());
            editParam.setCreateBy(SecurityUtil.getLoginUsername());
            editParam.setCreateAt(DateUtil.currentTimeMillis());
            commentMapper.insert(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加评论发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加评论发生异常");
        }
        return result;
    }


    public I18nResult<List<CommentModel>> getAllComments() {
        I18nResult<List<CommentModel>> result = I18nResult.newInstance();
        try {
            List<CommentModel> commentModels = commentMapper.queryByParentId("root");
            commentModels.forEach(this::setChildren);
            result.succeed().setData(commentModels);
        } catch (Exception e) {
            log.error("获取所有评论发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "获取所有评论发生异常");
        }
        return result;
    }

    /**
     * 递归获取
     *
     * @param commentDTO 参数
     */

    public void setChildren(CommentModel commentDTO) {
        List<CommentModel> children = commentMapper.queryByParentId(commentDTO.getId());
        if (!children.isEmpty()) {
            commentDTO.setChildren(children);
            children.forEach(this::setChildren);
        }
    }

    public I18nResult<PageModel<CommentModel>> queryByPageAndCondition(CommentQueryParam queryParam) {
        I18nResult<PageModel<CommentModel>> result = I18nResult.newInstance();
        try {
            queryParam.setParentId("root");
            PageMethod.startPage(queryParam.getPageNum(), queryParam.getPageSize());
            List<CommentModel> commentModels = commentMapper.queryByPageAndCondition(queryParam);
            PageModel<CommentModel> pageModel = new PageModel<>(commentModels);
            result.succeed().setData(pageModel);
        } catch (Exception e) {
            log.error("分页条件查询评论发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "分页条件查询评论发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> putComment(CommentEditParam editParam) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            commentMapper.putComment(editParam);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加评论发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加评论发生异常");
        }
        return result;
    }

    public I18nResult<Boolean> deleteById(String id) {
        I18nResult<Boolean> result = I18nResult.newInstance();
        try {
            commentMapper.deleteById(id);
            result.succeed().setData(true);
        } catch (Exception e) {
            log.error("添加评论发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "添加评论发生异常");
        }
        return result;
    }

    public I18nResult<CommentModel> queryOneById(String id) {
        I18nResult<CommentModel> result = I18nResult.newInstance();
        try {
            CommentModel commentModel = commentMapper.queryOneByIdAndRoot(id, "root");
            if (commentModel != null) {
                List<CommentModel> childComments = commentMapper.queryByRootId(commentModel.getId());
                if (!CollectionUtils.isEmpty(childComments)) {
                    Map<String, List<CommentModel>> commentsGroupedByParentId = childComments.stream()
                            .collect(Collectors.groupingBy(CommentModel::getParentId));
                    setChildren(commentModel, commentsGroupedByParentId);
                }
            }
            result.succeed().setData(commentModel);
        } catch (Exception e) {
            log.error("查询评论发生异常:", e);
            result.failed().setMessage(I18ResultCode.MESSAGE, "查询评论发生异常");
        }
        return result;
    }

    public void setChildren(CommentModel parent, Map<String, List<CommentModel>> commentsGrouped) {
        List<CommentModel> children = commentsGrouped.get(parent.getId());
        if (!CollectionUtils.isEmpty(children)) {
            parent.setChildren(children);
            for (CommentModel child : children) {
                setChildren(child, commentsGrouped);
            }

        }
    }
}
