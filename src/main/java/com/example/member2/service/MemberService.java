package com.example.member2.service;

import com.example.member2.domain.Member;
import com.example.member2.repository.MemberRepository;
import com.example.member2.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
  private final MemberRepository memberRepository;

  @Autowired
  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  // 회원가입
  public Long join(Member member) {
    // if(!doesMemberExist(member)) { // 이름이 중복되지 않으면
    //   memberRepository.save(member);
    //   return member.getId();
    // }
    // else { // 이름이 이미 존재하면
    //   return memberRepository.findByName(member.getName()).get().getId(); // 존재하는 회원 id 반환
    // }

    // 중복 회원 여부 검증
    validateDupMember(member);

    // 회원 추가
    memberRepository.save(member);
    return member.getId();
  }

  private boolean doesMemberExist(Member member) {
    return memberRepository.findByName(member.getName()).isPresent();
  }

  private void validateDupMember(Member member) { //
    memberRepository.findByName(member.getName()).ifPresent( m -> { // 이미 가입된 회원이 있으면
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    });
  }

  // 전체 회원 조희
  public List<Member> findAllMember() {
    return memberRepository.findAll();
  }

  // 한 명의 회원 조회
  public Optional<Member> findMemberById(Long id) {
    return memberRepository.findById(id);
  }
}
