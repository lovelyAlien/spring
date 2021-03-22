package com.sparta.project01.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private String username;
    @Column(nullable=false)
    private String content;

    public Post(String title, String username, String content){
        this.title=title;
        this.username=username;
        this.content=content;
    }

    public Post(PostRequestDto requestDto){
        this.title=requestDto.getTitle();
        this.username=requestDto.getUsername();
        this.content=requestDto.getContent();
    }

    public void update(PostRequestDto requestDto){
        this.title=requestDto.getTitle();
        this.username=requestDto.getUsername();
        this.content=requestDto.getContent();
    }
}
