package com.mysite.sbb.question;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuestionController {
    
    private final QuestionService questionService;

    @RequestMapping("/question/list")
    public String list(Model model) {
        List<QuestionDto> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
}