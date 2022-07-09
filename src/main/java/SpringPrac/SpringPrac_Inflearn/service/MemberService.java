package SpringPrac.SpringPrac_Inflearn.service;


import SpringPrac.SpringPrac_Inflearn.domain.Member;
import SpringPrac.SpringPrac_Inflearn.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service                          // Transcational 과 @Transactional(readOnly = true) 가 있는 경우  Transcational를 먼저 읽음
@Transactional(readOnly = true)   // Transactional 내에서 정보 수정이 가능하다! Default가 정보 수정이 가능하게 설정이 되어있음!
@RequiredArgsConstructor          // final을 가진 필드만 생성장를 자동 생성 시켜준다.
//@AllArgsConstructor             // MemberService의 생성자를 자동으로 만들어줘서 생성자를 생략할 수 있다.
public class MemberService {

    private final MemberRepository memberRepository; // 컴파일 시점에 체크해줄수 있기 때문에 Final 을 권장한다.
//    @AllArgsConstructor 를 통해서 아래의 내용은 생략이 가능하다.
//    @Autowired // 셍상자 Injection 자동으로 연결시켜주므로 생략가능하다.
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
    * 회원가입
    */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
