package com.ll.dj.doc.member.repository;

import com.ll.dj.doc.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNameAndEmail(String name, String email);
}
