package com.example.boardproj.service;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.BoardImgDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.BoardImg;
import com.example.boardproj.repository.BoardImgRepository;
import com.example.boardproj.repository.BoardRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardImgService {

    private final BoardImgRepository boardImgRepository;
    private final BoardRepository boardRepository;

    private final ModelMapper modelMapper;
    private final FileService fileService;

    @Value("${imgLocation}")
    private String imgLocation;

    public void boardImgregister(Long bno, MultipartFile multipartFile){
        // 원래 이름 가져오기
        String originalFilename =
        multipartFile.getOriginalFilename();
        // 파일명에 담긴 경로 삭제

        String imgName =
        originalFilename.substring(originalFilename.lastIndexOf("\\")+1);

        // 특별한 난수
        String uuid = UUID.randomUUID().toString();
        log.info("생성된 uuid "+uuid);
        // 중복되지 않도록 파일 이름을 변경해서 저장 DB, 물리적 파일 둘 다
        String newImgName = uuid + "_" + imgName;

        Board board =
        boardRepository.findById(bno).orElseThrow(EntityExistsException::new);

        // 참조 대상인 board를 가지고 boardimg를 만들어 저장한다.
        BoardImg boardImg = new BoardImg();
        boardImg.setBoard(board);   // 참조하는 글 번호

        boardImg.setImgPath(imgLocation);  // 저장경로
                                           // 각 경로 board notice member 등은
                                           // fileService를 만들 때 부터 파라미터로 받도록 한다.
        boardImg.setNewImgName(newImgName);   // 새로운 이름
        boardImg.setImgName(imgName);  // 사진 이름
        // DB 저장
        boardImgRepository.save(boardImg);

        // 물리적인 파일 저장
        fileService.register(multipartFile, newImgName);


    }

    public List<BoardImgDTO> boardImgread(Long bno){
        // select * from img where img.board.bno = :bno

        List<BoardImg> boardImgList =
        boardImgRepository.findByBoardBno(bno);

        List<BoardImgDTO> boardImgDTOList =

        boardImgList.stream().map(
                boardImg -> modelMapper.map(boardImg, BoardImgDTO.class)
        ).collect(Collectors.toList());

        boardImgDTOList.forEach(boardImgDTO -> log.info(boardImgDTO));

        return boardImgDTOList;
    }

    public void del(Long ino){
        boardImgRepository.deleteById(ino);
    }
}
