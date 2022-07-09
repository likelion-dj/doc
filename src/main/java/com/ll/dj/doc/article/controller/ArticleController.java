package com.ll.dj.doc.article.controller;

import com.ll.dj.doc.article.service.ArticleService;
import com.ll.dj.doc.article.dto.ArticleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/")
    public String list(Model model) {
        List<ArticleDto> articleDtoList = articleService.findAll();
        model.addAttribute("articles", articleDtoList);

        return "article/list";
    }

    @GetMapping("/articles/new")
    public String createForm() {
        return "article/new";
    }

    @PostMapping
    public String create() {
        return "redirect:/article/";
    }


}
