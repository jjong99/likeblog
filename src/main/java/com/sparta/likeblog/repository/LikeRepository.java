package com.sparta.likeblog.repository;

import com.sparta.likeblog.entity.Like;
import com.sparta.likeblog.entity.Post;
import com.sparta.likeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}
