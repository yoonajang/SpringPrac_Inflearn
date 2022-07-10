package SpringPrac.SpringPrac_Inflearn.controller;

import SpringPrac.SpringPrac_Inflearn.domain.Member;
import SpringPrac.SpringPrac_Inflearn.domain.Order;
import SpringPrac.SpringPrac_Inflearn.domain.item.Item;
import SpringPrac.SpringPrac_Inflearn.repository.OrderSearch;
import SpringPrac.SpringPrac_Inflearn.service.ItemService;
import SpringPrac.SpringPrac_Inflearn.service.MemberService;
import SpringPrac.SpringPrac_Inflearn.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {

        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";

    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                             @RequestParam("itemId") Long itemId,
                             @RequestParam("count") int count){

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";

        // 주문자 페이지로 가고 싶다면 아래의 내용 참고.
        //        Long orderId = orderService.order(memberId, itemId, count);
        //        return "redirect:/orders/" + orderId;

    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {

        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "order/orderList";
    }


}
