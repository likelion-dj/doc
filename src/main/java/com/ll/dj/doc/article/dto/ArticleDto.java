package com.ll.dj.doc.article.dto;

import com.ll.dj.doc.article.entity.Article;
import com.ll.dj.doc.util.Ut;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String title;
    private String body;

    public Article toEntity() {
        return Ut.modelMapper.map(this, Article.class);
    }
}
