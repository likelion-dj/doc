package com.ll.dj.doc;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.article.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AppTest {
    @Autowired
    private ArticleRepository articleRepository;
    @Test
    public void 테스트() {
        // when
        List<ArticleDto> articleDtos =  articleRepository.findAllWithIdAndTitle();

        // then
        Assertions.assertThat(articleDtos.size()).isGreaterThan(0);
    }
}

