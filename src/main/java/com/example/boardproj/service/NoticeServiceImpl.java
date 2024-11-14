package com.example.boardproj.service;

import com.example.boardproj.dto.NoticeDTO;
import com.example.boardproj.entity.Notice;
import com.example.boardproj.repository.NoticeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public NoticeDTO register(NoticeDTO noticeDTO) {
        Notice notice = modelMapper.map(noticeDTO, Notice.class);
        Notice result =
                noticeRepository.save(notice);

        log.info(result);

        return modelMapper.map(result, NoticeDTO.class);
    }

    @Override
    public List<NoticeDTO> list() {
        List<Notice> noticeList =
                noticeRepository.findAll();

        List<NoticeDTO> noticeDTOList = new ArrayList<>();

        NoticeDTO noticeDTO = null;
        for (Notice notice : noticeList) {
            noticeDTO = new NoticeDTO();

            noticeDTO = modelMapper.map(notice, NoticeDTO.class);

            noticeDTOList.add(noticeDTO);

        }

        noticeList.forEach(a->log.info(a));

        return noticeDTOList;
    }

    @Override
    public NoticeDTO read(Long num) {

        Optional<Notice> optionalNotice =
                noticeRepository.findById(num);

        Notice notice =
                optionalNotice.orElseThrow(EntityExistsException::new);
        NoticeDTO noticeDTO = modelMapper.map(notice, NoticeDTO.class);

        return noticeDTO;
    }

    @Override
    public NoticeDTO update(NoticeDTO noticeDTO) {

        Notice notice =
                noticeRepository.findById(noticeDTO.getNum()).orElseThrow(EntityExistsException::new);

        notice.setTitle(noticeDTO.getTitle());
        notice.setContent(noticeDTO.getContent());

        NoticeDTO noticeDTO1=
                modelMapper.map(notice, NoticeDTO.class);

        return noticeDTO1;

    }

    @Override
    public void delete(Long num) {
        noticeRepository.deleteById(num);
    }
}
