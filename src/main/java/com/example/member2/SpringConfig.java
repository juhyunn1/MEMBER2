package com.example.member2;

import com.example.member2.repository.JdbcTemplateMemberRepository;
import com.example.member2.repository.JpaMemberRepository;
import com.example.member2.repository.MemberRepository;
import com.example.member2.repository.MemoryMemberRepository;
import com.example.member2.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;


@Configuration // Bean 수동 등록 담당
public class SpringConfig {

  private final DataSource dataSource; // application.properties에 등록한 DataSource 가져온다, Spring이 DataSource 객체에 알아서 정보 넣어줌
  private final EntityManager entityManager;

  @Autowired // 생성자 매개변수의 타입에 맞는 IoC컨테이너 안에 Bean을 주입
  public SpringConfig(DataSource dataSource, EntityManager entityManager) {
    this.dataSource = dataSource;
    this.entityManager = entityManager;
  }


  @Bean
  public MemberService memberService() { // @Service에 대해 Bean으로 수동 등록
    return new MemberService(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() { // 각 컨트롤러에 @Controller 지우고 붙이고 대신 아래에서 하나만 남김
    // return new MemoryMemberRepository();
    // return new JdbcTemplateMemberRepository(dataSource);
    return new JpaMemberRepository(entityManager);
  }

}