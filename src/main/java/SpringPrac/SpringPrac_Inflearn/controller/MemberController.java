package SpringPrac.SpringPrac_Inflearn.controller;


import SpringPrac.SpringPrac_Inflearn.domain.Address;
import SpringPrac.SpringPrac_Inflearn.domain.Member;
import SpringPrac.SpringPrac_Inflearn.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService; // Contoller가 보통 Service를 가져다 쓰기 때문에!

    @GetMapping("/members/new")
    public String createForm(Model model) {    // View 넘어갈때 model 에 데이터를 실어서 넘긴다.
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        //@Valid를 적어줘야 이름이 작성되지 않을때 알려줌 + BindingReult는 Valid에서 오류가 났을 경우 오류가 담겨서 코드가 실행된다.

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
