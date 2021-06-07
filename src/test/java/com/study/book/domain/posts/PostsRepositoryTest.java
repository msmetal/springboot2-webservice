package com.study.book.domain.posts;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
            .title(title)
            .content(content)
            .author("msmetal")
            .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime date = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author("tester")
            .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        log.info("createdDate={}, modifiedDate={}", posts.getCreatedDate(), posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(date);
        assertThat(posts.getModifiedDate()).isAfter(date);
    }
}