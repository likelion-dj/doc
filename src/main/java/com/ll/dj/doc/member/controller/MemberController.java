package com.ll.dj.doc.member.controller;

import com.ll.dj.doc.member.form.JoinForm;
import com.ll.dj.doc.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @GetMapping("/join")
    public String joinForm(JoinForm joinForm, Model model) {

        model.addAttribute("joinForm", joinForm);
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid JoinForm joinForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        return "redirect:/";
    }
}
