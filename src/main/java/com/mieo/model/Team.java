package com.mieo.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import javax.swing.*;
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
