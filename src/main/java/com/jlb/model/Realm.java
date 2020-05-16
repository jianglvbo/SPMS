package com.jlb.model;

import com.jlb.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

@Slf4j
public class Realm extends AuthorizingRealm implements Serializable {
    @Autowired
    MemberService memberService;

    /**
     * 授权过滤器
     * 执行授权逻辑
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("执行授权逻辑");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject subject = SecurityUtils.getSubject();
        //获取当前登录用户
        Member member = (Member) subject.getPrincipal();
        //添加资源认证
        //管理员
        if (member.getMemberRole().equals("1")) {
            info.addStringPermission("project:add");
            info.addStringPermission("project:delete");
            info.addStringPermission("project:archive");
            info.addStringPermission("project:update");
            info.addStringPermission("task:add");
            info.addStringPermission("task:delete");
            info.addStringPermission("task:edit");
            info.addStringPermission("task:archive");
            info.addStringPermission("task:update");
            info.addStringPermission("member:add");
            info.addStringPermission("member:delete");
            info.addStringPermission("team:add");
            info.addStringPermission("team:delete");
            info.addStringPermission("member:edit");
            info.addStringPermission("setting");
            info.addStringPermission("setting:edit");
            info.addStringPermission("setting:announcement");
        }else if(member.getMemberRole().equals("2")){
            //项目经理
            info.addStringPermission("project:add");
            info.addStringPermission("project:delete");
            info.addStringPermission("project:archive");
            info.addStringPermission("project:update");
            info.addStringPermission("task:add");
            info.addStringPermission("task:delete");
            info.addStringPermission("task:edit");
            info.addStringPermission("task:archive");
            info.addStringPermission("task:update");
            info.addStringPermission("member:edit");

        }else{
            //其他人员
            info.addStringPermission("task:add");
            info.addStringPermission("task:delete");
            info.addStringPermission("task:edit");
            info.addStringPermission("task:archive");
            info.addStringPermission("member:edit");
        }
        info.addStringPermission(member.getMemberRole());
        //页面信息的初始化
        return info;
    }

    /**
     * 执行认证逻辑
     *
     * @param authenticationToken 包含用户名密码，主机，remember的包装类
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("执行认证逻辑");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
        List<Member> members = memberService.queryAllMemberInfo();
        for (Member member : members) {
            //判断用户账号
            if (member.getMemberAccount().equals(usernamePasswordToken.getUsername())) {
                //用户名存在
                log.debug("用户存在，正在验证密码");
                //判断用户密码
                String memberSalt = member.getMemberSalt();
                ByteSource salt = ByteSource.Util.bytes(member.getMemberSalt());
                return new SimpleAuthenticationInfo(member, member.getMemberPassword(), salt, this.getName());
            }
        }
        //用户名不存在，默认为这个异常，直接return null;
        log.debug("用户名不存在");
        return null;
    }
}
