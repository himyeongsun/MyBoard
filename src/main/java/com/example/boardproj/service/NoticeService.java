package com.example.boardproj.service;
import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.NoticeDTO;
import com.example.boardproj.entity.Notice;

import java.util.List;

public interface NoticeService {

    public NoticeDTO register(NoticeDTO noticeDTO);

    public List<NoticeDTO> list();
    public NoticeDTO read(Long num);
    public NoticeDTO update(NoticeDTO noticeDTO);

    // 삭제
    public void delete(Long num);


}
