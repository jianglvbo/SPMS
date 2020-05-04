package com.mieo.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@Data
@Component
@Alias("member")
public class Member implements Serializable {
    private Integer memberId;
    private String memberRole;
    private String memberName;
    private String memberPhone;
    private Integer memberTeamId;
    private Team memberTeam;
    private String memberCreateTime;
    private String memberAccount;
    private String memberPassword;
    private String memberGender;
    private String memberIcon;
    private String memberSalt;
}
