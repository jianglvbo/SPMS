package com.mieo.service.Impl;

import com.mieo.common.util.DingTalkUtil;
import com.mieo.mapper.DynamicStateMapper;
import com.mieo.mapper.MemberMapper;
import com.mieo.model.DynamicState;
import com.mieo.model.Member;
import com.mieo.model.Team;
import com.mieo.service.DynamicStateService;
import com.mieo.service.MemberService;
import com.mieo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberMapper memberMapper;
    @Autowired
    DynamicStateService dynamicStateService;
    @Autowired
    DingTalkUtil dingTalkUtil;
    @Autowired
    TeamService teamService;

    /**
     * 添加成员信息
     *
     * @param member 需要添加的成员信息
     * @return 状态码
     */
    @Override
    public void addMember(Member member) {
        memberMapper.addMember(member);
        Team team = teamService.queryTeamByTeamId(member.getMemberTeamId());
        //钉钉
        dingTalkUtil.dingTalkLinkAtOne("添加成员:  " + member.getMemberName(), member.getMemberRole() + "  " + team.getTeamName(), "http://localhost/member/toMemberDetailDynamicState?memberId=" + member.getMemberId());
        //删除动态
        DynamicState dynamicState = new DynamicState("添加成员", member.getMemberName(), "成员",member.getMemberId());
        dynamicStateService.addDynamicState(dynamicState);
    }

    /**
     * 通过成员id删除信息
     *
     * @param id 成员id
     * @return 状态码
     */
    @Override
    public int deleteMemberById(Integer id) {
        Member member = memberMapper.queryMemberById(id);
        //钉钉
        dingTalkUtil.dingTalkTextAtOne("删除成员:  " + member.getMemberName());
        //删除动态
        DynamicState dynamicState = new DynamicState("删除了成员", member.getMemberName(), "成员",0);
        DynamicState dynamicState1=new DynamicState("","","成员",member.getMemberId());
        dynamicStateService.deleteDynamicStateByDynamicState(dynamicState1);
        dynamicStateService.addDynamicState(dynamicState);
        return memberMapper.deleteMemberById(id);
    }

    /**
     * 通过成员id批量删除成员信息
     *
     * @param ids 成员id链表
     * @return 状态码
     */
    @Override
    public void deleteMemberByIds(List<Integer> ids) {
        for (Integer id : ids) {
            Member member = memberMapper.queryMemberById(id);
            //钉钉
            dingTalkUtil.dingTalkTextAtOne("删除成员:  " + member.getMemberName());
            //删除动态
            DynamicState dynamicState = new DynamicState("删除了成员", member.getMemberName(), "成员",0);
            DynamicState dynamicState1=new DynamicState("","","成员",member.getMemberId());
            dynamicStateService.deleteDynamicStateByDynamicState(dynamicState1);
            dynamicStateService.addDynamicState(dynamicState);
            memberMapper.deleteMemberById(id);
        }
    }

    /**
     * 修改对应id的成员信息
     *
     * @param member 成员信息
     */
    @Override
    public void updateMemberById(Member member) {
        memberMapper.updateMemberById(member);
        Team team = teamService.queryTeamByTeamId(member.getMemberTeamId());
        //钉钉
        dingTalkUtil.dingTalkLinkAtOne("修改成员:  " + member.getMemberName(), member.getMemberRole() + "  " + team.getTeamName(), "http://localhost/member/toMemberDetailDynamicState?memberId=" + member.getMemberId());
        //删除动态
        DynamicState dynamicState = new DynamicState("修改了成员", member.getMemberName(), "成员",member.getMemberId());
        dynamicStateService.addDynamicState(dynamicState);
    }


    /**
     * 查询所有的成员信息
     *
     * @return 成员信息
     */
    @Override
    public List<Member> queryAllMemberInfo() {
        return memberMapper.queryAllMemberInfo();
    }

    /**
     * 查询用户密码
     *
     * @param memberAccount 被查询的成员账号
     * @return 用户密码
     */
    @Override
    public String queryMemberPassword(String memberAccount) {
        return memberMapper.queryMemberPassword(memberAccount);
    }

    /**
     * 通过成员账号查询成员信息
     *
     * @param memberAccount 被查询的成员账号
     * @return 成员信息
     */
    @Override
    public Member queryMemberInfoByAccount(String memberAccount) {
        return memberMapper.queryMemberInfoByAccount(memberAccount);
    }

    /**
     * 通过用户id查找用户信息
     *
     * @param id 被查找的用户id
     * @return 用户信息
     */
    @Override
    public Member queryMemberById(Integer id) {
        return memberMapper.queryMemberById(id);
    }

    /**
     * 查询一个团队下的所有成员
     *
     * @param teamId 团队id
     * @return 团队下所有成员
     */
    @Override
    public List<Member> queryMemberByTeamId(int teamId) {
        return memberMapper.queryMemberByTeamId(teamId);
    }
}
