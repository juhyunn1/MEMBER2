package com.example.member2.repository;

import com.example.member2.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

// @Repository
public class MemoryMemberRepository implements MemberRepository {

  private static Map<Long, Member> map = new HashMap<>();
  private static long seq = 0;

  @Override
  public Member save(Member member) {
    member.setId(++seq); // 1부터 저장 되게
    map.put(member.getId(), member);
    // System.out.println(map);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    return Optional.ofNullable(map.get(id)); // Optional은 객체의 리스트에 담는다, null이면 빈 객체, not null이면 map.get(id) 반환
  }

  @Override
  public Optional<Member> findByName(String name) { // 이름이 unique하다는 가정 하에 로직 구성
    Optional<Member> any = map.values().stream() // map.values()의 리스트에서, 가독성 땜에 stream 사용 for문 써도 됨
        .filter(member -> member.getName().equals(name)) // 각 member의 name이 매개변수 name과 같은 것 중
        .findAny(); // 아무거나 가져와서 Optional로 반환
    return any;
  }

  @Override
  public List<Member> findAll() {
    return new ArrayList<>(map.values());
  }

  public void clear() {
    map.clear(); // HashMap 초기화
  }
}
