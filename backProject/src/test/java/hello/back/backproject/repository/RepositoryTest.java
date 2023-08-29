package hello.back.backproject.repository;

import hello.back.backproject.config.JpaConfig;
import hello.back.backproject.domain.Article;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
@Import(JpaConfig.class)
@ActiveProfiles("testdb")
class RepositoryTest {

  @Autowired
  ArticleRepository articleRepository;
  @Autowired
  ArticleCommentRepository articleCommentRepository;
  @Autowired
  EntityManager em;

  @Test
  @DisplayName("select 테스트")
  void read_테스트() {
    List<Article> articles = articleRepository.findAll();

    assertThat(articles).isNotNull()
            .hasSize(100);
  }

  @Test
  @DisplayName("insert 테스트")
  void insert_테스트() {

    //given
    long previousCount = articleRepository.count();

    //when
    Article article = Article.builder()
            .title("new")
            .content("conconcon")
            .hashtag("#good")
            .build();

    articleRepository.save(article);

    //then
    assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
//    assertThat(article.getCreatedBy()).isEqualTo("kch");
  }

  @Test
  @DisplayName("update 테스트")
  void update_테스트() {
    //given
    Article article = articleRepository.findById(1L).orElseThrow();
    String updateHashtag = "#springboot";
    article.changeArticle("1", "1", updateHashtag);
    em.flush();
    em.clear();

    //then
    assertThat(articleRepository.findById(1L).orElseThrow().getHashtag())
            .isEqualTo(updateHashtag);
  }

  @Test
  @DisplayName("삭제 테스트")
  void delete_test() {
    Article article = articleRepository.findById(1L).orElseThrow();
    long previousArticleCount = articleRepository.count();

    articleRepository.delete(article);

    //then
    assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
  }


}