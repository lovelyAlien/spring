package com.sparta.project02.service;

import com.sparta.project02.dto.BoardRequestDto;
import com.sparta.project02.model.Board;
import com.sparta.project02.repository.BoardRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardRequestDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    @Transactional
    public List<BoardRequestDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardRequestDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList) {
            BoardRequestDto boardDto = BoardRequestDto.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    @Transactional
    public BoardRequestDto getPost(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardRequestDto boardDto = BoardRequestDto.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .build();
        return boardDto;
    }
}
