package com.ll.dj.doc.base;

import com.ll.dj.doc.article.entity.Article;
import com.ll.dj.doc.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Profile("local")
@Component
@Slf4j
public class InitData {

    private final InitArticleService initArticleService;

    public InitData(InitArticleService initArticleService) {
        this.initArticleService = initArticleService;
    }

    @PostConstruct
    public void init() {
        initArticleService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitArticleService {

        private final ArticleRepository articleRepository;

        @Transactional
        public void init() {
            IntStream.rangeClosed(1, 100).forEach((id) -> {
                createTestArticle(id);
            });
        }

        public Article createTestArticle(int id) {
            Article article = new Article();
            article.setTitle("title " + id);
            article.setBody("body " + id);
            article.setCreatedDate(LocalDateTime.now());
            article.setModifiedDate(LocalDateTime.now());
            return articleRepository.save(article);
        }
    }
}
