package SpringPrac.SpringPrac_Inflearn.service;

import SpringPrac.SpringPrac_Inflearn.domain.item.Item;
import SpringPrac.SpringPrac_Inflearn.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 저장하기
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 수정하기
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }


    // 전체조회하기
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // 하나만 조회하기
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

}
