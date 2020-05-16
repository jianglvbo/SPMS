package com.jlb.mapper;

import com.jlb.model.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentMapper {


    /**
     * 通过类型和类型id删除对应的评论信息
     *
     * @param type   类型
     * @param typeId 对应类型id
     */
    @Delete("delete from comment where comment_type=#{type} and comment_type_id=#{typeId}")
    void deleteCommentByTypeAndTypeId(String type, int typeId);

    /**
     * 删除评论
     *
     * @param comment
     */
    @Delete("delete from comment where comment_type=#{commentType} and comment_type_id=#{commentTypeId}")
    void deleteCommentByComment(Comment comment);

    /**
     * 通过类型和类型id查询对应的评论信息
     *
     * @param type   类型
     * @param typeId 对应类型id
     * @return 评论信息
     */
    @Select("select * from comment where comment_type=#{type} and comment_type_id=#{typeId}")
    @Results(id = "comment_member", value = {
            @Result(property = "commentCreator", column = "comment_create_id", one = @One(select = "com.jlb.mapper.MemberMapper.queryMemberById"))
    })
    List<Comment> queryCommentByTypeAndTypeId(int type, int typeId);

    /**
     * 添加评论信息
     *
     * @param comment 评论
     */
    @Insert("insert  into  comment (comment_type, comment_type_id, comment_content, comment_create_time, comment_create_id) " +
            "values(#{commentType},#{commentTypeId},#{commentContent},#{commentCreateTime},#{commentCreateId})")
    void addComment(Comment comment);
}
