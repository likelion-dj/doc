package com.ll.dj.doc.article.service;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.article.entity.Article;
import com.ll.dj.doc.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ModelMapper modelMapper;
    private final ArticleRepository articleRepository;

    public Article of(ArticleDto articleDto) {
        if (articleDto == null) return null;
        return modelMapper.map(articleDto, Article.class);
    }

    public ArticleDto of(Article article) {
        if (article == null) return null;
        return modelMapper.map(article, ArticleDto.class);
    }

    public List<ArticleDto> of(List<Article> articleList) {
        return articleList.stream()
                .map(article -> of(article))
                .collect(Collectors.toList());
    }

    public ArticleDto create(ArticleDto articleDto) {
        Article article = articleRepository.save(of(articleDto));
        articleDto.setId(article.getId());
        return articleDto;
    }

    public ArticleDto findById(long id) {
        return of(articleRepository.findById(id).orElse(null));
    }

    @Transactional
    public void modify(ArticleDto articleDto) {
        Article article = articleRepository.findById(articleDto.getId()).get();
        modelMapper.map(articleDto, article);
    }

    @Transactional
    public void remove(long id) {
        articleRepository.deleteById(id);
    }

    public List<ArticleDto> findAll() {
        return of(articleRepository.findAll());
    }
}

