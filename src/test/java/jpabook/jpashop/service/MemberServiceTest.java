package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)                                        // JUnit 실행 시에 스프링과 엮을 것인지
@SpringBootTest                                                     // 스프링부트를 띄운 채로 테스트 실행
@Transactional                                                      // 트랜잭션 걸고 테스트 실행 후 끝나면 롤백
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback( value = false )                                      // @Transactional은 실제로 insert하지만 테스트이기 때문에 rollback되던 것을 수동으로 막는다.
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("jin");
        
        // when
        Long saveId = memberService.join(member);                   // commit()이 기준이기 때문에 persist() 때문에 insert문이 나가지는 않는다.
    
        // then
        assertEquals(member, memberRepository.findById(saveId).get());     // JPA는 DB에서 들여온 객체가 동일하면 하나의 인스턴스로 관리한다.
    }
    
    @Test( expected = IllegalStateException.class )
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");

        // when
        memberService.join(member1);
        memberService.join(member2);                                // 여기서 예외가 발생해야 한다.

        // then
        fail("예외가 발생해야 한다.");                                   // 로직이 여기까지 왔을 경우 테스트를 실패시키는 fail()
    }
}