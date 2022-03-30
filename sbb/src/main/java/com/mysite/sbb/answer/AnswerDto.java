package com.mysite.sbb.answer;

import java.time.LocalDateTime;

import com.mysite.sbb.question.QuestionDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerDto {
    private Integer id;
    private String content;
    private LocalDateTime createDate;
    private QuestionDto question;
}
