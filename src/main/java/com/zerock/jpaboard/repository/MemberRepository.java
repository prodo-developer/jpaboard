package com.zerock.jpaboard.repository;

import com.zerock.jpaboard.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}
