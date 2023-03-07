package com.example.member2.domain;

import javax.persistence.*;

@Entity
@Table(name = "member") // DB에서 테이블명
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
  private Long id;
  @Column(name = "name") // DB에서 컬럼명
  private String name;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "{ " + id + " : " + name + " }";
  }
}
