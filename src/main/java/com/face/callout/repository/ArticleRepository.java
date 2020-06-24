package com.face.callout.repository;

import com.face.callout.entity.Article;
import com.face.callout.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAllByIsdeletedFalseOrderByCreatedAtDesc(Pageable pageable);

    Page<Article> findAllByCreatorAndIsdeletedFalseOrderByCreatedAtDesc(User suer, Pageable pageable);
}
