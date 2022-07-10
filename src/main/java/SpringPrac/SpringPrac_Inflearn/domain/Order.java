package SpringPrac.SpringPrac_Inflearn.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders") //Table 이름지정을 안하면 Order로 됨, 하지만 여기에서는 "orders"로 하고싶다!
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 메서드를 기본적으로 protected로 함으로써 남발을 줄일 수 있음.
public class Order {

    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne (fetch = LAZY)
    @JoinColumn(name="member_id") // foreign Key로 잡아주기.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne (fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id") // foreign Key로 잡아주기.
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문시간

    @Enumerated(EnumType.STRING) // String타입으로 쓸것, Ordinary는 숫자로만 표기때문에 상태가 추가가 되면 밀릴 수 있다.
    private OrderStatus status; // 주문상태 [order, cancel]

    //=== 연관관계 메서드===//
    public void setMember (Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem (OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery (Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //== 비즈니스 로직 ==//
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {  //배송상태가 이미 완료가 되어있다면,
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);                 // 나의 상태를 취소로 바꿈
        for (OrderItem orderItem : orderItems) {            // 오더 수량을 원복
            orderItem.cancel();

        }
    }
    //== 조회 로직 ==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

}
