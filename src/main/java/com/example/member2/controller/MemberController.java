package com.example.member2.controller;

import com.example.member2.domain.Member;
import com.example.member2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member") // member로 들어오는 거는 아래 클래스로 맵핑
public class MemberController {

  private final MemberService memberService;

  @Autowired // 작업지시서 개념, 자동으로 이렇게 해라, 생성자 주입, 빈으로 등록되어있어야(@Service, @Repository) 사용 가능
  public MemberController(MemberService memberService) { // 생성자를 통해 생성될 때 MemberService에 의존한다, 외부로부터 매개변수 받아서 넣어준다
    this.memberService = memberService;
  }

  @GetMapping("/new") // .../member/new로 접속하면
  public String newMember() {
    return "member/join"; // join.html 열린다
  }

  @PostMapping("/new") // .../member/new에서 post 넘어오면
  // form에서 name 값만 넘어오기 떄문에 다른 필드가 포함된 Member 대신 MemberFromForm 사용
  // 주소의 파라미터 이름이나 text input의 name 속성이랑 객체의 필드명이 같으면 알아서 setter 통해서 객체로 만든다
  public String newMemberPost(MemberFromFrom memberFromFrom) {
    Member member = new Member();
    member.setName(memberFromFrom.getName());

    try {
      System.out.println(memberService.join(member) + "번 사용자 가입 완료");
    } catch (IllegalStateException e) { // MemberService의 join()이 던진거 처리
      System.out.println("회원등록 오류 : " + e.getMessage());
    }

    return "redirect:/"; // 홈화면으로 돌아간다, /를 처리하는 GetMapping으로
  }

  @GetMapping("/list")
  public String memberList(Model model) {
    System.out.println(model);
    model.addAttribute("memberList", memberService.findAllMember()); // 모델에 리스트 넣어서 memberList라는 이름으로 list.html에 보낸다
    System.out.println(model);
    return "member/list";
  }
}
