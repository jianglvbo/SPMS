package com.jlb.service;


import com.jlb.model.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 通过类型和类型id删除对应的评论信息
     *
     * @param type   类型
     * @param typeId 对应类型id
     */
    void deleteCommentByTypeAndTypeId(String type, int typeId);

    /**
     * 删除评论
     *
     * @param comment
     */
    void deleteCommentByComment(Comment comment);

    /**
     * 通过类型和类型id查询对应的评论信息
     *
     * @param type   类型
     * @param typeId 对应类型id
     * @return 评论信息
     */
    List<Comment> queryCommentByTypeAndTypeId(int type, int typeId);

    /**
     * 添加评论信息
     *
     * @param comment 评论
     */
    void addComment(Comment comment);
}
