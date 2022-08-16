package com.example.solo_project.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class MemberSearchDto implements Serializable {
    private String name;
    private String companyName;
    private String companyType;
    private String companyLocation;

    public MemberSearchDto(String name, String companyName, String companyType, String companyLocation) {
        this.name = name;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }
}
