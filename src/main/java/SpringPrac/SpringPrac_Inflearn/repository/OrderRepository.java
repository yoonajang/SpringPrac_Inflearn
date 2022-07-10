package SpringPrac.SpringPrac_Inflearn.repository;

import SpringPrac.SpringPrac_Inflearn.domain.Member;
import SpringPrac.SpringPrac_Inflearn.domain.Order;
import SpringPrac.SpringPrac_Inflearn.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.criteria.internal.predicate.BooleanExpressionPredicate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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

    // 오더 검색_1 (JPQL로 처리) *JPQL 쿼리를 문자로 생성하기는 번거롭고, 실수로 인한 버그가 충분히 발생할 수 있다.
    public List<Order> findAllByString(OrderSearch orderSearch) {
        //language=JPAQL
        String jpql = "select o From Order o join o.member m";
        boolean isFirstCondition = true;
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }
        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000); //최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

    // 오더 검색_2 (JPA Criteria로 처리) *실무에서 사용하기에 너무 복잡하다.
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
                    orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" +
                            orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }

//    // 오더 검색_3 (Querydsl 로 처리) * 그나마 추천하는 방법
//    public List<Order> findAll(OrderSearch orderSearch) {
//
//        // 값을 받아오는 경우 아래와 같이 설정할 수 있음.
////        return em.createQuery("select o from Order o join o.member m" +
////                        " where o.status = :status" +
////                        " and m.name like :name", Order.class)
////                .setParameter("status", orderSearch.getOrderStatus())
////                .setParameter("name", orderSearch.getMemberName())
////                .setFirstResult(100)   // 페이지 100부터 조회 (100부터 1000개가 조회됨)
////                .setMaxResults(1000)  // 1000개까지 조회가능
////                .getResultList();
//
//        // 하 지 만, 여기에서 파라미터로 가져오지 않기때문에 동적쿼리를 이용해야한다!!!!
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
//    }

}
