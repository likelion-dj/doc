package com.ll.dj.doc.article.repository;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.article.dto.QArticleDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ll.dj.doc.article.entity.QArticle.article;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ArticleDto> findAllWithIdAndTitle() {
        return jpaQueryFactory
                .select(new QArticleDto(
                        article.id,
                        article.body
                ))
                .from(article)
                .fetch();
    }
}
