package com.sparta.project02.repository;



import com.sparta.project02.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}