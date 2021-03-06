package com.ll.dj.doc.article.entity;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.util.Ut;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @NotNull
    private LocalDateTime createdDate;
    @NotNull
    private LocalDateTime modifiedDate;
    @NotNull
    private String title;
    @NotNull
    private String body;

    public Article(LocalDateTime createdDate, LocalDateTime modifiedDate, String title, String body) {
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.title = title;
        this.body = body;
    }

    public static List<ArticleDto> toDto(List<Article> articles) {
        return articles
                .stream()
                .map(Article::toDto)
                .collect(Collectors.toList());
    }

    public ArticleDto toDto() {
        return Ut.modelMapper.map(this, ArticleDto.class);
    }

    public void update(ArticleDto articleDto) {
        Ut.modelMapper.map(articleDto, this);
    }
}
