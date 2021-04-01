package com.sparta.project02.controller;

import com.sparta.project02.dto.SignupRequestDto;
import com.sparta.project02.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(SignupRequestDto requestDto, Model model) {
        //닉네임 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성하기

        try{
            userService.registerUser(requestDto);
        } catch( IllegalArgumentException e){
            // 에러 발생 시 이 쪽으로 이동되고, 에러메시지를 model 의 "error" 값으로 전달
            model.addAttribute("error",e.getMessage());
            return "signup";
        }

        return "redirect:/";
    }
}
