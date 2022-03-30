package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ModelMapper modelMapper;
    
    private QuestionDto of(Question question) {
        return modelMapper.map(question, QuestionDto.class);
    }
    
    private Question of(QuestionDto questionDto) {
        return modelMapper.map(questionDto, Question.class);
    }
    
    public Page<QuestionDto> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Question> questionList = this.questionRepository.findAll(pageable);
        Page<QuestionDto> questionDtoList = questionList.map(q -> of(q));
        return questionDtoList;
    }
    
    public QuestionDto getQuestion(Integer id) {  
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return of(question.get());
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    
    public QuestionDto create(String subject, String content, SiteUserDto user) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setSubject(subject);
        questionDto.setContent(content);
        questionDto.setCreateDate(LocalDateTime.now());
        questionDto.setAuthor(user);
        Question question = of(questionDto);
        this.questionRepository.save(question);
        return questionDto;
    }
    
    public QuestionDto modify(QuestionDto questionDto, String subject, String content) {
        questionDto.setSubject(subject);
        questionDto.setContent(content);
        questionDto.setModifyDate(LocalDateTime.now());
        Question question = of(questionDto);
        this.questionRepository.save(question);
        return questionDto;
    }
    
    public void delete(QuestionDto questionDto) {
        this.questionRepository.delete(of(questionDto));
    }
    
    public Question vote(Question q, SiteUser user) {
        q.voter.add(user);
        return this.questionRepository.save(q);
    }
}