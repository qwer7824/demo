package com.today.demo.controller.member;


import com.today.demo.entity.Heart;
import com.today.demo.entity.Marker;
import com.today.demo.entity.Member;
import com.today.demo.repository.BoardRepository;
import com.today.demo.repository.HeartRepository;
import com.today.demo.repository.MarkerRepository;
import com.today.demo.repository.MemberRepository;
import com.today.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/myPage")
    public String myPage(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        Member member = memberService.findOne(principal.getName())
                .orElse(null);
        model.addAttribute("member", member);
        return "member/myPage";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "member/login";
    }

    @GetMapping("/join")
    public String joinPage() {
        return "member/join";
    }


}
