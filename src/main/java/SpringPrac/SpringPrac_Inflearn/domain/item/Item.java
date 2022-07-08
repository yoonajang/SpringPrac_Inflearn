package SpringPrac.SpringPrac_Inflearn.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Inheritance (strategy = InheritanceType.JOINED)
@DiscriminatorColumn (name="dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    private List<Category> categories = ArrayList<>();



}
