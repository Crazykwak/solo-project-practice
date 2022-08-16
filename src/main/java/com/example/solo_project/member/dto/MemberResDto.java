package com.example.solo_project.member.dto;

import com.example.solo_project.member.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class MemberResDto implements Serializable {
    private final String name;
    private final Gender sex;
    private final String companyName;
    private final String companyType;
    private final String companyLocation;

}
