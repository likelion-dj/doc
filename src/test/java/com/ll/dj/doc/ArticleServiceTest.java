package com.ll.dj.doc;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.article.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;

    private ArticleDto genArticleDto(String title, String body) {
        return ArticleDto.builder()
                .title(title)
                .body(body)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
    }

    private ArticleDto saveArticle(String title, String body) {
        return articleService.create(genArticleDto(title, body));
    }

    @Test
    public void 게시물_생성() {
        ArticleDto articleDto = saveArticle("제목1", "내용1");

        assertThat(articleDto.getId()).isGreaterThan(0);
    }

    @Test
    public void 게시물_조회() {
        saveArticle("제목1", "내용1");
        ArticleDto articleDto = saveArticle("제목2", "내용2");

        ArticleDto foundArticleDto = articleService.findById(articleDto.getId());

        assertThat(foundArticleDto).isEqualTo(articleDto);
    }

    @Test
    @Rollback(value = false)
    public void 게시물_수정() {
        ArticleDto articleDto = saveArticle("제목1", "내용1");

        articleDto.setBody("내용2");

        articleService.modify(articleDto);

        ArticleDto foundArticleDto = articleService.findById(articleDto.getId());

        assertThat(foundArticleDto).isEqualTo(articleDto);
    }

    @Test
    public void 게시물_삭제() {
        ArticleDto articleDto = saveArticle("제목1", "내용1");

        articleService.remove(articleDto.getId());

        ArticleDto foundArticleDto = articleService.findById(articleDto.getId());

        assertThat(foundArticleDto).isNull();
    }
}
