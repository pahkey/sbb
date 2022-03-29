package com.mysite.sbb.answer;

import java.security.Principal;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, 
            @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Optional<Question> question = this.questionService.getQuestion(id);
        Optional<SiteUser> user = this.userService.getUser(principal.getName());
        
        if (question.isPresent() && user.isPresent()) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("question", question.get());
                return "question_detail";
            }
            this.answerService.create(question.get(), answerForm.getContent(), user.get());
            return String.format("redirect:/question/detail/%s", id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        Optional<Answer> answer = this.answerService.getAnswer(id);
        if (answer.isPresent()) {
            Answer a = answer.get();
            if (!a.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }
            answerForm.setContent(a.getContent());
        }
        return "answer_form";
    }
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, @PathVariable("id") Integer id,
            BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Optional<Answer> answer = this.answerService.getAnswer(id);
        if (answer.isPresent()) {
            Answer a = answer.get();
            if (!a.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
            }
            this.answerService.modify(a, answerForm.getContent());
            return String.format("redirect:/question/detail/%s", a.getQuestion().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Optional<Answer> answer = this.answerService.getAnswer(id);
        if (answer.isPresent()) {
            Answer a = answer.get();
            if (!a.getAuthor().getUsername().equals(principal.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
            }
            this.answerService.delete(a);
            return String.format("redirect:/question/detail/%s", a.getQuestion().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        Optional<Answer> answer = this.answerService.getAnswer(id);
        Optional<SiteUser> user = this.userService.getUser(principal.getName());
        if (answer.isPresent() && user.isPresent()) {
            Answer a = answer.get();
            SiteUser u = user.get();
            this.answerService.vote(a, u);
            return String.format("redirect:/question/detail/%s", a.getQuestion().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }
}