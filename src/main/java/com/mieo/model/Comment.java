package com.mieo.model;

import lombok.Data;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Component
public class Comment implements Serializable {
    public Comment(){

    }
    public Comment(String commentContent,String commentType,Integer commentTypeId){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Member member = (Member) session.getAttribute("user");
        if(commentType.equals("项目")){
            this.commentType="1";
        }else if(commentType.equals("任务")){
            this.commentType="2";
        }
        this.commentTypeId=commentTypeId;
        this.commentContent=commentContent;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
       this.commentCreateTime = df.format(new Date());
       this.commentCreateId=member.getMemberId();
    }
    private Integer commentId;//评论id
    private String  commentType;//评论类型
    private Integer commentTypeId;//评论类型对应id
    private String commentContent;//评论内容
    private String commentCreateTime;//评论时间
    private Integer commentCreateId;//评论者Id
    private Member commentCreator;//评论者
}
