package com.mysite.sbb.question;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;

    public List<Question> getList() {
        List<Question> questionList = this.questionRepository.findAll();
        
        return questionList;
    }
}