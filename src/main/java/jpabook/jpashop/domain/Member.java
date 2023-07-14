package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column( name = "member_id" )
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @JsonIgnore
    @OneToMany( mappedBy = "member" )               // 일대다 관계. 연관관계 주인을 넘겨주기 위해 'mappedBy' 활용
    private List<Order> orders = new ArrayList<>();
}