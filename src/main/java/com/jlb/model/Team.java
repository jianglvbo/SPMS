package com.jlb.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Alias("team")
@Data
public class Team implements Serializable {
    private Integer teamId;
    private String teamName;
    private String teamDescription;
    private String teamCreateTime;
    private List<Member> teamMembers;
}
