package com.example.member2.service;

import com.example.member2.domain.Member;
import com.example.member2.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional // 두개 같이 쓰면 rollback해서 db에 영향 없게 해줌
class MemberServiceIntegrationTest {

  MemberRepository memberRepository; // clear 사용하기 위해 MemoryMemberRepository로 선언
  MemberService memberService;

  @Autowired
  public MemberServiceIntegrationTest(MemberRepository memberRepository, MemberService memberService) {
    this.memberRepository = memberRepository;
    this.memberService = memberService;
  }

  @Test
  void 회원가입_테스트() {
    // given
    Member member = new Member();
    member.setName("어흥이");

    // when
    Long id = memberService.join(member); // 회원가입 수행

    // then
    Member temp = memberService.findMemberById(id).get(); // 회원가입된 상태에서 id로 찾아와서
    assertThat(temp.getName()).isEqualTo(member.getName()); // 비교
  }

  @Test
  void 회원가입_중복_테스트() throws Exception {
    // given
    Member member1 = new Member();
    member1.setName("어흥이");

    Member member2 = new Member();
    member2.setName("어흥이");

    // when
    memberService.join(member1); // 회원가입 수행

    // then
    IllegalStateException e = assertThrows(IllegalStateException.class,
        () -> memberService.join(member2)); // 예외가 발생하면( = 이미 존재하는 회원이면)
    assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 해당 에러 메시지와 같은지 비교, 같으면 성공
  }

  @Test
  void 전체_회원_조회_테스트() {
    // given
    Member member1 = new Member();
    member1.setName("어흥이");
    memberService.join(member1); // 회원가입 수행

    Member member2 = new Member();
    member2.setName("으릉이");
    memberService.join(member2);

    // when
    List<Member> temp = memberService.findAllMember();

    // then
    assertThat(temp.size()).isEqualTo(2);
  }
}