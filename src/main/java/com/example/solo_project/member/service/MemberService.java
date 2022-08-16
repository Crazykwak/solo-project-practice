package com.example.solo_project.member.service;

import com.example.solo_project.member.Member;
import com.example.solo_project.member.dto.MemberSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    public Page<Member> findAll(int page, int limit) {



        return null;
    }

    public Page<Member> findByEtc(MemberSearchDto memberSearchDto, int page, int limit) {


        return null;
    }
}
