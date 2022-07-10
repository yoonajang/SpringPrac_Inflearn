package SpringPrac.SpringPrac_Inflearn.repository;

import SpringPrac.SpringPrac_Inflearn.domain.Order;
import SpringPrac.SpringPrac_Inflearn.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
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

    // 오더 전체 조회 (동적쿼리 ===> Querydsl 로 처리)
//    public List<Order> findAll(OrderSearch orderSearch) {

        // 값을 받아오는 경우 아래와 같이 설정할 수 있음.
//        return em.createQuery("select o from Order o join o.member m" +
//                        " where o.status = :status" +
//                        " and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setFirstResult(100)   // 페이지 100부터 조회 (100부터 1000개가 조회됨)
//                .setMaxResults(1000)  // 1000개까지 조회가능
//                .getResultList();

        // 하 지 만, 여기에서 파라미터로 가져오지 않기때문에 동적쿼리를 이용해야한다!!!!
//        QOrder order = QOrder.order;
//        QMember member = QMember.member;
//
//        return query
//                .selecwet(order)
//                .from(order)
//                .join(order.member, member)
//                .where(statusEq(orderSearch.getOrderStatus()),
//                        nameLike(orderSearch.getMemberName()))
//                .limit(1000)
//                .fetch();
//
//    }
//
//    private BooleanExpression statusEq(OrderStatus statusCond) {
//        if (statusCond == null) {
//            return null;
//        }
//        return order.status.eq(statusCond);
}
