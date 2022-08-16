package com.example.solo_project.member.service;

import com.example.solo_project.member.Member;
import com.example.solo_project.member.dto.MemberSearchDto;
import com.example.solo_project.member.repository.MemberQueryDslRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberQueryDslRepo memberQueryDslRepo;
    public Page<Member> findAll(int page, int limit) {
        return memberQueryDslRepo.findAll(PageRequest.of(page, limit));
    }

    public Page<Member> findByEtc(MemberSearchDto memberSearchDto, int page, int limit) {

        List<Member> list = memberQueryDslRepo.findByEtc(memberSearchDto, page, limit);
        return new PageImpl<>(list, PageRequest.of(page, limit), list.size());
    }
}
