package com.ll.dj.doc.article.repository;

import com.ll.dj.doc.article.dto.ArticleDto;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<ArticleDto> findAllWithIdAndTitle();
}
