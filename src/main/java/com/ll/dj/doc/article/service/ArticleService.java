package com.ll.dj.doc.article.service;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.article.entity.Article;
import com.ll.dj.doc.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleDto create(ArticleDto articleDto) {
        Article article = articleRepository.save(articleDto.toEntity());

        ArticleDto createdArticleDto = article.toDto();

        return createdArticleDto;
    }

    public ArticleDto findById(long id) {
        return articleRepository
                .findById(id)
                .map(Article::toDto)
                .orElse(null);
    }

    public void modify(ArticleDto articleDto) {
        articleRepository.findById(articleDto.getId())
                .ifPresent(article -> {
                    article.update(articleDto);
                    articleRepository.save(article);
                });
    }

    public void remove(long id) {
        articleRepository.deleteById(id);
    }

    public List<ArticleDto> findAll() {
        return Article.toDto(articleRepository.findAll());
    }
}

