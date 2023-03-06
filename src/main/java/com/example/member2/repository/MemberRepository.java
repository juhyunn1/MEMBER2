package com.example.member2.repository;

import com.example.member2.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

  Member save(Member member);
  Optional<Member> findById(Long id); // NPE 방지 위해 Optional 사용
  Optional<Member> findByName(String name);
  List<Member> findAll();
}
