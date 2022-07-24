package com.ll.dj.doc.base;

import com.ll.dj.doc.article.dto.ArticleDto;
import com.ll.dj.doc.article.service.ArticleService;
import com.ll.dj.doc.member.dto.MemberDto;
import com.ll.dj.doc.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

@Profile("local")
@Component
@Slf4j
@RequiredArgsConstructor
@DependsOn("Ut")
public class InitData {

    private final InitArticleService initArticleService;
    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {
        initMemberService.init();
        initArticleService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitArticleService {
        private final ArticleService articleService;

        @Transactional
        public void init() {
            IntStream.rangeClosed(1, 10).forEach((id) -> {
                createTestArticle(id);
            });
        }

        public ArticleDto createTestArticle(int id) {
            return articleService.create(new ArticleDto(
                    id,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "제목 " + id,
                    "내용 " + id
            ));
        }
    }

    @Component
    @RequiredArgsConstructor
    static class InitMemberService {
        private final MemberService memberService;

        @Transactional
        public void init() {
            IntStream.rangeClosed(1, 10).forEach((id) -> {
                memberService.create(
                        new MemberDto(
                                "user" + id,
                                "pass",
                                "유저" + id,
                                "user" + id + "@test.com")
                );
            });
        }
    }
}
