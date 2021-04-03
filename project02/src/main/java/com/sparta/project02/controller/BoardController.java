package com.sparta.project02.controller;
import com.sparta.project02.dto.BoardRequestDto;
import com.sparta.project02.security.UserDetailsImpl;
import com.sparta.project02.service.BoardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@Controller
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String list(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardRequestDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("postList", boardDtoList);
        return "board/list.html";
    }

    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }

    @PostMapping("/post")
    public String write(BoardRequestDto boardDto) {
        System.out.println(boardDto.getTitle());
        System.out.println(boardDto.getAuthor());
        System.out.println(boardDto.getContent());
        boardService.savePost(boardDto);

        return "redirect:/";
    }

    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardRequestDto boardDto = boardService.getPost(id);
        model.addAttribute("post", boardDto);
        return "board/detail.html";
    }
}