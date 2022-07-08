package SpringPrac.SpringPrac_Inflearn.service;

import SpringPrac.SpringPrac_Inflearn.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;

@Service
@Transactional(readOnly = true)  //Transactional 내에서 정보 수정이 가능하다! Default가 정보 수정이 가능하게 설정이 되어있음!
                                  //Transcational 과 @Transactional(readOnly = true) 가 있는 경우  Transcational를 먼저 읽음
public class MemberService {

    private MemberRepository memberRepository;

    // 회원가입
    public Long join(Member member) {

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

}
