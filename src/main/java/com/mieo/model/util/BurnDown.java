package com.mieo.model.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class BurnDown {
    private String date; //日期
    private Integer taskCount;//任务数
}
