package com.sparta.likeblog.service;


import com.sparta.likeblog.dto.PostResponseDto;
import com.sparta.likeblog.entity.Like;
import com.sparta.likeblog.entity.Post;
import com.sparta.likeblog.entity.User;
import com.sparta.likeblog.repository.LikeRepository;
import com.sparta.likeblog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    @Transactional
    public PostResponseDto addLike(User user, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
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
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        Like like = likeRepository.findByUserAndPost(user, post).orElseThrow(() -> new IllegalArgumentException("좋아요를 누른 적이 없는 게시글 입니다."));
        post.setLikeCount(post.getLikeCount()-1);
        likeRepository.delete(like);
        return new PostResponseDto(post);
    }
}
