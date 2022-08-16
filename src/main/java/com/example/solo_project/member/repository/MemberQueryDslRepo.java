package com.example.solo_project.member.repository;

import com.example.solo_project.member.Member;
import com.example.solo_project.member.QMember;
import com.example.solo_project.member.dto.MemberSearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public abstract class MemberQueryDslRepo implements MemberRepository{

    private final EntityManager em;
    private final JPAQueryFactory query;

    public MemberQueryDslRepo(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Member> findByEtc(MemberSearchDto memberSearchDto, int page, int limit) {

        String name = memberSearchDto.getName();
        String companyName = memberSearchDto.getCompanyName();
        String companyType = memberSearchDto.getCompanyType();
        String companyLocation = memberSearchDto.getCompanyLocation();

        BooleanBuilder builder = new BooleanBuilder();

        if (StringUtils.hasText(name)) {
            builder.and(QMember.member.name.like("%" + name + "%"));
        }

        if (StringUtils.hasText(companyName)) {
            builder.and(QMember.member.companyName.like("%" + companyName + "%"));
        }

        if (StringUtils.hasText(companyType)) {
            builder.and(QMember.member.companyType.like("%" + companyType + "%"));
        }

        if (StringUtils.hasText(companyLocation)) {
            builder.and(QMember.member.companyLocation.like("%" + companyLocation + "%"));
        }

        return query
                .select(QMember.member)
                .from(QMember.member)
                .where(builder)
                .limit(limit)
                .offset(page)
                .fetch();
    }



}
