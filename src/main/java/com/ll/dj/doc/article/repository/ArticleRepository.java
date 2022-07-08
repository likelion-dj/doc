package com.ll.dj.doc.article.repository;

import com.ll.dj.doc.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
