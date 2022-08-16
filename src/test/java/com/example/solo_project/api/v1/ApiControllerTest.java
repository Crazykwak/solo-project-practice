package com.example.solo_project.api.v1;

import com.example.solo_project.mapper.MemberMapper;
import com.example.solo_project.member.Gender;
import com.example.solo_project.member.Member;
import com.example.solo_project.member.dto.MemberResDto;
import com.example.solo_project.member.dto.MemberSearchDto;
import com.example.solo_project.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Test
    void findAllMembers() throws Exception {

        // given
        int page = 1;
        int limit = 10;
        Member member1 = new Member("테스트1", Gender.M, "코드스테이츠", "005", "001");
        Member member2 = new Member("테스트2", Gender.W, "코드수테이쭈", "004", "002");
        List<Member> list = new ArrayList<>();
        list.add(member1);
        list.add(member2);

        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Member> pageMembers = new PageImpl<>(list, pageable, list.size());

        List<MemberResDto> result = new ArrayList<>();
        MemberResDto memberResDto1 = new MemberResDto("테스트1", Gender.M, "코드스테이츠", "005", "001");
        MemberResDto memberResDto2 = new MemberResDto("테스트2", Gender.W, "코드수테이쭈", "004", "002");
        result.add(memberResDto1);
        result.add(memberResDto2);

        given(memberService.findAll(Mockito.anyInt(), Mockito.anyInt())).willReturn(pageMembers);
        given(mapper.MemberListToMemberResDtoList(Mockito.anyList())).willReturn(result);


        // when
        ResultActions actions = mockMvc.perform(
                get("/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("page", String.valueOf(page))
                        .queryParam("limit", String.valueOf(limit))
        );

        // then

        actions.andExpect(jsonPath("$.data").isArray())
                .andExpect(status().isOk())
                .andDo(document(
                        "get-member-all",
//                        preprocessRequest(prettyPrint()),
//                        preprocessResponse(prettyPrint()),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과데이터 DTO"),
                                        fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("결과데이터 DTO, 이름"),
                                        fieldWithPath("data.[].sex").type(JsonFieldType.STRING).description("결과데이터 DTO, 성별"),
                                        fieldWithPath("data.[].companyName").type(JsonFieldType.STRING).description("결과데이터 DTO, 회사 이름"),
                                        fieldWithPath("data.[].companyType").type(JsonFieldType.STRING).description("결과데이터 DTO, 회사 타입"),
                                        fieldWithPath("data.[].companyLocation").type(JsonFieldType.STRING).description("결과데이터 DTO, 회사 위치"),

                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지수"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("표시될 회원수"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 회원수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지수")
                                )
                        )

                ));

    }

    @Test
    void findSearch() throws Exception {

        int page = 1;
        int limit = 10;

        Member member1 = new Member("테스트1", Gender.M, "코드스테이츠", "005", "001");
        List<Member> list = new ArrayList<>();
        list.add(member1);

        Pageable pageable = PageRequest.of(page-1, limit);
        Page<Member> pageMembers = new PageImpl<>(list, pageable, list.size());

        List<MemberResDto> result = new ArrayList<>();
        MemberResDto memberResDto1 = new MemberResDto("테스트1", Gender.M, "코드스테이츠", "005", "001");
        result.add(memberResDto1);

        MemberSearchDto memberSearchDto = new MemberSearchDto("테스트1", "코드스테이츠", "005", "001");
        String content = gson.toJson(memberSearchDto);

        given(memberService.findByEtc(Mockito.any(MemberSearchDto.class), Mockito.anyInt(), Mockito.anyInt())).willReturn(pageMembers);
        given(mapper.MemberListToMemberResDtoList(Mockito.anyList())).willReturn(result);


        // when
        ResultActions actions = mockMvc.perform(
                get("/members/search")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("page", String.valueOf(page))
                        .queryParam("limit", String.valueOf(limit))
                        .content(content)
        );

        // then

        actions.andExpect(status().isOk())
                .andDo(document(
                        "get-member-condition",
                        requestFields(
                                List.of(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("검색 조건 사람 이름 name, null 가능").optional(),
                                        fieldWithPath("companyName").type(JsonFieldType.STRING).description("검색 조건 사람 이름 회사 이름 null 가능").optional(),
                                        fieldWithPath("companyType").type(JsonFieldType.STRING).description("검색 조건 사람 이름 회사 타입 null 가능").optional(),
                                        fieldWithPath("companyLocation").type(JsonFieldType.STRING).description("검색 조건 사람 이름 회사 위치 null 가능").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과데이터 DTO"),
                                        fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("결과데이터 DTO, 이름"),
                                        fieldWithPath("data.[].sex").type(JsonFieldType.STRING).description("결과데이터 DTO, 성별"),
                                        fieldWithPath("data.[].companyName").type(JsonFieldType.STRING).description("결과데이터 DTO, 회사 이름"),
                                        fieldWithPath("data.[].companyType").type(JsonFieldType.STRING).description("결과데이터 DTO, 회사 타입"),
                                        fieldWithPath("data.[].companyLocation").type(JsonFieldType.STRING).description("결과데이터 DTO, 회사 위치"),

                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("페이지수"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("표시될 회원수"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("전체 회원수"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지수")
                                )
                        )
                ));

    }
}