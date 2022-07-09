package com.ll.dj.doc.article.controller;

import com.ll.dj.doc.article.entity.Article;
import com.ll.dj.doc.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String list(Model model) {
        List<Article> articleList = articleService.findAll();
        model.addAttribute("articleList", articleList);

        return "article/list";
    }

}
