package com.jlb.model;

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
public class DynamicState implements Serializable {
    public DynamicState() {
    }

    public DynamicState(String dynamicStateAction, String dynamicStateContent, String dynamicType, Integer dynamicTypeId) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Member member = (Member) session.getAttribute("user");
        this.dynamicStateCreateId = member.getMemberId();
        this.dynamicStateAction = dynamicStateAction;
        this.dynamicStateContent = dynamicStateContent;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        this.dynamicStateCreateTime = df.format(new Date());
        int type = 0;
        if (dynamicType.equals("普通类型")) {
            type = 0;
        } else if (dynamicType.equals("项目")) {
            type = 1;
        } else if (dynamicType.equals("任务")) {
            type = 2;
        } else if (dynamicType.equals("成员")) {
            type = 3;
        }else if (dynamicType.equals("团队")) {
            type = 4;
        }
        this.dynamicStateType = type;
        this.dynamicStateTypeId = dynamicTypeId;
    }

    private Integer dynamicStateId;
    private Integer dynamicStateCreateId;
    private Member dynamicStateCreator;
    private String dynamicStateAction;
    private String dynamicStateContent;
    private String dynamicStateCreateTime;
    private Integer dynamicStateType;//动态类型
    private Integer dynamicStateTypeId;//动态类型对应id

}
