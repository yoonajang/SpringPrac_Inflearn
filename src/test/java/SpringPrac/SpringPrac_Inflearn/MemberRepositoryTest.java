package SpringPrac.SpringPrac_Inflearn;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest

public class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional  //테스트가 끝나면 롤백!
    @Rollback(false)  // 롤백되었지만, 보고싶을 때!
    public void testMember() throws Exception {
        //given

        Member member = new Member();
        member.setUsername("memberA");

        //when

        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
        System.out.println("findMember" + "member" );

    }
}