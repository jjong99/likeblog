package com.sparta.likeblog.repository;

import com.sparta.likeblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
