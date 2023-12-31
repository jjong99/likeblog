package com.sparta.likeblog.repository;

import com.sparta.likeblog.entity.Comment;
import com.sparta.likeblog.entity.CommentLike;
import com.sparta.likeblog.entity.Post;
import com.sparta.likeblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
