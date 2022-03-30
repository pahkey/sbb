package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.user.SiteUserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;

    private Answer of(AnswerDto answerDto) {
        return modelMapper.map(answerDto, Answer.class);
    }
    
    public AnswerDto create(QuestionDto questionDto, String content, SiteUserDto author) {
        AnswerDto answerDto = new AnswerDto();
        answerDto.setContent(content);
        answerDto.setCreateDate(LocalDateTime.now());
        answerDto.setQuestion(questionDto);
        answerDto.setAuthor(author);
        Answer answer = of(answerDto);
        this.answerRepository.save(answer);
        return answerDto;
    }
    
    public Optional<Answer> getAnswer(Integer id) {
        return this.answerRepository.findById(id);
    }

    public Answer modify(Answer a, String content) {
        a.setContent(content);
        a.setModifyDate(LocalDateTime.now());
        a = this.answerRepository.save(a);
        return a;
    }
    
    public void delete(Answer a) {
        this.answerRepository.delete(a);
    }
}