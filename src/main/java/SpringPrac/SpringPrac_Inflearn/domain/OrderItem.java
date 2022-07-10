package SpringPrac.SpringPrac_Inflearn.domain;

import SpringPrac.SpringPrac_Inflearn.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 메서드를 기본적으로 protected로 함으로써 남발을 줄일 수 있음.
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne (fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne (fetch = LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice; // 주문 당시 가격
    private int count; // 주문 갯수

    //== 생성 메서드 ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);      //주문서에 들어가 Orderitem 갯수 만큼 재고에서 까주기
        return orderItem;
    }


    //== 비즈니스 로직 ==//
    public void cancel() {
        getItem().addStock(count);
    }

    //== 조회 로직 ==//

    /**
     * 주문상품 전체 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
