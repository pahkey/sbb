package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mysite.sbb.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Question> questionList = this.questionRepository.findAll(pageable);
        return questionList;
    }

    public Optional<Question> getQuestion(Integer id) {
        return this.questionRepository.findById(id);
    }

    public Question create(String subject, String content, SiteUser user) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        q.setAuthor(user);
        q = this.questionRepository.save(q);
        return q;
    }
    
    public Question modify(Question q, String subject, String content) {
        q.setSubject(subject);
        q.setContent(content);
        q.setModifyDate(LocalDateTime.now());
        q = this.questionRepository.save(q);
        return q;
    }
    
    public void delete(Question q) {
        this.questionRepository.delete(q);
    }
    
    public Question vote(Question q, SiteUser user) {
        q.voter.add(user);
        return this.questionRepository.save(q);
    }
}