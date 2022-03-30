package com.mysite.sbb.question;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    
    private QuestionDto of(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }
    
    public List<QuestionDto> getList() {
        List<Question> questionList = this.questionRepository.findAll();
        List<QuestionDto> questionDtoList = questionList.stream().map(q -> of(q)).toList();
        return questionDtoList;
    }
    
    public Optional<Question> getQuestion(Integer id) {  
        return this.questionRepository.findById(id);  
    }
}