package com.jlb.service.Impl;

import com.jlb.mapper.CommentMapper;
import com.jlb.model.Comment;
import com.jlb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;

    /**
     * 通过类型和类型id删除对应的评论信息
     *
     * @param type   类型
     * @param typeId 对应类型id
     */
    @Override
    public void deleteCommentByTypeAndTypeId(String type, int typeId) {
        commentMapper.deleteCommentByTypeAndTypeId(type,typeId);
    }

    /**
     * 删除评论
     *
     * @param comment
     */
    @Override
    public void deleteCommentByComment(Comment comment) {
        commentMapper.deleteCommentByComment(comment);
    }

    /**
     * 通过类型和类型id查询对应的评论信息
     *
     * @param type   类型
     * @param typeId 对应类型id
     * @return 评论信息
     */
    @Override
    public List<Comment> queryCommentByTypeAndTypeId(int type, int typeId) {
        return commentMapper.queryCommentByTypeAndTypeId(type, typeId);
    }

    /**
     * 添加评论信息
     *
     * @param comment 评论
     */
    @Override
    public void addComment(Comment comment) {
        commentMapper.addComment(comment);
    }
}
