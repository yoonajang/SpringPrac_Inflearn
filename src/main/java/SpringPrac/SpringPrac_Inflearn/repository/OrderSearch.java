package SpringPrac.SpringPrac_Inflearn.repository;

import SpringPrac.SpringPrac_Inflearn.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {

    private String MemberName;
    private OrderStatus orderStatus;


}
