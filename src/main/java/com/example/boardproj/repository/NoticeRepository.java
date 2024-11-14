package com.example.boardproj.repository;
import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {



}
