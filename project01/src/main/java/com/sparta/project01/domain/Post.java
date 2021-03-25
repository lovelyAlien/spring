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
    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    public Post(String title, String username, String contents){
        this.title=title;
        this.username=username;
        this.contents=contents;
    }

    public Post(PostRequestDto requestDto){
        this.title=requestDto.getTitle();
        this.username=requestDto.getUsername();
        this.contents=requestDto.getContents();
    }

    public void update(PostRequestDto requestDto){
        this.title=requestDto.getTitle();
        this.username=requestDto.getUsername();
        this.contents=requestDto.getContents();
    }
}
