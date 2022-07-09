package com.ll.dj.doc.article.service;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.article.entity.Article;
import com.ll.dj.doc.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ModelMapper mapper;
    private final ArticleRepository articleRepository;

    private Article of(ArticleDto articleDto) {
        return mapper.map(articleDto, Article.class);
    }

    private ArticleDto of(Article article) {
        return mapper.map(article, ArticleDto.class);
    }

    private List<ArticleDto> of(List<Article> articleList) {
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
        Optional<Article> optArticle = articleRepository.findById(id);

        return optArticle.isPresent() ? of(optArticle.get()) : null;
    }

    @Transactional
    public void modify(ArticleDto articleDto) {
        Article article = articleRepository.findById(articleDto.getId()).get();
        mapper.map(articleDto, article);
    }

    @Transactional
    public void remove(long id) {
        articleRepository.deleteById(id);
    }

    public List<ArticleDto> findAll() {
        return of(articleRepository.findAll());
    }
}

