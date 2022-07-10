package SpringPrac.SpringPrac_Inflearn.service;

import SpringPrac.SpringPrac_Inflearn.domain.Delivery;
import SpringPrac.SpringPrac_Inflearn.domain.Member;
import SpringPrac.SpringPrac_Inflearn.domain.Order;
import SpringPrac.SpringPrac_Inflearn.domain.OrderItem;
import SpringPrac.SpringPrac_Inflearn.domain.item.Item;
import SpringPrac.SpringPrac_Inflearn.repository.ItemRepository;
import SpringPrac.SpringPrac_Inflearn.repository.MemberRepository;
import SpringPrac.SpringPrac_Inflearn.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문하기
     */
    @Transactional
    public Long Order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소하기
     */
    @Transactional
    public void cancelOrder(Long orderId) {

        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 삭제
        order.cancel();
    }


    /**
     * 주문 검색하기
     */
//    public List<Order> findOrder(OrderSearch orderSearch) {
//        return orderRepository.findAll(orderSearch);
//    }

}
