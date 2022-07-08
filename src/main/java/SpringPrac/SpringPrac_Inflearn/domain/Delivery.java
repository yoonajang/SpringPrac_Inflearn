package SpringPrac.SpringPrac_Inflearn.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery") // 해당 엔티티에 연결되었다는 것을 표시할때
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // String타입으로 쓸것, Ordinary는 숫자로만 표기때문에 상태가 추가가 되면 밀릴 수 있다.
    private DeliveryStatus status; // READY, COMP
}
