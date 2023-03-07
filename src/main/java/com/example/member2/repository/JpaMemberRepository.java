package com.example.member2.repository;

import com.example.member2.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

  private final EntityManager entityManager;

  @Autowired
  public JpaMemberRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Member save(Member member) {
    entityManager.persist(member);
    return member;
  }

  @Override
  public Optional<Member> findById(Long id) {
    Member member = entityManager.find(Member.class, id); // id를 키로 찾아서 Member 클래스로 반환
    return Optional.ofNullable(member); // Optional로 감싸서 반환
  }

  @Override
  public Optional<Member> findByName(String name) {
    // m이란 이름의 Member 객체에서 이름이 setParameter()로 준 값과 같은 필드를 리스트로 가져온다
    // m은 SQL에서 앨리어스
    // select m은 select *와 같다
    List<Member> result = entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
        .setParameter("name", name)
        .getResultList();

    //System.out.println("result.get(0) = " + result.get(0) + ":" + result.get(0).getClass()); // 없는 경우 오류 발생하므로 이렇게 하면 안됨
    //System.out.println("Optional.of(result.get(0)) = " + Optional.of(result.get(0)) + ":" + Optional.of(result.get(0)).getClass());
    System.out.println("result.stream().findAny() = " + result.stream().findAny() + ":" + result.stream().findAny().getClass());
    return result.stream().findAny(); // 없으면 Optional.empty()로 반환
    // Optional.of(result.get(0)); // ;
  }

  @Override
  public List<Member> findAll() {
    return entityManager.createQuery("select m from Member m", Member.class)
        .getResultList(); // 리스트로 반환
  }
}
