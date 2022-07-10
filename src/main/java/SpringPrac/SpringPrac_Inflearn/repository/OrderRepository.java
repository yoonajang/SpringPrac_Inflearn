package SpringPrac.SpringPrac_Inflearn.repository;

import SpringPrac.SpringPrac_Inflearn.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    // 오더 저장하기
    public void save (Order order) {
        em.persist(order);
    }

    // 오더 하나만 조회하기
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // 오더 이름으로 검색
    public List<Order> findByName(String name) {
        return em.createQuery("select o from Order o where o.name = :name", Order.class)
                .setParameter("name", name)
                .getResultList();
    }

//    // 오더 전체 조회 (나중에 작업)
//    public List<Order> findAll(OrderSearch orderSearch) {
//        return em.createQuery("select o from Order o", Order.class)
//                .setParameter("orderSearch", orderSearch)
//                .getResultList();
//    }
}
