package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor                // Lombok이 final이 붙은 필드만 가지고 생성자를 직접 만들어준다.
public class MemberService {
    private final MemberRepository memberRepository;

    /*
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */
    // 필드에 직접 @Autowired 걸면 다른 리포지토리 인스턴스를 넣지 못한다는 단점이 발생
    // Setter에 @Autowired 걸면 실제 애플리케이션 돌아가는 시점에 누가 바꿀 수 있다는 단점이 발생


    /*
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    */
    // 그래서 생성자를 통한 주입은 중간에 바꿀 수 있다는 단점을 상쇄한다.
    // 그리고 테스트할 때, MemberService 생성 시점에, 서비스가 의존하는 리포지토리도 신경써서 파라미터로 넣어준다는 장점도 발생한다.
    // 최신 스프링 버전에서는 @Autowired가 없어도 생성자에 직접 주입해준다.

    /*
    *  회원 가입
    */
    @Transactional( readOnly = false )          // 데이터 변경은 모두 트랜잭션 안에서
    public Long join(Member member) {
        validateDuplicateMember(member);        // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
    *  전체 회원 조회
    */
    @Transactional( readOnly = true )           // 읽기 전용 메서드에는 'readOnly = true'
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional( readOnly = true )
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }
}
