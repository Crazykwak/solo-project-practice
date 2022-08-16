package com.example.solo_project.mapper;

import com.example.solo_project.member.Member;
import com.example.solo_project.member.dto.MemberResDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {
    public List<MemberResDto> MemberListToMemberResDtoList(List<Member> list) {

        return list.stream().map(e -> new MemberResDto(e.getName(), e.getSex(), e.getCompanyName(), e.getCompanyType(), e.getCompanyLocation()))
                .collect(Collectors.toList());
    }
}
