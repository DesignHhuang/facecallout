package com.face.callout.repository;

import com.face.callout.entity.Article;
import com.face.callout.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Article[] findAllByIsdeletedFalseOrderByCreatedAtDesc();
    Article[] findAllByCreatorAndIsdeletedFalseOrderByCreatedAtDesc(User suer);
}
