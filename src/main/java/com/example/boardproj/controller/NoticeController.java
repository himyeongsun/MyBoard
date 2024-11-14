package com.example.boardproj.controller;

import com.example.boardproj.dto.NoticeDTO;
import com.example.boardproj.entity.Notice;
import com.example.boardproj.repository.NoticeRepository;
import com.example.boardproj.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;

    @GetMapping("/register")
    public String registerGet(){
        return "notice/register";
    }

    @PostMapping ("/register")
    public String registerPost(Notice notice){
        log.info("controller로 들어온 notice : "+notice);

        Notice notice1=
                noticeRepository.save(notice);

        notice1.getNum();

        return "redirect:/notice/read?num="+notice.getNum();
    }

    @GetMapping("/list")
    public String list(Model model){
        List<NoticeDTO> noticeDTOList =
                noticeService.list();

        model.addAttribute("noticeDTOList",noticeDTOList);

        return "notice/list";
    }

    @GetMapping("/read")
    public String read(Long num, Model model){

        log.info("read controller로 들어온 num : "+num);

        NoticeDTO noticeDTO =
                noticeService.read(num);

        model.addAttribute("noticeDTO",noticeDTO);

        return "notice/read";
    }

    @GetMapping("/update")
    public String updateGet(Long num, Model model){
        log.info("update로 넘어온 num : "+num);

        NoticeDTO noticeDTO =
                noticeService.read(num);

        model.addAttribute("noticeDTO",noticeDTO);

        return "notice/update";
    }

    @PostMapping("/update")
    public String updatePost(NoticeDTO noticeDTO, RedirectAttributes redirectAttributes){
        log.info("updatePost로 넘어온 notice : "+noticeDTO);


        noticeService.update(noticeDTO);



        return "redirect:/notice/read?num="+noticeDTO.getNum();
    }

    @PostMapping("/del")
    public String del(Long num){
        noticeService.delete(num);

        return "redirect:/notice/list";
    }

}
