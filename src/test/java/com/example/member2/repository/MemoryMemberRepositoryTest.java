package com.example.member2.repository;

import com.example.member2.domain.Member;
// import org.junit.jupiter.api.Assertions;
// import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


class MemoryMemberRepositoryTest {

  MemoryMemberRepository memberRepository = new MemoryMemberRepository();

  @AfterEach // 각각의 Test 이후에 실행
  void clear() {
    memberRepository.clear(); // 하나의 HashMap을 공통으로 사용하니깐 하나 끝나고 초기화 해주어야
  }

  @Test
  @DisplayName("멤버 저장 테스트") // 콘솔에 표시되는 테스트 이름
  void save() {
    // given
    Member member = new Member();
    member.setName("어흥이");

    // when
    memberRepository.save(member); // 새로 만들거 저장

    // then
    Member temp = memberRepository.findById(member.getId()).get(); // get()을 이용해서 Optional에서 객체를 꺼낸다, 객체의 리스트로 담겨있다
    // 새로 만든거랑 저장한 것을 꺼내온 것이 같은지 비교
    // Assertions.assertEquals(member, temp); // jupiter 사용
    // Assertions.assertThat(member).isEqualTo(temp); // assertj 사용
    assertThat(temp).isEqualTo(member); // static으로 import하면 앞에 Assertions 생략 가능
  }

  @Test
  void findById() {
  }

  @Test
  void findByName() { // 같은 이름은 없다
    // given
    Member member = new Member();
    member.setName("어흥이");
    memberRepository.save(member);

    if (memberRepository.findByName(member.getName()).isPresent()) { // 가져온 Optional 객체가 값을 가지고 있으면, not null이면
      // when
      Member temp = memberRepository.findByName(member.getName()).get();

      // then
      assertThat(temp).isEqualTo(member); // 주어진 temp는(assertThat()) member와 같은가(isEqualTo())
    }
  }

  @Test
  void findAll() {
    // given
    Member member1 = new Member();
    member1.setName("어흥이");
    memberRepository.save(member1);

    Member member2 = new Member();
    member2.setName("으릉이");
    memberRepository.save(member2);

    // when
    List<Member> temp = memberRepository.findAll();

    // then
    assertThat(temp.size()).isEqualTo(2);
  }
}