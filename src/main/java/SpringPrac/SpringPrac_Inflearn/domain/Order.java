package SpringPrac.SpringPrac_Inflearn.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders") //Table 이름지정을 안하면 Order로 됨, 하지만 여기에서는 "orders"로 하고싶다!
@Getter
@Setter
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

    public void setOrderItem (OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery (Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }



}
