package SpringPrac.SpringPrac_Inflearn.service;

import SpringPrac.SpringPrac_Inflearn.domain.Address;
import SpringPrac.SpringPrac_Inflearn.domain.Member;
import SpringPrac.SpringPrac_Inflearn.domain.Order;
import SpringPrac.SpringPrac_Inflearn.domain.OrderStatus;
import SpringPrac.SpringPrac_Inflearn.domain.item.Book;

import SpringPrac.SpringPrac_Inflearn.exception.NotEnoughStockException;
import SpringPrac.SpringPrac_Inflearn.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;


    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 JAP", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 Order");
        assertEquals(1, getOrder.getOrderItems().size(),"주문한 상품 종류 수 가 정확해야 한다.");
        assertEquals(10000*orderCount, getOrder.getTotalPrice(),"주문가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(),"주문수량만큼 재고가 줄어야한다.");

    }


    @Test (expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();

        Book book = createBook("시골 JAP", 10000, 10);

        int orderCount = 11;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }


    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book book = createBook("시골 JAP", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals( OrderStatus.CANCEL, getOrder.getStatus(),"주문취소시 상태는 CANCEL 이다.");
        assertEquals( 10, book.getStockQuantity(),"취소된 상품은 그만큼 재고가 증가해야 한다.");

    }

    @Test
    public void 재고수량초과() throws Exception {
        //given

        //when

        //then
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "동작구","12345"));
        em.persist(member);
        return member;
    }


}