package com.mieo.controller;

import com.mieo.common.util.MessageUtil;
import com.mieo.model.Comment;
import com.mieo.model.Team;
import com.mieo.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    MessageUtil messageUtil;

    @RequestMapping("queryCommentByTypeAndTypeId")
    @ResponseBody
    public List<Comment> queryCommentByTypeAndTypeId(@RequestBody Map<String,Integer>map){
        Integer type = MapUtils.getInteger(map, "type");
        Integer typeId = MapUtils.getInteger(map, "typeId");
        return commentService.queryCommentByTypeAndTypeId(type,typeId);
    };

    @RequestMapping("addComment")
    @ResponseBody
    public Map<String,String> addComment(@RequestBody Map<String,String> map){
        String commentType = MapUtils.getString(map, "commentType");
        String commentContent = MapUtils.getString(map, "commentContent");
        Integer commentTypeId = MapUtils.getInteger(map, "commentTypeId");
        Comment comment1=new Comment(commentContent,commentType,commentTypeId);
        commentService.addComment(comment1);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg", "评论成功");
        return msg;
    };

}
