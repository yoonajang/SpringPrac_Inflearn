package SpringPrac.SpringPrac_Inflearn.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Getter @Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name="category_id")
    private Long id;
    private String name;

    private List<Item> items = new ArrayList<>();

}




