package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository                         // 스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 예외 변환
@RequiredArgsConstructor            // 스프링 부트는 @PersistentContext 대신 @Autowired도 받아준다. 그러므로 Service에서 처럼 바꿀 수 있다.
public class MemberRepository {
    // @PersistenceContext          // 엔티티 메니저( EntityManager ) 주입
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
