package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Set;

import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.user.SiteUserDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {
    private Integer id;
    private String content;
    private LocalDateTime createDate;
    private QuestionDto question;
    private SiteUserDto author;
    private LocalDateTime modifyDate;
    private Set<SiteUserDto> voter;
}