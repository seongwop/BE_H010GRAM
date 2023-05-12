package com.hanghae.be_h010gram.domain.member.repository;

import com.hanghae.be_h010gram.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
