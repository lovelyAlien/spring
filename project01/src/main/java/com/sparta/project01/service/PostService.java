package com.sparta.project01.service;


import com.sparta.project01.domain.Post;
import com.sparta.project01.domain.PostRepository;
import com.sparta.project01.domain.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Long update(Long id, PostRequestDto requestDto){
        Post post = postRepository.findById(id).orElseThrow(
                ()->new NullPointerException("해당 아이디가 존재하지 않습니다")
        );
        post.update(requestDto);
        return post.getId();


    }
}
