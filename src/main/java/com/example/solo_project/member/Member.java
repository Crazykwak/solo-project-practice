package com.example.solo_project.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender sex;
    private String companyName;


    private String companyType;
    private String companyLocation;

    public Member(String name, Gender sex, String companyName, String companyType, String companyLocation) {
        this.name = name;
        this.sex = sex;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyLocation = companyLocation;
    }
}
