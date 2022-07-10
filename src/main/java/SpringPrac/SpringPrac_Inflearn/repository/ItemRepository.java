package SpringPrac.SpringPrac_Inflearn.repository;


import SpringPrac.SpringPrac_Inflearn.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 생성하기 + 있으면 수정하기
    public void save(Item item) {
        if (item.getId() == null) {        // item은 새로 저장되기 전까지 id값이 없다. 그렇기 때문에 생성하기
            em.persist(item);
        } else {
            em.merge(item);                // item id가 있으면 갯수를 올려라!
        }
    }

    // 1개 조회하기
    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    // 모두 조회하기
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
