package com.zengze.demo_for_evaluate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    private String fs;
    private String ds;
    private String as;
    private String ns;
    private String finals;
    private String ctime;
}
