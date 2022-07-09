package SpringPrac.SpringPrac_Inflearn.service;

import SpringPrac.SpringPrac_Inflearn.domain.Member;
import SpringPrac.SpringPrac_Inflearn.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)        // junit과 sping을 연결해서 실행할 때 사용
@SpringBootTest                     // 스트링부트를 띄운 상태에서 테스트를 하고 싶을때 사용 (이것 없으면, Autowired 연결 실패함)
@Transactional                      // TEST일때에는 Rollback (다시 되돌리기) 기능이 있다.(Service나 Repository에는 없음)
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;                        // (1/2). DB 쿼리 보고 싶을때

    @Test
//    @Rollback(false) // DB에 직접 확인해보기 다시 되돌려지는 것은 False로 둔다.
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
//        em.flush();                                      // (2/2). DB 쿼리 보고 싶을때
        assertEquals(member, memberRepository.findOne (savedId));
    }


    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

//        try {
//            memberService.join(member2);
//        } catch (IllegalStateException e) {
//            return;
//        }
        //위의 구문은 @Test(expected = IllegalStateException.class) 통해 대체 될수 있다.



        //then
        fail("예외가 발생해야 한다.");

    }

}