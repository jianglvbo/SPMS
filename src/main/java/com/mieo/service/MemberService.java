package com.mieo.service;

import com.mieo.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

public interface MemberService {

    /**
     * 添加成员信息
     *
     * @param member 需要添加的成员信息
     * @return 状态码
     */
    void addMember(Member member);

    /**
     * 通过成员id删除信息
     *
     * @param id 成员id
     * @return 状态码
     */
    int deleteMemberById(Integer id);

    /**
     * 通过成员id批量删除成员信息
     *
     * @param ids 成员id链表
     * @return 状态码
     */
    void deleteMemberByIds(List<Integer> ids);


    /**
     * 修改对应id的成员信息
     *
     * @param member 成员信息
     */
    void updateMemberById(Member member);

    /**
     * 查询所有的成员信息
     *
     * @return 成员信息
     */
    List<Member> queryAllMemberInfo();

    /**
     * 查询用户密码
     *
     * @return 用户密码
     */
    String queryMemberPassword(String memberAccount);

    /**
     * 通过成员账号查询成员信息
     *
     * @param memberAccount 成员账号
     * @return 成员信息
     */
    Member queryMemberInfoByAccount(String memberAccount);

    /**
     * 通过用户id查找用户信息
     *
     * @param id 被查找的用户id
     * @return 用户信息
     */
    Member queryMemberById(Integer id);

    /**
     * 查询一个团队下的所有成员
     *
     * @param teamId 团队id
     * @return 团队下所有成员
     */
    List<Member> queryMemberByTeamId(int teamId);
}
