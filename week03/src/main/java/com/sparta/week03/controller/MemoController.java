package com.sparta.week03.controller;


import com.sparta.week03.domain.Memo;
import com.sparta.week03.domain.MemoRepository;
import com.sparta.week03.domain.MemoRequestDto;
import com.sparta.week03.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemoController {
    private final MemoRepository memoRepository;
    private final MemoService memoService;

    @PostMapping("/api/memos")
    public Memo creatMemo(@RequestBody MemoRequestDto requestDto){
        Memo memo =new Memo(requestDto);

        return memoRepository.save(memo);
    }

    @GetMapping("/api/memos")
    public List<Memo> readMemo(){
        LocalDateTime now  =LocalDateTime.now();
        LocalDateTime yesterday=LocalDateTime.now().minusDays(1);
        return memoRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(yesterday,now );
    }

    @DeleteMapping("/api/memos/{id}")
    public Long deleteMemo(@PathVariable Long id){
        memoRepository.deleteById(id);
        return id;
    }

    @PutMapping("/api/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        return memoService.update(id, requestDto);
    }
}
