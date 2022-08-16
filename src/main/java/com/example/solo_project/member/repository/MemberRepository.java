package com.example.solo_project.member.repository;

import com.example.solo_project.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
