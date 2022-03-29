package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        List<Question> questionList = this.questionRepository.findAll();
        return questionList;
    }

    public Optional<Question> getQuestion(Integer id) {
        return this.questionRepository.findById(id);
    }
    
    public Question create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q = this.questionRepository.save(q);
        return q;
    }
}