package com.mysite.sbb.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser user) {
        Answer a = new Answer();
        a.setContent(content);
        a.setCreateDate(LocalDateTime.now());
        a.setQuestion(question);
        a.setAuthor(user);
        a = this.answerRepository.save(a);
        return a;
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