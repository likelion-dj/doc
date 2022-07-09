package com.ll.dj.doc.user.repository;

import com.ll.dj.doc.user.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNameAndEmail(String name, String email);
}
