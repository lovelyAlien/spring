//package com.sparta.project02.controller;

//import com.sparta.project02.security.UserDetailsImpl;
//import com.sparta.project02.service.BoardService;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class HomeController {
//
//
//    @GetMapping("/")
//    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        model.addAttribute("username", userDetails.getUsername());
//        return "/board/list.html";
//    }
//}