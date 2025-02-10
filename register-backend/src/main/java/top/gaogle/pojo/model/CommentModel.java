package top.gaogle.pojo.model;

import top.gaogle.pojo.domain.Comment;

import java.util.List;

public class CommentModel extends Comment {
    private List<CommentModel> children;

    public List<CommentModel> getChildren() {
        return children;
    }

    public void setChildren(List<CommentModel> children) {
        this.children = children;
    }
}
