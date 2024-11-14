package com.example.boardproj.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoticeDTO {

    private Long num;

    private String  title;

    private String content;


}