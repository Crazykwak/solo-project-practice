package com.example.solo_project.api.v1;

import com.example.solo_project.api.v1.dto.MultiResDto;
import com.example.solo_project.mapper.MemberMapper;
import com.example.solo_project.member.Member;
import com.example.solo_project.member.dto.MemberSearchDto;
import com.example.solo_project.member.service.MemberService;
import com.example.solo_project.member.dto.MemberResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class ApiController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    // 사이트 내 전체 회원 조회 API
    @GetMapping
    public ResponseEntity findAllMembers(@RequestParam int page,
                                         @RequestParam int limit) {

        Page<Member> memberPage = memberService.findAll(page-1, limit);

        List<MemberResDto> memberResDtos = mapper.MemberListToMemberResDtoList(memberPage.getContent());

        return new ResponseEntity<>(new MultiResDto<>(memberResDtos, memberPage), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity findSearch(@RequestBody MemberSearchDto memberSearchDto,
                                     @RequestParam int page,
                                     @RequestParam int limit) {


        Page<Member> list = memberService.findByEtc(memberSearchDto, page-1, limit);
        List<MemberResDto> memberResDtos = mapper.MemberListToMemberResDtoList(list.getContent());

        return new ResponseEntity<>(new MultiResDto<>(memberResDtos, list), HttpStatus.OK);
    }

}
