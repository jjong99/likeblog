package com.sparta.likeblog.service;


import com.sparta.likeblog.dto.CommentResponseDto;
import com.sparta.likeblog.dto.PostResponseDto;
import com.sparta.likeblog.entity.*;
import com.sparta.likeblog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public PostResponseDto addLike(User user, Long postId) {
        Post post = findPost(postId);
        if(likeRepository.findByUserAndPost(user, post).isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 누른 글 입니다.");
        } else {
            post.setLikeCount(post.getLikeCount()+1);
            Like like = new Like(user, post);
            likeRepository.save(like);
        }
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto cancelLike(User user, Long postId) {
        Post post = findPost(postId);
        Like like = likeRepository.findByUserAndPost(user, post).orElseThrow(() -> new IllegalArgumentException("좋아요를 누른 적이 없는 게시글 입니다."));
        post.setLikeCount(post.getLikeCount()-1);
        likeRepository.delete(like);
        return new PostResponseDto(post);
    }


    @Transactional
    public CommentResponseDto commentAddLike(User user, Long postId, Long commentId) {
        Post post = findPost(postId);
        Comment comment = findComment(commentId);

        if (commentLikeRepository.findByUserAndPostAndComment(user, post, comment).isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 누른 댓글 입니다.");
        } else {
            comment.setLikeCount(comment.getLikeCount()+1);
            CommentLike commentLike = new CommentLike(user, post, comment);
            commentLikeRepository.save(commentLike);
            return new CommentResponseDto(comment);
        }
    }

    @Transactional
    public CommentResponseDto commentCancelLike(User user, Long postId, Long commentId) {
        Post post = findPost(postId);
        Comment comment = findComment(commentId);
        CommentLike commentLike = commentLikeRepository.findByUserAndPostAndComment(user, post, comment).orElseThrow(
                () -> new IllegalArgumentException("좋아요를 누른 적이 없는 댓글 입니다.")
        );
        comment.setLikeCount(comment.getLikeCount() - 1);
        commentLikeRepository.delete(commentLike);
        return new CommentResponseDto(comment);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
    }


}
