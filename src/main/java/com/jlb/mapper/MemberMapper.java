package com.jlb.mapper;

import com.jlb.model.Member;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberMapper {

    /**
     * 添加新成员
     * @param member 成员信息
     * @return 状态码
     */
    @Insert("insert  INTO  member" +
            "    (member_role, member_name, member_phone, member_team_id, member_create_time, member_account, member_password, member_gender, member_icon, member_salt)" +
            "     VALUES(#{memberRole},#{memberName},#{memberPhone},#{memberTeamId},#{memberCreateTime},#{memberAccount},#{memberPassword},#{memberGender},#{memberIcon},#{memberSalt})")
    @Options(useGeneratedKeys = true,keyProperty = "memberId",keyColumn = "member_id")
    void addMember(Member member);

    /**
     * 修改对应id的成员信息
     * @param member 成员信息
     */
    @Update("update member set member_role=#{memberRole},member_name=#{memberName},member_account=#{memberAccount}," +
            "member_phone=#{memberPhone},member_team_id=#{memberTeamId},member_create_time=#{memberCreateTime}," +
            "member_gender=#{memberGender}," +
            "member_icon=#{memberIcon} where member_id=#{memberId}")
    void updateMemberById(Member member);

    /**
     * 修改成员的账号密码
     * @param member 成员信息
     */
    @Update("update member set member_password=#{memberPassword},member_salt=#{memberSalt} where member_phone=#{memberPhone}")
    void updateMemberPasswordByPhone(Member member);

    /**
     * 通过成员id删除信息
     *
     * @param id 成员id
     * @return 状态码
     */
    @Delete("delete from member where member_id=#{id}")
    int deleteMemberById(Integer id);


    /**
     * 通过用户手机号查找用户信息
     * @return 查找的用户信息
     */
    @Select("select * from member where member_phone=#{phone}")
    @ResultMap("member_team")
    Member queryMemberByPhone(String phone);

    /**
     * 通过用户id查找用户信息
     *
     * @return 查找的用户信息
     */
    @Select("select * from member where member_id=#{id}")
    @ResultMap("member_team")
    Member queryMemberById(Integer id);

    /**
     * 通过用户id查询用户名称
     * @param id
     * @return
     */
    @Select("SELECT member_name FROM member WHERE member_id=#{id}")
    String queryMemberNameByMemberId(Integer id);

    /**
     * 查询团队下的成员是否为空
     * @param teamId
     * @return
     */
    @Select("SELECT count(*) FROM  member where member_team_id=#{teamId}")
    int queryMemberCountByTeamId(int teamId);

    /**
     * 查询所有的成员数量
     * @return
     */
    @Select("Select count(*) from member")
    int queryMemberCountAll();

    /**
     * 查询用户密码
     *
     * @return 用户密码
     */
    @Select("select member_password from member where member_account=#{memberAccount}")
    String queryMemberPassword(String memberAccount);

    /**
     * 通过成员账号查询成员信息
     *
     * @param memberAccount 成员账号
     * @return 成员信息
     */
    @Select("select * from member where member_account=#{memberAccount}")
    @Results(id = "member_team",value = {
            @Result(property = "memberTeam",column = "member_team_id",one=@One(select = "com.jlb.mapper.TeamMapper.queryTeamByTeamId"))
    })
    Member queryMemberInfoByAccount(String memberAccount);

    /**
     * 查询所有成员的信息
     * @return 所有成员的信息
     */
    @Select("select * from member")
    @ResultMap(value="member_team")
    List<Member> queryAllMemberInfo();

    /**
     * 查询一个团队下的所有成员
     * @param teamId 团队id
     * @return 团队下所有成员
     */
    @Select("select * from member where member_team_id=#{teamId}")
    List<Member> queryMemberByTeamId(int teamId);
}
