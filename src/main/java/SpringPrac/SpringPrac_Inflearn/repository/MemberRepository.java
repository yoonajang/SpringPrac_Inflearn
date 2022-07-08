package SpringPrac.SpringPrac_Inflearn.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Member;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext //EntityFacotry에 의해 생성하고.. 뭐 막 해야하는데 얘 덕분에 안해도됨!
    private EntityManager em;

    // 저장하기
    public void save(Member member) {
        em.persist(member);
    }

    // Id로 하나만 조회
    public Member findOne(Long id) {
        return em.find(Member.class, id); // Member.class는 Member클래스를 사용한다는 것인가.. 싶음.
    }

    // 모든 자료 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 이름으로 조회
    public List<Member> findName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class) //:name 파라미터로 받기때문에 이렇게 작성됨.
                .setParameter("name",name)
                .getResultList();
    }
}
